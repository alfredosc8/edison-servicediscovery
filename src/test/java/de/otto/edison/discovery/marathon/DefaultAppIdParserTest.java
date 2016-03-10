package de.otto.edison.discovery.marathon;

import org.testng.annotations.Test;

import static de.otto.edison.discovery.marathon.AppIdParser.ClusterAttr.COLOR;
import static de.otto.edison.discovery.marathon.AppIdParser.ClusterAttr.ENV;
import static de.otto.edison.discovery.marathon.AppIdParser.ClusterAttr.GROUP;
import static de.otto.edison.discovery.marathon.AppIdParser.ClusterAttr.SERVICE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DefaultAppIdParserTest {

    @Test
    public void shouldParseAppIdWithoutColor() {
        final String appId = "/test/grp/service";
        final DefaultAppIdParser service = new DefaultAppIdParser();
        assertThat(service.clusterAttrOf(appId).get(SERVICE), is("service"));
        assertThat(service.clusterAttrOf(appId).get(GROUP), is("grp"));
        assertThat(service.clusterAttrOf(appId).get(ENV), is("test"));
        assertThat(service.clusterAttrOf(appId).get(COLOR), is(""));
    }

    @Test
    public void shouldParseAppIdWithColor() {
        final String appId = "/test/grp/service/blu";
        final DefaultAppIdParser service = new DefaultAppIdParser();
        assertThat(service.clusterAttrOf(appId).get(SERVICE), is("service"));
        assertThat(service.clusterAttrOf(appId).get(GROUP), is("grp"));
        assertThat(service.clusterAttrOf(appId).get(ENV), is("test"));
        assertThat(service.clusterAttrOf(appId).get(COLOR), is("blu"));
    }

    @Test
    public void shouldParseAppIdWithoutGroupAndWithColor() {
        final String appId = "/test/service/grn";
        final DefaultAppIdParser service = new DefaultAppIdParser();
        assertThat(service.clusterAttrOf(appId).get(SERVICE), is("service"));
        assertThat(service.clusterAttrOf(appId).get(GROUP), is("service"));
        assertThat(service.clusterAttrOf(appId).get(ENV), is("test"));
        assertThat(service.clusterAttrOf(appId).get(COLOR), is("grn"));
    }

    @Test
    public void shouldParseAppIdWithoutGroupAndWithoutColor() {
        final String appId = "/test/service";
        final DefaultAppIdParser service = new DefaultAppIdParser();
        assertThat(service.clusterAttrOf(appId).get(SERVICE), is("service"));
        assertThat(service.clusterAttrOf(appId).get(GROUP), is("service"));
        assertThat(service.clusterAttrOf(appId).get(ENV), is("test"));
        assertThat(service.clusterAttrOf(appId).get(COLOR), is(""));
    }

}