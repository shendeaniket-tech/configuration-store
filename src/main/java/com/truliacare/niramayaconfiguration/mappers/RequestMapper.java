package com.truliacare.niramayaconfiguration.mappers;

import com.truliacare.niramayaconfiguration.models.Configuration;

import java.util.Map;

public class RequestMapper {

    public static Configuration mapRequestToConfiguration(Map<String, Object> request, Map<String, Object> context,
                                                          Configuration configuration) {
        if (request.containsKey("configurationKey")) {
            configuration.setConfigurationKey((String) request.get("configurationKey"));
        }
        if (request.containsKey("configurationValue")) {
            configuration.setConfigurationValue((String) request.get("configurationValue"));
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> eachContext : context.entrySet()) {
            String eachContextKey = eachContext.getKey();
            Object eachContextValue = eachContext.getValue();
            stringBuilder.append("key:").append(eachContextKey).append(",value:").append(eachContextValue.toString())
                    .append(",");
        }
        String contextString = stringBuilder.toString();
        if (contextString.isEmpty()) {
            configuration.setContext(null);
        } else {
            configuration.setContext(contextString.substring(0, contextString.length() - 1));
        }
        return configuration;
    }
}
