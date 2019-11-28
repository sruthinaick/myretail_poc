package com.myRetail.myRetailDemo.exceptions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import java.util.Map;

@JsonTypeInfo(
        use = Id.CLASS,
        include = As.PROPERTY,
        property = "@class"
)
public class ServiceException extends RuntimeException {
    public ErrorResponse errorResponse;
    public static final String mainMessage = "There is a Service exception while processing the request";
    protected ServiceException(String mainMessage) {
        super(mainMessage);
    }

    public ServiceException(ErrorResponse errorResponse) {
        super(mainMessage);
        this.errorResponse = errorResponse;
    }

    public ServiceException(String errorCode, String errorMessage) {
        super(mainMessage);
        ErrorDetails details = new ErrorDetails(errorCode, errorMessage);
        this.errorResponse = new ErrorResponse(details);
    }

    protected ServiceException(String errorCode, String errorMessage, Map<String, String> additionalInfo) {
        super(mainMessage);
        ErrorDetails details = new ErrorDetails(errorCode, errorMessage);
        this.errorResponse = new ErrorResponse(details, additionalInfo);
    }
}