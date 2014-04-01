/*
 * Copyright (c) Costcutter 2014.
 */
package com.custardcoding.skipbo.exception;

/**
 *
 * @author Kris Bloe
 */
public enum RequestError {
    INVALID_GAME_ID("This game id does not exist", 1),
    WRONG_PLAYER("It's not your turn", 2),
    WRONG_FROM_PILE("You cannot play from this pile", 3),
    FROM_PILE_EMPTY("You cannot play from an empty pile", 4),
    WRONG_TO_PILE("You cannot play to this pile", 5),
    NOT_SEQUENTIAL("You can only play a card one higher than the current card, a 1 on a blank space or a SKIPBO card", 6),
    INVALID_PLAY("You cannot do this!", 7),
    UNKNOWN_ERROR("Unknown internal error", 500);
    
    private final String errorString;
    private final int errorCode;
    
    RequestError(String errorString, int errorCode) {
        this.errorString = errorString;
        this.errorCode = errorCode;
    }

    public String getErrorString() {
        return errorString;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
