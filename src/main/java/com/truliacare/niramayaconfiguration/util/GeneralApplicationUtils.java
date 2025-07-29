package com.truliacare.niramayaconfiguration.util;

import com.google.gson.Gson;
import com.truliacare.niramayaconfiguration.enums.IntentType;
import com.truliacare.niramayaconfiguration.models.ErrorResponse;

public class GeneralApplicationUtils {

    public static String getErrorResponse(String message, int status) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(message);
        error.setStatus(status);
        return new Gson().toJson(error);
    }

    public static String enumToName(IntentType intentType) {
        return intentType.name().toLowerCase();
    }

    public static int getStartOffset(int pageNumber) {
        int startOffset;
        if (pageNumber == 1) {
            startOffset = 0;
        } else {
            startOffset = (pageNumber - 1) * 100;
        }
        return startOffset;
    }

}
