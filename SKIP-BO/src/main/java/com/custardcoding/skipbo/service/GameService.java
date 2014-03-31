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
        
        Map<PlayerNumber, Player> players = new EnumMap<PlayerNumber, Player>(PlayerNumber.class) {{
            put(PlayerNumber.ONE, new Player());
            put(PlayerNumber.TWO, new Player());
        }};
        
        game.createPiles();
        game.createDeck();
        game.setPlayers(players);
        game.setCurrentPlayerNumber((PlayerNumber) players.keySet().toArray()[new Random().nextInt(players.size())]);
        game.deal();
        
        gameDAO.saveGame(game);
        
        log.info("...game {} created, starting player {}", game.getId(), game.getCurrentPlayerNumber());
        
        return game;
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
        } else if (!fromPileType.isPlayableFrom() || game.getCurrentPlayer().getPile(fromPileType).isEmpty()) {
            FailureResponse response = new FailureResponse("Cannot play from here!");
            log.debug("Invalid from location", response);
            return response;
        } else if (!toPileType.isPlayableTo()) {
            FailureResponse response = new FailureResponse("Cannot play to here!");
            log.debug("Invalid to location", response);
            return response;
        }
        
        Pile fromPile = game.getCurrentPlayer().getPile(fromPileType);
        
        if (toPileType.getPileArea().equals(PileArea.GAME)) {
            Card toPileTopCard = game.getPile(toPileType).getTopCard();
            
            if (fromPile.getTopCard().canPlayOn(toPileTopCard)) {
                game.getPile(toPileType).playCard(fromPile.removeTopCard());
                gameDAO.saveGame(game);
                
                log.debug("...card played");
                
                PlayerNumber winner = game.getWinner();
                
                return new SuccessResponse(winner != null, winner);
            } else {
                FailureResponse response = new FailureResponse("Cannot play to here!");
                log.debug("Invalid to location", response);
                return response;
            }
        } else {
            game.getCurrentPlayer().getPile(toPileType).playCard(fromPile.removeTopCard());
            gameDAO.saveGame(game);
            
            log.debug("...card played");
            
            return new SuccessResponse(true, game.getWinner());
        }
    }
}
