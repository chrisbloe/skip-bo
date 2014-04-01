package com.custardcoding.skipbo.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Custard
 */
@Entity
@JsonInclude(Include.NON_NULL)
public class Game implements Serializable {
    private static final Logger log = LogManager.getLogger(Game.class);
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<PileType, Pile> piles;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<PlayerNumber, Player> players;
    
    @Enumerated(EnumType.STRING)
    private PlayerNumber currentPlayerNumber;
    
    @Column(nullable = false)
    private boolean complete;
    
    public Game() {
        piles = new EnumMap<PileType, Pile>(PileType.class) {{
            put(PileType.DECK, new Pile());
            put(PileType.EXILE, new Pile());
            put(PileType.BUILD1, new Pile());
            put(PileType.BUILD2, new Pile());
            put(PileType.BUILD3, new Pile());
            put(PileType.BUILD4, new Pile());
        }};
        
        players = new EnumMap<PlayerNumber, Player>(PlayerNumber.class) {{
            put(PlayerNumber.ONE, new Player());
            put(PlayerNumber.TWO, new Player());
        }};
        
        createDeck();
        
        currentPlayerNumber = (PlayerNumber) players.keySet().toArray()[new Random().nextInt(players.size())];
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void endTurn() {
        currentPlayerNumber = currentPlayerNumber.getNextPlayer();
    }
    
    public boolean isCurrentPlayer(PlayerNumber playerNumber) {
        return currentPlayerNumber == playerNumber;
    }

    public Map<PileType, Pile> getPiles() {
        return piles;
    }

    public Pile getPile(PileType pileType) {
        return piles.get(pileType);
    }

    public Map<PlayerNumber, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<PlayerNumber, Player> players) {
        this.players = players;
    }

    public PlayerNumber getCurrentPlayerNumber() {
        return currentPlayerNumber;
    }

    public void setCurrentPlayerNumber(PlayerNumber currentPlayerNumber) {
        this.currentPlayerNumber = currentPlayerNumber;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
    
    private void createDeck() {
        Pile deck = piles.get(PileType.DECK);
        
        for (int i = 0; i < 12; i++) {
            for (Card card : Card.values()) {
                deck.addCard(card);
            }
        }
        
        for (int i = 0; i < 6; i++) {
            deck.addCard(Card.SKIPBO);
        }
        
        deck.shuffle();
    }
    
    @JsonIgnore
    public Player getCurrentPlayer() {
        return players.get(currentPlayerNumber);
    }
    
    @JsonIgnore
    public List<Pile> getCurrentPlayersHand() {
        Player currentPlayer = getCurrentPlayer();
        
        return new ArrayList<Pile>() {{
            PileType.getHandPileTypes().forEach((type) -> {
                add(currentPlayer.getPile(type));
            });
        }};
    }
    
    public PlayerNumber getWinner() {
        for (PlayerNumber playerNumber : players.keySet()) {
            if (players.get(playerNumber).getPile(PileType.DRAW).isEmpty()) {
                log.info("Player {} wins game {}!", playerNumber, id);
                
                complete = true;
                
                return playerNumber;
            }
        }
        
        return null;
    }
}
