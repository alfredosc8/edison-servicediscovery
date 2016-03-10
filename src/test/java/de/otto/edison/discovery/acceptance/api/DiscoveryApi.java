package de.otto.edison.discovery.acceptance.api;

import de.otto.edison.discovery.ClusterInfo;
import de.otto.edison.discovery.DiscoveryService;
import de.otto.edison.discovery.marathon.MarathonDiscoveryService;
import de.otto.edison.discovery.testsupport.applicationdriver.SpringTestBase;
import de.otto.edison.discovery.testsupport.dsl.When;

import java.io.IOException;
import java.util.List;

public class DiscoveryApi extends SpringTestBase {

    private static List<ClusterInfo> clusters;

    public static When discovery_is_executed() throws IOException {
        final DiscoveryService discoveryService = applicationContext().getBean(MarathonDiscoveryService.class);
        clusters = discoveryService.discover();
        return When.INSTANCE;
    }

    public static List<ClusterInfo> the_cluster_info() {
        return clusters;
    }

}
