package com.truliacare.niramayaconfiguration.services;

import com.truliacare.niramayaconfiguration.models.IncomingRequestBody;

import java.util.Map;

public interface ConfigurationService {

    String getData(IncomingRequestBody requestBody);

    String executeJPA(String intent, Map<String, Object> payload);
}
