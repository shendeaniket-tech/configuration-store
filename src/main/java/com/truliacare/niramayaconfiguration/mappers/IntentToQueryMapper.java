package com.truliacare.niramayaconfiguration.mappers;

import com.truliacare.niramayaconfiguration.enums.IntentType;
import com.truliacare.niramayaconfiguration.models.IncomingRequestBody;
import com.truliacare.niramayaconfiguration.models.QueryResponse;
import com.truliacare.niramayaconfiguration.util.GeneralApplicationUtils;

public class IntentToQueryMapper {

    private static QueryResponse mapToQueryResponse(String query, boolean hasErrors) {
        QueryResponse queryResponse = new QueryResponse();
        queryResponse.setHasError(hasErrors);
        queryResponse.setResult(query);
        return queryResponse;
    }

    public static QueryResponse mapIntentToQuery(IncomingRequestBody requestBody) {
        try {
            if (requestBody.getIntent().equals(GeneralApplicationUtils.enumToName(IntentType.GET_CONFIGURATION))) {
                String configurationKey = requestBody.getPayload().getString("configurationKey");
//                String stringBuilder = "SELECT * FROM configuration WHERE configurationKey = " +
//                        "'" + configurationKey + "' OR context = " + null;
                String stringBuilder = "SELECT * FROM configuration WHERE configurationKey = " +
                        "'" + configurationKey + "'";
                return mapToQueryResponse(stringBuilder, false);
            } else {
                return mapToQueryResponse(
                        "Invalid Intent",
                        true
                );
            }
        } catch (Exception e) {
            return mapToQueryResponse(
                    e.getLocalizedMessage(),
                    true
            );
        }
    }

}
