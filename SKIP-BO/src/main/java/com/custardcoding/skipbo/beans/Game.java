package com.custardcoding.skipbo.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Custard
 */
@Entity
@JsonInclude(Include.NON_NULL)
public class Game implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Map<PileType, Pile> piles;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Map<PlayerNumber, Player> players;
    
    @Enumerated(EnumType.STRING)
    private PlayerNumber currentPlayerNumber;
    
    public Game() {
        createPiles();
        createDeck();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private void createPiles() {
        piles = new EnumMap<PileType, Pile>(PileType.class) {{
            put(PileType.DECK, new Pile());
            put(PileType.EXILE, new Pile());
            put(PileType.BUILD1, new Pile());
            put(PileType.BUILD2, new Pile());
            put(PileType.BUILD3, new Pile());
            put(PileType.BUILD4, new Pile());
        }};
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

    public void deal() {
        for (Player player : players.values()) {
            Pile deck = piles.get(PileType.DECK);
            Pile drawPile = player.getPile(PileType.DRAW);
            
            for (int i = 0; i < 30; i++) {
                drawPile.addCard(deck.removeTopCard());
            }
            
            for (int i = 1; i < 6; i++) {
                player.getPile(PileType.valueOf("HAND" + i)).addCard(deck.removeTopCard());
            }
        }
    }
    
    private void endTurn() {
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

    @JsonIgnore
    public Player getCurrentPlayer() {
        return players.get(currentPlayerNumber);
    }
    
    @JsonIgnore()
    public PlayerNumber getWinner() {
        for (PlayerNumber playerNumber : players.keySet()) {
            if (players.get(playerNumber).getPile(PileType.DRAW).isEmpty()) {
                return playerNumber;
            }
        }
        
        return null;
    }
}
