package de.otto.edison.discovery;

import de.otto.edison.annotations.Beta;

/**
 * A link to a service, consisting of a href, title and link-relation type.
 *
 * Created by guido on 08.03.16.
 */
@Beta
public class ServiceUrl {

    private final String href;
    private final String title;
    private final LinkRelationType rel;

    public ServiceUrl(final String href,
                      final String title,
                      final LinkRelationType rel) {
        this.href = href;
        this.title = title;
        this.rel = rel;
    }

    public String getHref() {
        return href;
    }

    public String getTitle() {
        return title;
    }

    public LinkRelationType getRel() {
        return rel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceUrl that = (ServiceUrl) o;

        if (href != null ? !href.equals(that.href) : that.href != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return rel == that.rel;

    }

    @Override
    public int hashCode() {
        int result = href != null ? href.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (rel != null ? rel.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ServiceUrl{" +
                "href='" + href + '\'' +
                ", title='" + title + '\'' +
                ", rel=" + rel +
                '}';
    }
}
