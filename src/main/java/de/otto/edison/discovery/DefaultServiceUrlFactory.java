package de.otto.edison.discovery;

import de.otto.edison.annotations.Beta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.otto.edison.discovery.LinkRelationType.REL_SERVICE;
import static de.otto.edison.discovery.LinkRelationType.REL_SERVICE_INTERNAL;
import static java.util.Arrays.asList;

/**
 * A Factory to build ServiceUrls for discovered services.
 *
 * In order to be able to create URLs for a service in different environments, this implementation
 * is using two different uri-templates: one for "frontend" URLs, accessible from the web, and one
 * for "internal" URLs, only accessible from the internal network.
 *
 * The configured URI templates for both kind of services may contain environment-specific templates. In
 * this case, URIs a created using the env-specific template, or using the default template, if no
 * env-specific template is configured. Please have a look into README.MD for more information.
 *
 * This default implementation can be replaced by configuring a different implementation of the
 * ServiceUrlFactory interface in a Spring configuration.
 *
 * Created by guido on 09.03.16.
 */
@Beta
public class DefaultServiceUrlFactory implements ServiceUrlFactory {

    static final String DEFAULT = "edison-default-template";

    private Map<String,UriTemplate> serviceUriTemplateConfig;
    private Map<String, UriTemplate> internalUriTemplateConfig;

    /**
     * Creates a new instance.
     *
     * @param serviceUriTemplate An URI template to build service URIs, possibly containing placeholders for {service}, {group} or {env}.
     *                           Environment-specific templates can be added like this: &lt;default-template&gt;,&lt;env&gt;=&lt;env-specific-template&gt;, ...
     * @param internalUriTemplate An URI template to build internal URIs, possibly containing placeholders for {service}, {group} or {env}.
     *                           Environment-specific templates can be added like this: &lt;default-template&gt;,&lt;env&gt;=&lt;env-specific-template&gt;, ...
     */
    public DefaultServiceUrlFactory(final String serviceUriTemplate,
                                    final String internalUriTemplate) {
        this.serviceUriTemplateConfig = parseTemplateConfig(serviceUriTemplate);
        this.internalUriTemplateConfig = parseTemplateConfig(internalUriTemplate);
    }

    /**
     * Returns a list containing two ServiceUrls: one {@link LinkRelationType#REL_SERVICE} and one
     * {@link LinkRelationType#REL_SERVICE_INTERNAL} URL.
     *
     * @param appId the application identifier
     * @param service the name of the service
     * @param group the name of the service group
     * @param environment the environment / stage of the deployed service.
     *
     * @return list of service urls
     */
    @Override
    public List<ServiceUrl> getServiceUrls(final String appId,
                                           final String service,
                                           final String group,
                                           final String environment) {

        final Map<String, String> parameters = new HashMap<String,String>() {{
            put("service", service);
            put("group", group);
            put("env", environment);
        }};

        final String serviceUrl = resolveTemplate(serviceUriTemplateConfig, environment)
                .expand(parameters);
        final String internalUrl = resolveTemplate(internalUriTemplateConfig, environment)
                .expand(parameters);
        return asList(
                new ServiceUrl(serviceUrl, appId, REL_SERVICE),
                new ServiceUrl(internalUrl, appId, REL_SERVICE_INTERNAL)
        );
    }

    UriTemplate resolveTemplate(final Map<String,UriTemplate> templateConfig, final String environment) {
        UriTemplate envTemplate = templateConfig.get(environment);
        return envTemplate != null
                ? envTemplate
                : templateConfig.get(DEFAULT);
    }

    Map<String,UriTemplate> parseTemplateConfig(final String templates) {
        final Map<String, UriTemplate> templateConfig = new HashMap<>();
        final String[] parts = templates.split(",");
        templateConfig.put(DEFAULT, new UriTemplate(parts[0]));
        for (int i=1,len=parts.length; i<len; ++i) {
            final String[] envTemplate = parts[i].split("=");
            if (envTemplate.length == 2) {
                templateConfig.put(envTemplate[0], new UriTemplate(envTemplate[1]));
            } else {
                throw new IllegalArgumentException("Unable to parse template for service URLs: '" + parts[i] + "' does not match the expected pattern '<env>=<uri-template");
            }
        }
        return templateConfig;
    }
}
