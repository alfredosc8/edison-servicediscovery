package de.otto.edison.discovery;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

public class DefaultServiceUrlFactoryTest {

    @Test
    public void shouldGenerateTwoUrls() {
        // given
        final DefaultServiceUrlFactory urlFactory = new DefaultServiceUrlFactory("http://{env}.example.org/{service}", "http://{service}.{group}.{env}.example.org/{service}");
        // when
        final List<ServiceUrl> serviceUrls = urlFactory.getServiceUrls("/foo", "fooservice", "fooGroup", "test");
        // then
        assertThat(serviceUrls, contains(
                new ServiceUrl("http://test.example.org/fooservice", "/foo", LinkRelationType.REL_SERVICE),
                new ServiceUrl("http://fooservice.fooGroup.test.example.org/fooservice", "/foo", LinkRelationType.REL_SERVICE_INTERNAL)
        ));
    }

    @Test
    public void shouldUseEnvSpecificTemplate() {
        // given
        final DefaultServiceUrlFactory urlFactory = new DefaultServiceUrlFactory("http://{env}.example.org/{service},live=http://www.example.org/{service}", "http://{service}.{group}.{env}.example.org/{service}");
        // when
        final List<ServiceUrl> serviceUrls = urlFactory.getServiceUrls("/foo", "fooservice", "fooGroup", "live");
        // then
        assertThat(serviceUrls, contains(
                new ServiceUrl("http://www.example.org/fooservice", "/foo", LinkRelationType.REL_SERVICE),
                new ServiceUrl("http://fooservice.fooGroup.live.example.org/fooservice", "/foo", LinkRelationType.REL_SERVICE_INTERNAL)
        ));
    }

    @Test
    public void shouldResolveDefaultTemplate() {
        // given
        final DefaultServiceUrlFactory urlFactory = new DefaultServiceUrlFactory("http://{env}.example.org/{service},live=http://www.example.org/{service}", "http://{service}.{group}.{env}.example.org/{service}");
        final HashMap<String, UriTemplate> templateConfig = new HashMap<String, UriTemplate>() {{
            put(DefaultServiceUrlFactory.DEFAULT, new UriTemplate("first"));
            put("bar", new UriTemplate("second"));
        }};
        // when
        final UriTemplate uriTemplate = urlFactory.resolveTemplate(templateConfig, "test");
        // then
        assertThat(uriTemplate.toString(), is("first"));
    }

    @Test
    public void shouldResolveEnvTemplate() {
        // given
        final DefaultServiceUrlFactory urlFactory = new DefaultServiceUrlFactory("http://{env}.example.org/{service},live=http://www.example.org/{service}", "http://{service}.{group}.{env}.example.org/{service}");
        final HashMap<String, UriTemplate> templateConfig = new HashMap<String, UriTemplate>() {{
            put(DefaultServiceUrlFactory.DEFAULT, new UriTemplate("first"));
            put("live", new UriTemplate("second"));
        }};
        // when
        final UriTemplate uriTemplate = urlFactory.resolveTemplate(templateConfig, "live");
        // then
        assertThat(uriTemplate.toString(), is("second"));
    }

}