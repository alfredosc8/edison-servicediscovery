package de.otto.edison.discovery.acceptance;

import de.otto.edison.discovery.ServiceUrl;
import org.testng.annotations.Test;

import java.io.IOException;

import static de.otto.edison.discovery.LinkRelationType.REL_SERVICE;
import static de.otto.edison.discovery.LinkRelationType.REL_SERVICE_INTERNAL;
import static de.otto.edison.discovery.acceptance.api.DiscoveryApi.discovery_is_executed;
import static de.otto.edison.discovery.acceptance.api.DiscoveryApi.the_cluster_info;
import static de.otto.edison.discovery.testsupport.dsl.Then.assertThat;
import static de.otto.edison.discovery.testsupport.dsl.Then.then;
import static de.otto.edison.discovery.testsupport.dsl.When.when;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

public class DiscoveryServiceAcceptanceTest {

    @Test
    public void shouldDiscoverClusters() throws IOException {
        when(
                discovery_is_executed()
        );

        then(
                assertThat(the_cluster_info().size(), is(2)),
                assertThat(the_cluster_info().get(0).getId(), is("/ci/order/shoppingcart")),
                assertThat(the_cluster_info().get(0).getEnvironment(), is("ci")),
                assertThat(the_cluster_info().get(0).getGroup(), is("order")),
                assertThat(the_cluster_info().get(0).getServiceName(), is("shoppingcart")),
                assertThat(the_cluster_info().get(1).getId(), is("/live/order/shoppingcart")),
                assertThat(the_cluster_info().get(1).getEnvironment(), is("live")),
                assertThat(the_cluster_info().get(1).getGroup(), is("order")),
                assertThat(the_cluster_info().get(1).getServiceName(), is("shoppingcart"))
        );
    }

    @Test
    public void clustersShouldHaveServiceUrls() throws IOException {
        when(
                discovery_is_executed()
        );

        then(
                assertThat(the_cluster_info().get(0).getServiceUrls(), contains(
                    new ServiceUrl("http://ci.example.org/order-shoppingcart", "/ci/order/shoppingcart", REL_SERVICE),
                    new ServiceUrl("http://shoppingcart.order.ci.example.org/order-shoppingcart", "/ci/order/shoppingcart", REL_SERVICE_INTERNAL))),
                assertThat(the_cluster_info().get(1).getServiceUrls(), contains(
                    new ServiceUrl("https://www.example.org/order-shoppingcart", "/live/order/shoppingcart", REL_SERVICE),
                    new ServiceUrl("http://shoppingcart.order.live.example.org/order-shoppingcart", "/live/order/shoppingcart", REL_SERVICE_INTERNAL)))
        );
    }

    @Test(enabled = false)
    public void shouldDiscoverClusterNodes() throws IOException {
        when(
                discovery_is_executed()
        );

        then(
                assertThat(the_cluster_info().get(0).getNodes(), hasSize(1)),
                assertThat(the_cluster_info().get(1).getNodes(), hasSize(2))
        );
    }

}
