package de.otto.edison.discovery;

import java.util.Map;

public class UriTemplate {
    private final String template;

    public UriTemplate(final String template) {
        this.template = template;
    }

    public String expand(final Map<String, String> parameters) {
        String uri = template;
        for (String s : parameters.keySet()) {
            uri = uri.replaceAll("\\{" + s + "\\}", parameters.get(s));
        }
        return uri;
    }

    @Override
    public String toString() {
        return template;
    }
}
