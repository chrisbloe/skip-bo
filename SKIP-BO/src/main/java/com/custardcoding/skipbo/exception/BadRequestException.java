package com.custardcoding.skipbo.exception;

/**
 *
 * @author Kris Kustard Bloe
 */
public class BadRequestException extends AbstractRequestException {

    /**
     * 
     * @param gameId
     * @param requestError
     */
	public BadRequestException(Long gameId, RequestError requestError) {
		super(gameId, requestError);
	}
    
    /**
     * 
     * @param gameId
     * @param requestError
     * @param cause
     */
    public BadRequestException(Long gameId, RequestError requestError, Throwable cause) {
		super(gameId, requestError, cause);
	}
}
