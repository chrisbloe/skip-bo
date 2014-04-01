package com.custardcoding.skipbo.service;

import com.custardcoding.skipbo.beans.Card;
import com.custardcoding.skipbo.beans.Game;
import com.custardcoding.skipbo.beans.Pile;
import com.custardcoding.skipbo.beans.PileArea;
import com.custardcoding.skipbo.beans.PileType;
import com.custardcoding.skipbo.beans.Player;
import com.custardcoding.skipbo.beans.PlayerNumber;
import com.custardcoding.skipbo.beans.api.FailureResponse;
import com.custardcoding.skipbo.beans.api.Response;
import com.custardcoding.skipbo.beans.api.SuccessResponse;
import com.custardcoding.skipbo.repository.GameDAO;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Custard
 */
@Service
@Transactional
public class GameService {
    private static final Logger log = LogManager.getLogger(GameService.class);
    
    @Autowired
    private GameDAO gameDAO;

    public void setGameDAO(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }
    
    public Game startNewGame() {
        log.info("Creating new game...");
        
        Game game = new Game();
        deal(game);
        gameDAO.saveGame(game);
        
        log.info("...game {} created, starting player {}", game.getId(), game.getCurrentPlayerNumber());
        
        return game;
    }
    
    private void deal(Game game) {
        game.getPlayers().values().forEach((player) -> {
            Pile drawPile = player.getPile(PileType.DRAW);
            
            for (int i = 0; i < 30; i++) {
                drawPile.addCard(getTopDeckCard(game));
            }
            
            for (int i = 1; i < 6; i++) {
                player.getPile(PileType.valueOf("HAND" + i)).addCard(getTopDeckCard(game));
            }
        });
    }

    public Game retrieveGame(Long gameId) {
        log.debug("Retrieving game {}...", gameId);
        
        Game game = gameDAO.get(gameId);
        
        log.debug("...game {} retrieved", gameId);
        
        return game;
    }
    
    public Response playCard(Long gameId, PlayerNumber playerNumber, PileType fromPileType, PileType toPileType) {
        log.debug("Playing card (game {}, player {}, from {}, to {})...", gameId, playerNumber, fromPileType, toPileType);
        
        Game game = gameDAO.get(gameId);
        
        if (game == null) {
            FailureResponse response = new FailureResponse("Invalid game id!");
            log.error("Game " + gameId + " does not exist", response);
            return response;
        } else if (!game.isCurrentPlayer(playerNumber)) {
            FailureResponse response = new FailureResponse("Wrong player!");
            log.error("Wrong player (game " + gameId + ", player " + playerNumber + ')', response);
            return response;
        } else if (PileType.isGamePileArea(fromPileType) || game.getCurrentPlayer().getPile(fromPileType).isEmpty()) {
            FailureResponse response = new FailureResponse("Cannot play from here!");
            log.debug("Invalid from location", response);
            return response;
        } else if (!PileType.isBuildPileType(toPileType) && !PileType.isDiscardPileType(toPileType)) {
            FailureResponse response = new FailureResponse("Cannot play to here!");
            log.debug("Invalid to location", response);
            return response;
        }
        
        Pile fromPile = game.getCurrentPlayer().getPile(fromPileType);
        
        if (fromPile.isEmpty()) {
            FailureResponse response = new FailureResponse("Pile empty!");
            log.error("Emppty pile (game " + gameId + ", player " + playerNumber + ", pile " + fromPileType + ')', response);
            return response;
        }
        
        if (PileType.isGamePileArea(toPileType)) {
            Card toPileTopCard = game.getPile(toPileType).getTopCard();
            
            if (fromPile.getTopCard().canPlayOn(toPileTopCard)) {
                game.getPile(toPileType).playCard(fromPile.removeTopCard());
                
                // If the card played was the last from the hand of 5 cards,
                // replenish the hand
                if (isHandEmpty(game)) {
                    replenishHand(game);
                }
                
                endTurn(game);
                PlayerNumber winner = game.getWinner();
                gameDAO.saveGame(game);
                
                log.debug("...card played");
                
                return new SuccessResponse(winner != null, winner);
            } else {
                FailureResponse response = new FailureResponse("Cannot play to here!");
                log.debug("Invalid to location", response);
                return response;
            }
        } else {
            // Only play from the player's hand.
            if (PileType.isHandPileType(fromPileType)) {
                game.getCurrentPlayer().getPile(toPileType).playCard(fromPile.removeTopCard());
                endTurn(game);
                PlayerNumber winner = game.getWinner();
                gameDAO.saveGame(game);

                log.debug("...card played");

                return new SuccessResponse(true, winner);
            } else {
                FailureResponse response = new FailureResponse("Cannot play to here!");
                log.debug("Invalid to location", response);
                return response;
            }
        }
    }

    private void endTurn(Game game) {
        game.endTurn();
        replenishHand(game);
    }

    private boolean isHandEmpty(Game game) {
        for (Pile h : game.getCurrentPlayersHand()) {
            if (!h.isEmpty()) {
                return false;
            }
        }
        
        return true;
    }

    private void replenishHand(Game game) {
        game.getCurrentPlayersHand().forEach((h) -> {
            if (h.isEmpty()) {
                h.addCard(getTopDeckCard(game));
            }
        });
    }
    
    private Card getTopDeckCard(Game game) {
        Pile deck = game.getPile(PileType.DECK);
        
        if (deck.isEmpty()) {
            Pile exile = game.getPile(PileType.EXILE);
            deck.addCards(exile.emptyPile());
            deck.shuffle();
        }
        
        return deck.removeTopCard();
    }
}
