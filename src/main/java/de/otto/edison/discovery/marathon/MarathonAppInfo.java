package de.otto.edison.discovery.marathon;

import de.otto.edison.annotations.Beta;
import net.jcip.annotations.Immutable;

@Immutable
@Beta
class MarathonAppInfo {

    public final int currentInstances;
    public final int maxInstances;
    public final int minInstances;

    public MarathonAppInfo(final int currentInstances,
                            final int maxInstances,
                            final int minInstances) {
        this.currentInstances = currentInstances;
        this.maxInstances = maxInstances;
        this.minInstances = minInstances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarathonAppInfo that = (MarathonAppInfo) o;

        if (currentInstances != that.currentInstances) return false;
        if (maxInstances != that.maxInstances) return false;
        if (minInstances != that.minInstances) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = currentInstances;
        result = 31 * result + maxInstances;
        result = 31 * result + minInstances;
        return result;
    }

    @Override
    public String toString() {
        return "MarathonAppInfo{" +
                "currentInstances=" + currentInstances +
                ", maxInstances=" + maxInstances +
                ", minInstances=" + minInstances +
                '}';
    }
}
