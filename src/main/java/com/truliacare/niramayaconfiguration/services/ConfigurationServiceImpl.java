package com.truliacare.niramayaconfiguration.services;

import com.google.gson.Gson;
import com.truliacare.niramayaconfiguration.enums.IntentType;
import com.truliacare.niramayaconfiguration.mappers.IntentToQueryMapper;
import com.truliacare.niramayaconfiguration.mappers.RequestMapper;
import com.truliacare.niramayaconfiguration.models.Configuration;
import com.truliacare.niramayaconfiguration.models.ErrorResponse;
import com.truliacare.niramayaconfiguration.models.IncomingRequestBody;
import com.truliacare.niramayaconfiguration.models.QueryResponse;
import com.truliacare.niramayaconfiguration.repositories.ConfigurationRepository;
import com.truliacare.niramayaconfiguration.repositories.daos.ConfigurationDao;
import com.truliacare.niramayaconfiguration.util.GeneralApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
    @Autowired
    ConfigurationDao configurationDao;

    @Autowired
    ConfigurationRepository configurationRepository;

    @Override
    public String executeJPA(String intent, Map<String, Object> request) {
        try {
            Map<String, Object> payload = (Map<String, Object>) request.get("payload");
            Map<String, Object> context = (Map<String, Object>) request.get("context");
            if (intent.equals(GeneralApplicationUtils.enumToName(IntentType.ADD_CONFIGURATION))) {
                Configuration existingConfiguration;

                if (payload.containsKey("identifier")) {
                    Optional<Configuration> configurationFromDatabase = configurationRepository.findByIdentifier((Integer) payload.get("identifier"));
                    existingConfiguration = configurationFromDatabase.orElseGet(Configuration::new);
                } else {
                    existingConfiguration = new Configuration();
                }
                Configuration configuration = RequestMapper.mapRequestToConfiguration(payload, context, existingConfiguration);
                Configuration response = configurationRepository.save(configuration);
                return new Gson().toJson(response);
            } else {
                return GeneralApplicationUtils.getErrorResponse("Invalid Intent", 500);
            }
        } catch (Exception exception) {
            return GeneralApplicationUtils.getErrorResponse(exception.getMessage(), 500);
        }
    }

    @Override
    public String getData(IncomingRequestBody requestBody) {
        QueryResponse queryResponse = IntentToQueryMapper.mapIntentToQuery(requestBody);
        if (queryResponse.isHasError()) {
            ErrorResponse error = new ErrorResponse();
            error.setMessage(queryResponse.getResult());
            error.setStatus(500);
            return new Gson().toJson(error);
        } else {
            return configurationDao.processQuery(queryResponse.getResult());
        }
    }
}
