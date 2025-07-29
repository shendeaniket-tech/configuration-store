package com.truliacare.niramayaconfiguration.models;

import lombok.Data;

@Data
public class ErrorResponse {
    String message;
    int status;
}
