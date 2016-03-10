package de.otto.edison.discovery;

import de.otto.edison.annotations.Beta;

/**
 * Information about a single node in a {@link ClusterInfo cluster}.
 *
 * Created by guido on 08.03.16.
 */
@Beta
public final class NodeInfo {
    private final String id;
    private final String host;
    private final int port;
    private final String href;

    public NodeInfo(final String id,
                    final String host,
                    final int port,
                    final String href) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getHref() {
        return href;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeInfo nodeInfo = (NodeInfo) o;

        if (port != nodeInfo.port) return false;
        if (id != null ? !id.equals(nodeInfo.id) : nodeInfo.id != null) return false;
        if (host != null ? !host.equals(nodeInfo.host) : nodeInfo.host != null) return false;
        return !(href != null ? !href.equals(nodeInfo.href) : nodeInfo.href != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (host != null ? host.hashCode() : 0);
        result = 31 * result + port;
        result = 31 * result + (href != null ? href.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NodeInfo{" +
                "id='" + id + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", href='" + href + '\'' +
                '}';
    }
}
