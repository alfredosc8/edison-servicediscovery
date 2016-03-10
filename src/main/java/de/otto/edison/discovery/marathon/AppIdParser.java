package de.otto.edison.discovery.marathon;

import java.util.Map;

/**
 * Created by guido on 10.03.16.
 */
public interface AppIdParser {

    Map<ClusterAttr,String> clusterAttrOf(String appId);

    enum ClusterAttr {ENV, GROUP, SERVICE, COLOR};

}
