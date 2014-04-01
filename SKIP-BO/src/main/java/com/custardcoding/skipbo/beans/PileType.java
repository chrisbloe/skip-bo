package com.custardcoding.skipbo.beans;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Custard
 */
public enum PileType {
    // Game
    DECK(PileArea.GAME), EXILE(PileArea.GAME),
    BUILD1(PileArea.GAME), BUILD2(PileArea.GAME),
    BUILD3(PileArea.GAME), BUILD4(PileArea.GAME),
    
    // Player
    DRAW(PileArea.PLAYER), HAND1(PileArea.PLAYER),
    HAND2(PileArea.PLAYER), HAND3(PileArea.PLAYER),
    HAND4(PileArea.PLAYER), HAND5(PileArea.PLAYER),
    DISCARD1(PileArea.PLAYER), DISCARD2(PileArea.PLAYER),
    DISCARD3(PileArea.PLAYER), DISCARD4(PileArea.PLAYER);
    
    private final PileArea pileArea;
    
    PileType(PileArea pileArea) {
        this.pileArea = pileArea;
    }

    public PileArea getPileArea() {
        return pileArea;
    }
    
    public static boolean isGamePileArea(PileType pileType) {
        return pileType.getPileArea() == PileArea.GAME;
    }
    
    public static boolean isBuildPileType(PileType pileType) {
        return pileType == BUILD1 ||
               pileType == BUILD2 ||
               pileType == BUILD3 ||
               pileType == BUILD4;
    }
    
    public static boolean isDiscardPileType(PileType pileType) {
        return pileType == DISCARD1 ||
               pileType == DISCARD2 ||
               pileType == DISCARD3 ||
               pileType == DISCARD4;
    }
    
    public static boolean isHandPileType(PileType pileType) {
        return pileType == HAND1 ||
               pileType == HAND2 ||
               pileType == HAND3 ||
               pileType == HAND4 ||
               pileType == HAND5;
    }
    
    public static List<PileType> getHandPileTypes() {
        return new ArrayList<PileType>() {{
            add(HAND1);
            add(HAND2);
            add(HAND3);
            add(HAND4);
            add(HAND5);
        }};
    }
}
