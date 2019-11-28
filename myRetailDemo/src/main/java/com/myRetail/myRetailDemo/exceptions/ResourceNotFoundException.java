package com.myRetail.myRetailDemo.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ResourceNotFoundException extends ServiceException {
    private static final String MAIN_MESSAGE = "The requested resource does not exist.";
    ErrorDetails error = new ErrorDetails(ErrorCode.RESOURCE_NOT_FOUND.name(),ErrorCode.RESOURCE_NOT_FOUND.getDescription());

    public ResourceNotFoundException() {
        super(MAIN_MESSAGE);
        Object additionalInfo = null;
        this.errorResponse = new ErrorResponse(error, (Map)additionalInfo);
    }

    public ResourceNotFoundException(String resourceId) {
        super(MAIN_MESSAGE);
        HashMap additionalInfo = null;
        if(resourceId != null) {
            additionalInfo = new HashMap();
            additionalInfo.put("resourceId", resourceId);
        }
        this.errorResponse = new ErrorResponse(error, additionalInfo);
    }

}