package com.custardcoding.skipbo.service;

import com.custardcoding.skipbo.beans.Card;
import com.custardcoding.skipbo.beans.Game;
import com.custardcoding.skipbo.beans.Pile;
import com.custardcoding.skipbo.beans.PileArea;
import com.custardcoding.skipbo.beans.PileType;
import com.custardcoding.skipbo.beans.Player;
import com.custardcoding.skipbo.beans.PlayerNumber;
import com.custardcoding.skipbo.beans.api.Response;
import com.custardcoding.skipbo.repository.GameDAO;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;
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
    
    @Autowired
    private GameDAO gameDAO;

    public void setGameDAO(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }
    
    public Game startNewGame() {
        Game game = new Game();
        
        Map<PlayerNumber, Player> players = new EnumMap<PlayerNumber, Player>(PlayerNumber.class) {{
            put(PlayerNumber.ONE, new Player());
            put(PlayerNumber.TWO, new Player());
        }};
        
        game.setPlayers(players);
        
        game.setCurrentPlayerNumber((PlayerNumber) players.keySet().toArray()[new Random().nextInt(players.size())]);
        
        game.deal();
        
        gameDAO.saveGame(game);
        
        return game;
    }

    public Game retrieveGame(Long gameId) {
        return gameDAO.get(gameId);
    }
    
    public Response playCard(Long gameId, PlayerNumber playerNumber, PileType fromPileType, PileType toPileType) {
        Game game = gameDAO.get(gameId);
        
        if (game == null) {
            Response response = new Response();
            response.setSuccess(false);
            response.setErrorMessage("Invalid game id!");
            return response;
        }
        
        if (!game.isCurrentPlayer(playerNumber)) {
            Response response = new Response();
            response.setSuccess(false);
            response.setErrorMessage("Wrong player!");
            return response;
        }
        
        if (!fromPileType.isPlayableFrom() || game.getCurrentPlayer().getPile(fromPileType).isEmpty()) {
            Response response = new Response();
            response.setSuccess(false);
            response.setErrorMessage("Cannot play from here!");
            return response;
        }
        
        if (!toPileType.isPlayableTo()) {
            Response response = new Response();
            response.setSuccess(false);
            response.setErrorMessage("Cannot play to here!");
            return response;
        }
        
        Response response = new Response();
        
        Pile fromPile = game.getCurrentPlayer().getPile(fromPileType);
        
        if (toPileType.getPileArea().equals(PileArea.GAME)) {
            Card toPileTopCard = game.getPile(toPileType).getTopCard();
            
            if (fromPile.getTopCard().canPlayOn(toPileTopCard)) {
                game.getPile(toPileType).playCard(fromPile.removeTopCard());
                response.setSuccess(true);
            } else {
                response.setSuccess(false);
                response.setErrorMessage("Cannot play to here!");
            }
            
            response.setEndTurn(false);
            response.setWinner(game.getWinner());
        } else {
            game.getCurrentPlayer().getPile(toPileType).playCard(fromPile.removeTopCard());
            response.setSuccess(true);
            response.setEndTurn(true);
            response.setWinner(game.getWinner());
        }
        
        gameDAO.saveGame(game);
        
        return response;
    }
}
