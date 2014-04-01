package com.custardcoding.skipbo.beans;

/**
 *
 * @author Custard
 */
public enum Card {
    SKIPBO() {
        @Override
        public boolean canPlayOn(Card currentCard) {
            return true;
        }
    },
    
    ONE() {
        @Override
        public boolean canPlayOn(Card currentCard) {
            return currentCard == null;
        }
    },
    
    TWO() {
        @Override
        public boolean canPlayOn(Card currentCard) {
            return currentCard == ONE;
        }
    },
    
    THREE() {
        @Override
        public boolean canPlayOn(Card currentCard) {
            return currentCard == TWO;
        }
    },
    
    FOUR() {
        @Override
        public boolean canPlayOn(Card currentCard) {
            return currentCard == THREE;
        }
    },
    
    FIVE() {
        @Override
        public boolean canPlayOn(Card currentCard) {
            return currentCard == FOUR;
        }
    },
    
    SIX() {
        @Override
        public boolean canPlayOn(Card currentCard) {
            return currentCard == FIVE;
        }
    },
    
    SEVEN() {
        @Override
        public boolean canPlayOn(Card currentCard) {
            return currentCard == SIX;
        }
    },
    
    EIGHT() {
        @Override
        public boolean canPlayOn(Card currentCard) {
            return currentCard == SEVEN;
        }
    },
    
    NINE() {
        @Override
        public boolean canPlayOn(Card currentCard) {
            return currentCard == EIGHT;
        }
    },
    
    TEN() {
        @Override
        public boolean canPlayOn(Card currentCard) {
            return currentCard == NINE;
        }
    },
    
    ELEVEN() {
        @Override
        public boolean canPlayOn(Card currentCard) {
            return currentCard == TEN;
        }
    },
    
    TWELVE() {
        @Override
        public boolean canPlayOn(Card currentCard) {
            return currentCard == ELEVEN;
        }
    };
    
    public abstract boolean canPlayOn(Card currentCard);
    
    public static Card getEquivalentCard(int count) {
        switch (count) {
            case 1: return ONE;
            case 2: return TWO;
            case 3: return THREE;
            case 4: return FOUR;
            case 5: return FIVE;
            case 6: return SIX;
            case 7: return SEVEN;
            case 8: return EIGHT;
            case 9: return NINE;
            case 10: return TEN;
            case 11: return ELEVEN;
            default: return null;
        }
    }
}
