package com.truliacare.niramayaconfiguration.mappers;

import com.truliacare.niramayaconfiguration.models.IncomingRequestBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class JSONMapper {
	
	public static IncomingRequestBody mapJSONToGetRequestBody(Map<String, Object> request) {
		JSONObject requestJson = new JSONObject(request);
		try {
			String intent = requestJson.getString("intent");
			JSONObject payload = requestJson.getJSONObject("payload");
			JSONObject context = requestJson.getJSONObject("context");
			IncomingRequestBody body = new IncomingRequestBody(intent, payload, context);
			return body;
		} catch (JSONException e) {
            return null;
        }
    }
}
