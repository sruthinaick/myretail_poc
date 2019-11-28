package com.myRetail.myRetailDemo.exceptions;


import io.swagger.annotations.ApiModel;
import java.net.URI;

@ApiModel
public class ErrorDetails {
    public String errorCode;
    public String message;
    public URI moreInfo;


    public ErrorDetails(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ErrorDetails(String errorCode, String message, URI moreInfo) {
        this.errorCode = errorCode;
        this.message = message;
        this.moreInfo = moreInfo;
    }
}