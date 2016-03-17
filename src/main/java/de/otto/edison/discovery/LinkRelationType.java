package de.otto.edison.discovery;

import de.otto.edison.annotations.Beta;

/**
 * Predefined Link-Relation Types used in Edison to identify hyperlinks to services.
 *
 * Created by guido on 08.03.16.
 */
@Beta
public enum LinkRelationType {

    /**
     * Relation type of a link to a Edison service.
     *
     * Example for a service-url: http://example.org:8080/my-service
     */
    REL_SERVICE("http://github.com/otto-de/edison/link-relations/service"),
    /**
     * Relation type of a link to internal APIs of Edison services.
     *
     * Example for a service-url: http://internal.live.example.org:8080/my-service/internal
     */
    REL_SERVICE_INTERNAL("http://github.com/otto-de/edison/link-relations/service/internal"),
    /**
     * Relation type of a link to the status page of Edison services.
     *
     * Example for a service-url: http://internal.live.example.org:8080/my-service/internal/status
     */
    REL_SERVICE_STATUS("http://github.com/otto-de/edison/link-relations/service/status"),
    /**
     * Relation type of a link to service's healthcheck.
     *
     * Example for a service-url: http://internal.live.example.org:8080/my-service/internal/healthcheck
     */
    REL_SERVICE_HEALTH("http://github.com/otto-de/edison/link-relations/service/health"),
    /**
     * Relation type of a link to single cluster nodes of Edison services.
     *
     * Example for a service-url: http://10.106.42.42:8080/my-service/internal
     */
    REL_SERVICE_NODE("http://github.com/otto-de/edison/link-relations/service/node");

    private final String rel;

    LinkRelationType(final String rel) {
        this.rel = rel;
    };

    @Override
    public String toString() {
        return rel;
    }
}
