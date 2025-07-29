package com.truliacare.niramayaconfiguration.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truliacare.niramayaconfiguration.models.Configuration;
import com.truliacare.niramayaconfiguration.models.ErrorResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigurationPriorityUtils {

    private static final String[] priorityQueue = new String[]{"user", "role", "organization"};

    public static String getHighestPriorityConfiguration(List<Configuration> configurationList, Map<String, Object> request) {
        try {
            Map<String, Object> context = (Map<String, Object>) request.get("context");
            List<String> contextList = mapContextToList(context);
            if (contextList.isEmpty()) {
                throw new Exception("Context not provided");
            }
            List<Configuration> configurationBasedOnRequestedContext = getConfigurationBasedOnRequestContext(contextList, configurationList);
            if (configurationBasedOnRequestedContext.size() > 1) {
                Configuration resultConfiguration = prioritizeContext(configurationBasedOnRequestedContext);
                return new Gson().toJson(resultConfiguration);
            } else if (configurationBasedOnRequestedContext.size() == 1){
                return new Gson().toJson(configurationBasedOnRequestedContext.get(0));
            } else {
                Configuration configuration = getDefaultConfigurationIfPresent(configurationList);
                if (configuration != null) {
                    return new Gson().toJson(configuration);
                } else {
                    ErrorResponse error = new ErrorResponse();
                    error.setMessage("Nothing present for the requested context");
                    error.setStatus(500);
                    return new Gson().toJson(error);
                }
            }
        } catch (Exception exception) {
            ErrorResponse error = new ErrorResponse();
            error.setMessage(exception.getMessage());
            error.setStatus(500);
            return new Gson().toJson(error);
        }
    }

    private static Configuration getDefaultConfigurationIfPresent(List<Configuration> configurationList) {
        Configuration resultConfiguration = null;
        for (Configuration configuration: configurationList) {
            if (configuration.getContext() == null) {
                resultConfiguration = configuration;
                break;
            }
        }
        return resultConfiguration;
    }

    private static Configuration prioritizeContext(List<Configuration> configurationBasedOnRequestedContext) {
        Configuration resultConfiguration = null;
        for (String eachPriority : priorityQueue) {
            if (resultConfiguration != null) {
                break;
            }
            for (Configuration configuration : configurationBasedOnRequestedContext) {
                if (configuration.getContext().contains(eachPriority)) {
                    resultConfiguration = configuration;
                    break;
                }
            }
        }
        return resultConfiguration;
    }

    private static List<Configuration> getConfigurationBasedOnRequestContext(List<String> contextList,
                                                                             List<Configuration> configurationList) {
        if (contextList.isEmpty()) {
            return configurationList;
        } else {
            List<Configuration> resultConfiguration = new ArrayList<>();
            for (Configuration configuration : configurationList) {
                for (String context : contextList) {
                    if (configuration.getContext() != null && configuration.getContext().equals(context)) {
                        resultConfiguration.add(configuration);
                    }
                }
            }
            return resultConfiguration;
        }
    }

    private static List<String> mapContextToList(Map<String, Object> context) {
        List<String> contextList = new ArrayList<>();
        for (Map.Entry<String, Object> eachContext : context.entrySet()) {
            StringBuilder stringBuilder = new StringBuilder();
            String eachContextKey = eachContext.getKey();
            Object eachContextValue = eachContext.getValue();
            stringBuilder.append("key:").append(eachContextKey).append(",value:").append(eachContextValue.toString());
            contextList.add(stringBuilder.toString());
        }
        return contextList;
    }
}
