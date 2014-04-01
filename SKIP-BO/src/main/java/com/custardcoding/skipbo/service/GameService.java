package com.custardcoding.skipbo.service;

import com.custardcoding.skipbo.beans.Card;
import com.custardcoding.skipbo.beans.Game;
import com.custardcoding.skipbo.beans.Pile;
import com.custardcoding.skipbo.beans.PileType;
import com.custardcoding.skipbo.beans.PlayerNumber;
import com.custardcoding.skipbo.beans.api.FailureResponse;
import com.custardcoding.skipbo.beans.api.Response;
import com.custardcoding.skipbo.beans.api.SuccessResponse;
import com.custardcoding.skipbo.repository.GameDAO;
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
        
        log.info("...game {} created, starting player is {}", game.getId(), game.getCurrentPlayerNumber());
        
        return game;
    }
    
    private void deal(Game game) {
        log.trace("...dealing cards...");
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
        log.trace("{}: Retrieving game...", gameId);
        
        Game game = gameDAO.get(gameId);
        
        log.trace("{}: ...game retrieved", gameId);
        
        return game;
    }
    
    public Response playCard(Long gameId, PlayerNumber playerNumber, PileType fromPileType, PileType toPileType) {
        log.debug("{}: Playing card (player {}, from {}, to {})", gameId, playerNumber, fromPileType, toPileType);
        
        Game game = gameDAO.get(gameId);
        
        if (game == null) {
            FailureResponse response = new FailureResponse("Invalid game id!");
            log.error(gameId + ": Game does not exist", response);
            return response;
        } else if (!game.isCurrentPlayer(playerNumber)) {
            FailureResponse response = new FailureResponse("Wrong player!");
            log.error(gameId + ": It isn't " + playerNumber + "'s turn", response);
            return response;
        } else if (PileType.isGamePileArea(fromPileType)) {
            FailureResponse response = new FailureResponse("Cannot play from here!");
            log.debug(gameId + ": " + fromPileType + " does not belong to players", response);
            return response;
        } else if (game.getCurrentPlayer().getPile(fromPileType).isEmpty()) {
            FailureResponse response = new FailureResponse("Cannot play from here!");
            log.debug(gameId + ": " + fromPileType + " is empty", response);
            return response;
        } else if (!PileType.isBuildPileType(toPileType) && !PileType.isDiscardPileType(toPileType)) {
            FailureResponse response = new FailureResponse("Cannot play to here!");
            log.debug(gameId + ": Cannot play to " + toPileType, response);
            return response;
        }
        
        Pile fromPile = game.getCurrentPlayer().getPile(fromPileType);
        
        if (fromPile.isEmpty()) {
            FailureResponse response = new FailureResponse("Pile empty!");
            log.error(gameId + ": " + fromPileType + " is empty", response);
            return response;
        }
        
        if (PileType.isGamePileArea(toPileType)) {
            Pile toPile = game.getPile(toPileType);
            Card toPileTopCardEquivalent = toPile.getTopCardEquivalent();
            
            if (fromPile.getTopCard().canPlayOn(toPileTopCardEquivalent)) {
                if (toPile.getTopCard() == Card.SKIPBO) {
                    log.trace("{}: Playing a {} on a {} ({})", gameId, fromPile.getTopCard(), toPile.getTopCard(), toPileTopCardEquivalent);
                } else {
                    log.trace("{}: Playing a {} on a {}", gameId, fromPile.getTopCard(), toPile.getTopCard());
                }
                
                toPile.playCard(fromPile.removeTopCard());
                
                checkPiles(game);
                PlayerNumber winner = game.getWinner();
                gameDAO.saveGame(game);
                
                log.debug("{}: Card played", gameId);
                
                return new SuccessResponse(winner != null, winner);
            } else {
                if (toPile.getTopCard() == Card.SKIPBO) {
                    log.debug("{}: Cannot play a {} on a {} ({})", gameId, fromPile.getTopCard(), toPile.getTopCard(), toPileTopCardEquivalent);
                } else {
                    log.debug("{}: Cannot play a {} on a {}", gameId, fromPile.getTopCard(), toPile.getTopCard());
                }
                
                return new FailureResponse("Cannot play to here!");
            }
        } else {
            // Only play from the player's hand.
            if (PileType.isHandPileType(fromPileType)) {
                Pile toPile = game.getCurrentPlayer().getPile(toPileType);
                log.trace("{}: Playing a {} on a {}", gameId, fromPile.getTopCard(), toPile.getTopCard());
                toPile.playCard(fromPile.removeTopCard());
                endTurn(game);
                PlayerNumber winner = game.getWinner();
                gameDAO.saveGame(game);

                log.debug("{}: Card played", gameId);

                return new SuccessResponse(true, winner);
            } else {
                FailureResponse response = new FailureResponse("Cannot play to here!");
                log.debug(gameId + ": Cannot discard from " + fromPileType, response);
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
                log.trace("{}: {}'s hand is not empty", game.getId(), game.getCurrentPlayerNumber());
                return false;
            }
        }
        
        log.debug("{}: {}'s hand is empty, need to replenish", game.getId(), game.getCurrentPlayerNumber());
        return true;
    }

    private void replenishHand(Game game) {
        game.getCurrentPlayersHand().forEach((h) -> {
            if (h.isEmpty()) {
                h.addCard(getTopDeckCard(game));
            }
        });
        
        log.debug("{}: {}'s hand is replenished", game.getId(), game.getCurrentPlayerNumber());
    }
    
    private Card getTopDeckCard(Game game) {
        Pile deck = game.getPile(PileType.DECK);
        
        if (deck.isEmpty()) {
            log.debug("{}: Deck is empty, replenishing from exile...", game.getId());
            Pile exile = game.getPile(PileType.EXILE);
            deck.addCards(exile.emptyPile());
            deck.shuffle();
            log.debug("{}: ...deck now contains {} cards", game.getId(), deck.getSize());
        }
        
        return deck.removeTopCard();
    }

    private void checkPiles(Game game) {
        // Reset any completed build piles
        PileType.getBuildPileTypes().forEach(pileType -> {
            Pile pile = game.getPile(pileType);
            
            if (pile.getSize() == 12) {
                log.debug("{}: {} has reached 12 and is being reset", game.getId(), pileType);
                Pile exile = game.getPile(PileType.EXILE);
                exile.addCards(pile.emptyPile());
                log.debug("{}: Exile contains {} cards", game.getId(), exile.getSize());
            }
        });
        
        // If the card played was the last from the hand of 5 cards, replenish the hand
        if (isHandEmpty(game)) {
            replenishHand(game);
        }
    }
}
