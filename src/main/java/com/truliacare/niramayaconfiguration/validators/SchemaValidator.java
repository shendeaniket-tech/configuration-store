package com.truliacare.niramayaconfiguration.validators;

import com.truliacare.niramayaconfiguration.models.IncomingRequestBody;

public class SchemaValidator {

    public static boolean validate(IncomingRequestBody incomingRequestBody) {
        return incomingRequestBody != null;
    }
}
