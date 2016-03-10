package de.otto.edison.discovery;

import de.otto.edison.annotations.Beta;

import java.util.List;

/**
 * A factory used to generate the URLs of a discovered cluster of services.
 *
 * Created by guido on 09.03.16.
 */
@Beta
public interface ServiceUrlFactory {

    /**
     * Generates one or more ServiceUrl instances for the given parameters.
     *
     * @param appId the application identifier
     * @param service the name of the service
     * @param group the name of the service group
     * @param environment the environment / stage of the deployed service.
     * @return List of ServiceUrls
     */
    public List<ServiceUrl> getServiceUrls(String appId,
                                           String service,
                                           String group,
                                           String environment);

}
