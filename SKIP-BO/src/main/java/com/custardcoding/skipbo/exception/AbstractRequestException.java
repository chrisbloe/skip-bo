package com.custardcoding.skipbo.exception;

import com.custardcoding.skipbo.beans.api.FailureResponse;

/**
 *
 * @author Kris Kustard Bloe
 */
public abstract class AbstractRequestException extends RuntimeException {
    protected final Long gameId;
    protected final RequestError requestError;
    
    /**
     * 
     * @param gameId
     * @param requestError
     */
	public AbstractRequestException(Long gameId, RequestError requestError) {
		super(requestError.getErrorString());
        this.gameId = gameId;
        this.requestError = requestError;
	}
    
    /**
     * 
     * @param gameId
     * @param requestError
     * @param cause
     */
    public AbstractRequestException(Long gameId, RequestError requestError, Throwable cause) {
		super(requestError.getErrorString(), cause);
        this.gameId = gameId;
        this.requestError = requestError;
	}

    public Long getGameId() {
        return gameId;
    }
    
    /**
     * 
     * @return FailureResponse
     */
    public FailureResponse getResponse() {
        return new FailureResponse(requestError);
    }
}
