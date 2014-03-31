package com.custardcoding.skipbo.beans.api;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author Custard
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FailureResponse extends Response {
    private final String errorMessage;
    
    public FailureResponse(String errorMessage) {
        success = false;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
