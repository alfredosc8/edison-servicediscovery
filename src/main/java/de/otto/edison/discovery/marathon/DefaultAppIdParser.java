package de.otto.edison.discovery.marathon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static de.otto.edison.discovery.marathon.AppIdParser.ClusterAttr.*;

/**
 * Created by guido on 10.03.16.
 */
public class DefaultAppIdParser implements AppIdParser {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultAppIdParser.class);

    @Override
    public Map<ClusterAttr,String> clusterAttrOf(final String appId) {
        final Map<ClusterAttr, String> data = new HashMap<>();
        try {
            StringTokenizer tokenizer = new StringTokenizer(appId, "/", false);
            data.put(ENV, tokenizer.nextToken());

            final String group = tokenizer.nextToken();
            data.put(GROUP, group);

            if (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                if (token.equals("blu") || token.equals("grn")) {
                    data.put(SERVICE, group);
                    data.put(COLOR, token);
                } else {
                    data.put(SERVICE, token);
                    data.put(COLOR, tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "");
                }
            } else {
                data.put(SERVICE, group);
                data.put(COLOR, "");
            }
        } catch (final Exception e) {
            LOG.warn("Unable to parse appId '" + appId + "': " + e.getMessage());
        }
        return data;
    }


}
