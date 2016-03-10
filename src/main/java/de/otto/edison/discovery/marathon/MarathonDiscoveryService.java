package de.otto.edison.discovery.marathon;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ning.http.client.Response;
import de.otto.edison.annotations.Beta;
import de.otto.edison.discovery.ClusterInfo;
import de.otto.edison.discovery.DiscoveryService;
import de.otto.edison.discovery.NodeInfo;
import de.otto.edison.discovery.ServiceUrlFactory;
import de.otto.edison.discovery.marathon.AppIdParser.ClusterAttr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static de.otto.edison.discovery.marathon.AppIdParser.ClusterAttr.*;
import static java.util.Collections.emptyList;

/**
 * DiscoveryService that is using the Marathon API to lookup clusters and service nodes of
 * a distributed system of Edison-like microservices.
 *
 */
@Beta
public class MarathonDiscoveryService implements DiscoveryService {

    private final static Logger LOG = LoggerFactory.getLogger(MarathonDiscoveryService.class);

    private final HttpService httpService;
    private final String marathonAppsAPI;
    private final String marathonApiUsername;
    private final String marathonApiPassword;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ServiceUrlFactory serviceUrlFactory;
    private final AppIdParser appIdParser;

    public MarathonDiscoveryService(final String marathonAppsAPI,
                                    final String marathonApiUsername,
                                    final String marathonApiPassword,
                                    final ServiceUrlFactory serviceUrlFactory,
                                    final AppIdParser appIdParser) {
        this.httpService = new HttpService();
        this.marathonAppsAPI = marathonAppsAPI + "/v2/apps";
        this.marathonApiUsername = marathonApiUsername;
        this.marathonApiPassword = marathonApiPassword;
        this.serviceUrlFactory = serviceUrlFactory;
        this.appIdParser = appIdParser;
    }

    @Override
    public List<ClusterInfo> discover() {
        try {
            final Response response = httpService.getJson(
                    marathonApiUsername,
                    marathonApiPassword,
                    marathonAppsAPI
            );
            return parseJsonResponse(response);
        } catch (TimeoutException | IOException | InterruptedException | ExecutionException e) {
            LOG.error("Unable to access marathon API at '{}'. Please check your application.properties", marathonAppsAPI, e);
            return emptyList();
        }
    }

    /**
     * Parses the response from Marathon /v2/apps API and returns the discovered information about clusters and nodes.
     *
     * @param response JSON response from Marathon
     * @return all discovered ClusterInfos
     * @throws IOException if accessing the response body is failing
     */
    private List<ClusterInfo> parseJsonResponse(final Response response) throws IOException {
        final List<ClusterInfo> clusters = new ArrayList<>();
        final JsonNode marathonAppInfoNode = objectMapper.readTree(response.getResponseBodyAsStream());
        marathonAppInfoNode.path("apps").forEach(appNode->{
            final String appId = appNode.path("id").asText();
            Map<ClusterAttr, String> parts = appIdParser.clusterAttrOf(appId);
            if (!parts.isEmpty()) {
                clusters.add(new ClusterInfo(
                        appId,
                        parts.get(SERVICE),
                        parts.get(GROUP),
                        parts.get(ENV),
                        propertiesOf(appNode),
                        nodeInfos(appId),
                        serviceUrlFactory.getServiceUrls(appId, parts.get(SERVICE), parts.get(GROUP), parts.get(ENV))
                ));
            }
        });
        return clusters;
    }

    /**
     * Returns the "labels" and "env" properties of a service.
     *
     * @param appNode a single node in the /apps array
     * @return key-value pairs from "labels" and "env" nodes of an app
     */
    private Map<String, String> propertiesOf(final JsonNode appNode) {
        final Map<String,String> properties = new HashMap<>();
        final JsonNode envNode = appNode.path("env");
        final Iterator<String> names = envNode.fieldNames();
        while (names.hasNext()) {
            String key = names.next();
            properties.put(key, envNode.get(key).asText());
        }
        final JsonNode labelsNode = appNode.path("labels");
        final Iterator<String> labels = labelsNode.fieldNames();
        while (labels.hasNext()) {
            String key = labels.next();
            properties.put(key, labelsNode.get(key).asText());
        }
        return properties;
    }

    private List<NodeInfo> nodeInfos(final String appId) {
        return emptyList();
    }


/*
    public Optional<MarathonAppInfo> getAppInfo(final AppConfig appConfig) {
        try {
            final Response response = httpService.getJson(
                    marathonApiUsername,
                    marathonApiPassword,
                    marathonAppsAPI + appConfig.getMarathonAppId()
            );

            final JsonNode marathonAppInfoNode = objectMapper.readTree(response.getResponseBodyAsStream());
            final JsonNode appNode = marathonAppInfoNode.path("app");
            final JsonNode labelsNode = appNode.path("labels");
            final Integer currentInstances = Integer.valueOf(appNode.path("instances").asText());
            final Integer maxInstances = Integer.valueOf(labelsNode.path("AUTOSCALING_MAX_INSTANCES").asText());
            final Integer minInstances = Integer.valueOf(labelsNode.path("AUTOSCALING_MIN_INSTANCES").asText());

            return Optional.of(createMarathonAppInfo(currentInstances, maxInstances, minInstances));
        } catch (TimeoutException | IOException | InterruptedException | ExecutionException e) {
            LOG.error("could not retrieve marathon app info '{}'", marathonAppsAPI + appConfig.getMarathonAppId(), e);
            return Optional.empty();
        }
    }

    public void scaleTo(final int newNumberOfInstances, final String colorToDeploy, final AppConfig appConfig) {
        final String appIdWithColor = appConfig.getMarathonAppId() +
                (appConfig.isDeployWithColor()
                        ? "/" + colorToDeploy.toLowerCase()
                        : "");
        final String marathonAppUrl = marathonAppsAPI + appIdWithColor;
        final ImmutableMap<String, Object> jsonConfig = ImmutableMap.of(
                "id", appIdWithColor,
                "instances", newNumberOfInstances
        );

        try {
            final Response response = httpService.putJson(
                    marathonApiUsername,
                    marathonApiPassword,
                    marathonAppUrl,
                    jsonConfig
            );
            if (response.getStatusCode() < 400) {
                LOG.info("deploy API response '{}', '{}'", response.getStatusCode(), response.getResponseBody());
            } else {
                LOG.error("deploy API response '{}', '{}'", response.getStatusCode(), response.getResponseBody());
            }
        } catch (TimeoutException | IOException | InterruptedException | ExecutionException e) {
            LOG.error("could not scale marathon app '{}' with body '{}'", marathonAppUrl, jsonConfig, e);
        }
    }
    */
}
