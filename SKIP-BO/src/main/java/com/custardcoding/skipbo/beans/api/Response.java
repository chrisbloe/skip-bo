package com.custardcoding.skipbo.beans.api;

import com.custardcoding.skipbo.beans.PlayerNumber;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author Custard
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private boolean endTurn, success;
    private String errorMessage;
    private PlayerNumber winner;
    
    public Response() { }

    public boolean isEndTurn() {
        return endTurn;
    }

    public void setEndTurn(boolean endTurn) {
        this.endTurn = endTurn;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public PlayerNumber getWinner() {
        return winner;
    }

    public void setWinner(PlayerNumber winner) {
        this.winner = winner;
    }
}
