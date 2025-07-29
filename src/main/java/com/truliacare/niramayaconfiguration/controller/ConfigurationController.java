package com.truliacare.niramayaconfiguration.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truliacare.niramayaconfiguration.enums.IntentType;
import com.truliacare.niramayaconfiguration.mappers.JSONMapper;
import com.truliacare.niramayaconfiguration.models.Configuration;
import com.truliacare.niramayaconfiguration.models.IncomingRequestBody;
import com.truliacare.niramayaconfiguration.services.ConfigurationServiceImpl;
import com.truliacare.niramayaconfiguration.util.ConfigurationPriorityUtils;
import com.truliacare.niramayaconfiguration.util.GeneralApplicationUtils;
import com.truliacare.niramayaconfiguration.validators.SchemaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class ConfigurationController {

    @Autowired
    ConfigurationServiceImpl configurationService;

    @PostMapping(value = "/v1/execute")
    public String execute(@RequestBody Map<String, Object> request) {
        IncomingRequestBody requestBody = JSONMapper.mapJSONToGetRequestBody(request);
        if (SchemaValidator.validate(requestBody)) {
            if (requestBody.getIntent().equals(GeneralApplicationUtils.enumToName(IntentType.GET_CONFIGURATION))) {
                String configurationResponse = configurationService.getData(requestBody);
                try {
                    List<Configuration> configurationList = new Gson().fromJson(configurationResponse,
                            TypeToken.getParameterized(List.class, Configuration.class).getType());
                    return ConfigurationPriorityUtils.getHighestPriorityConfiguration(configurationList, request);
                } catch (Exception exception) {
                    return configurationResponse;
                }
            } else if (requestBody.getIntent().equals(GeneralApplicationUtils.enumToName(IntentType.ADD_CONFIGURATION))) {
                return configurationService.executeJPA((String) request.get("intent"), request);
            } else {
                return GeneralApplicationUtils.getErrorResponse("Invalid Intent", 500);
            }
        } else {
            return GeneralApplicationUtils.getErrorResponse("Invalid Schema", 500);
        }
    }
}
