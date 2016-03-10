package de.otto.edison.discovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

import static java.util.Comparator.*;

@Component
public class ExampleClient {

    private static final Logger LOG = LoggerFactory.getLogger(ExampleClient.class);

    @Autowired
    private DiscoveryService discoveryService;

    @PostConstruct
    public void logDiscoveredServices() {
        discoveryService.discover().stream().sorted(comparing(ClusterInfo::getId)).forEach(cluster->{
            final String internalLink = internalHrefOf(cluster);
            LOG.info("id={}, env={}, group={}, service={}, href={}",
                    cluster.getId(),
                    cluster.getEnvironment(),
                    cluster.getGroup(),
                    cluster.getServiceName(),
                    internalLink);
        });
    }

    private String internalHrefOf(ClusterInfo cluster) {
        final Optional<ServiceUrl> serviceUrl = cluster.getServiceUrls()
                .stream()
                .filter(s -> s.getRel().equals(LinkRelationType.REL_SERVICE_INTERNAL))
                .findAny();
        return serviceUrl.isPresent() ? serviceUrl.get().getHref(): "<unknown>";
    }
}
