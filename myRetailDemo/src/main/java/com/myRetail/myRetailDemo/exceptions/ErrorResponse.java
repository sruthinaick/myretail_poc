package com.myRetail.myRetailDemo.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@ApiModel("The error response.")
public class ErrorResponse {
    public List<ErrorDetails> errors;
    public Map<String, String> additionalInfo;


    public ErrorResponse(List<ErrorDetails> errors) {
        this.errors = errors;
    }

    public ErrorResponse(List<ErrorDetails> errors, Map<String, String> additionalInfo) {
        this.errors = errors;
        this.additionalInfo = additionalInfo;
    }

    public ErrorResponse(ErrorDetails error) {
        this.errors = new ArrayList();
        this.errors.add(error);
    }

    public ErrorResponse(ErrorDetails error, Map<String, String> additionalInfo) {
        this.errors = new ArrayList();
        this.errors.add(error);
        this.additionalInfo = additionalInfo;
    }
}