package de.otto.edison.discovery;

import java.util.List;

public interface DiscoveryService {

    List<ClusterInfo> discover();

}
