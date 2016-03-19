package de.otto.edison.discovery;

import de.otto.edison.annotations.Beta;

import java.util.List;
import java.util.Map;

/**
 * Information about discovered application clusters
 *
 * A cluster consists of
 *
 * Created by guido on 08.03.16.
 */
@Beta
public final class ClusterInfo {

    private final String id;
    private final String service;
    private final String group;
    private final String environment;
    private final Map<String,String> properties;
    private final List<NodeInfo> nodes;
    private final List<ServiceUrl> serviceUrls;

    public ClusterInfo(final String id,
                       final String service,
                       final String group,
                       final String environment,
                       final Map<String,String> properties,
                       final List<NodeInfo> nodes,
                       final List<ServiceUrl> serviceUrls) {
        this.id = id;
        this.service = service;
        this.group = group;
        this.environment = environment;
        this.properties = properties;
        this.nodes = nodes;
        this.serviceUrls = serviceUrls;
    }

    public String getId() {
        return id;
    }

    public String getServiceName() {
        return service;
    }

    public String getGroup() {
        return group;
    }

    public String getEnvironment() {
        return environment;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public List<NodeInfo> getNodes() {
        return nodes;
    }

    public List<ServiceUrl> getServiceUrls() {
        return serviceUrls;
    }

    public boolean isSameCluster(final ClusterInfo c) {
        return c.getServiceName().equals(service)
                && c.getGroup().equals(group)
                && c.getEnvironment().equals(environment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClusterInfo that = (ClusterInfo) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        if (environment != null ? !environment.equals(that.environment) : that.environment != null) return false;
        if (properties != null ? !properties.equals(that.properties) : that.properties != null) return false;
        if (nodes != null ? !nodes.equals(that.nodes) : that.nodes != null) return false;
        if (serviceUrls != null ? !serviceUrls.equals(that.serviceUrls) : that.serviceUrls != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (environment != null ? environment.hashCode() : 0);
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        result = 31 * result + (nodes != null ? nodes.hashCode() : 0);
        result = 31 * result + (serviceUrls != null ? serviceUrls.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClusterInfo{" +
                "id='" + id + '\'' +
                ", group='" + group + '\'' +
                ", environment='" + environment + '\'' +
                ", properties=" + properties +
                ", nodes=" + nodes +
                ", serviceUrls=" + serviceUrls +
                '}';
    }
}
