package com.truliacare.niramayaconfiguration.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.JSONObject;


@Getter
@AllArgsConstructor
public class IncomingRequestBody {
	private String intent;
	private JSONObject payload;
	private JSONObject context;
}
