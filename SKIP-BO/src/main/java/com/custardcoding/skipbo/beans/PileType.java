package com.custardcoding.skipbo.beans;

/**
 *
 * @author Custard
 */
public enum PileType {
    // Game
    DECK(PileArea.GAME, false, false), EXILE(PileArea.GAME, false, false),
    BUILD1(PileArea.GAME, false, true), BUILD2(PileArea.GAME, false, true),
    BUILD3(PileArea.GAME, false, true), BUILD4(PileArea.GAME, false, true),
    
    // Player
    DRAW(PileArea.PLAYER, true, false), HAND1(PileArea.PLAYER, true, false),
    HAND2(PileArea.PLAYER, true, false), HAND3(PileArea.PLAYER, true, false),
    HAND4(PileArea.PLAYER, true, false), HAND5(PileArea.PLAYER, true, false),
    DISCARD1(PileArea.PLAYER, true, true), DISCARD2(PileArea.PLAYER, true, true),
    DISCARD3(PileArea.PLAYER, true, true), DISCARD4(PileArea.PLAYER, true, true);
    
    private PileArea pileArea;
    private boolean playableFrom, playableTo;
    
    PileType(PileArea pileArea, boolean playableFrom, boolean playableTo) {
        this.pileArea = pileArea;
        this.playableFrom = playableFrom;
        this.playableTo = playableTo;
    }

    public PileArea getPileArea() {
        return pileArea;
    }

    public boolean isPlayableFrom() {
        return playableFrom;
    }

    public boolean isPlayableTo() {
        return playableTo;
    }
}
