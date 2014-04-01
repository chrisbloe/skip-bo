package com.custardcoding.skipbo.beans.api;

import com.custardcoding.skipbo.exception.RequestError;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author Custard
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FailureResponse extends Response {
    private final String errorMessage;
    private final int errorCode;
    
    public FailureResponse(RequestError requestError) {
        success = false;
        this.errorMessage = requestError.getErrorString();
        this.errorCode = requestError.getErrorCode();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
