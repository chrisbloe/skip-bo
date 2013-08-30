package com.custardcoding.skipbo.beans;

/**
 *
 * @author Custard
 */
public enum PlayerNumber {
    ONE {
        @Override
        public PlayerNumber getNextPlayer() {
            return TWO;
        }
    },

    TWO {
        @Override
        public PlayerNumber getNextPlayer() {
            return ONE;
        }
    };

    public abstract PlayerNumber getNextPlayer();
}
