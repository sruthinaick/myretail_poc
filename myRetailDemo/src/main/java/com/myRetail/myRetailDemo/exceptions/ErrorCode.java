package com.myRetail.myRetailDemo.exceptions;

public enum ErrorCode {
    DATA_ACCESS_RESOURCE_FAILURE("data.access.resource.failure", "Failure in Database connection"),
    RESOURCE_NOT_FOUND("resource.not.found","The Resource you are trying to access is not found:"),
    PRODUCT_ALREADY_EXISTS("product.already.exists","The product you are trying to save already exists."),
    SERVICE_UNAVAILABLE("service.unavailable","Please verify that service is up and running or service URL is correct");

    private final String code;
    private final String description;

    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}