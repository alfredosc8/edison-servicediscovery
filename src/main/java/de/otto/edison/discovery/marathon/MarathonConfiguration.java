package de.otto.edison.discovery.marathon;

import de.otto.edison.annotations.Beta;
import de.otto.edison.discovery.DefaultServiceUrlFactory;
import de.otto.edison.discovery.DiscoveryService;
import de.otto.edison.discovery.ServiceUrlFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Arrays.asList;


@Configuration
@ConditionalOnProperty(name = "edison.servicediscovery.marathon.servers")
@Beta
public class MarathonConfiguration {

    private @Value("${edison.servicediscovery.marathon.servers}") String marathonApiUrls;
    private @Value("${edison.servicediscovery.marathon.username}") String marathonApiUsername;
    private @Value("${edison.servicediscovery.marathon.password}") String marathonApiPassword;

    private @Value("${edison.servicediscovery.serviceUriTemplate}") String serviceUriTemplate;
    private @Value("${edison.servicediscovery.internalUriTemplate}") String internalUriTemplate;

    @Bean
    @ConditionalOnMissingBean(ServiceUrlFactory.class)
    public ServiceUrlFactory serviceUrlFactory() {
        return new DefaultServiceUrlFactory(serviceUriTemplate, internalUriTemplate);
    }

    @Beta
    @ConditionalOnMissingBean(AppIdParser.class)
    public AppIdParser appIdParser() {
        return new DefaultAppIdParser();
    }

    @Bean
    @ConditionalOnMissingBean(DiscoveryService.class)
    public DiscoveryService discoveryService() {
        return new MarathonDiscoveryService(asList(marathonApiUrls.split(",")), marathonApiUsername, marathonApiPassword, serviceUrlFactory(), appIdParser());
    }
}
