package com.custardcoding.skipbo.controller;

import com.custardcoding.skipbo.beans.api.FailureResponse;
import com.custardcoding.skipbo.exception.BadRequestException;
import com.custardcoding.skipbo.exception.RequestError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Custard
 */
public abstract class SkipboController {
    private static final Logger log = LogManager.getLogger(SkipboController.class);
	
    /**
     * 
     * @param e
     * @return FailureResponse
     */
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody FailureResponse badRequest(BadRequestException e) {
        log.debug(e.getGameId() + ": " + e.getMessage());
        return e.getResponse();
	}
    
    /**
     * For any other dodgy error.
     * 
     * @param e
     * @return FailureResponse
     */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody FailureResponse miscellaneousException(Exception e) {
        log.error("Unknown Internal server error - this is VERY BAD!", e);
        return new FailureResponse(RequestError.UNKNOWN_ERROR);
	}
}
