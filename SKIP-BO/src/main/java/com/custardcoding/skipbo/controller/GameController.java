package com.custardcoding.skipbo.controller;

import com.custardcoding.skipbo.beans.Game;
import com.custardcoding.skipbo.beans.PileType;
import com.custardcoding.skipbo.beans.PlayerNumber;
import com.custardcoding.skipbo.beans.api.Response;
import com.custardcoding.skipbo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Custard
 */
@RestController
public class GameController extends SkipboController {
    
    @Autowired
    private GameService gameService;
    
    @RequestMapping(value = "/startGame")
    public Game startGame() {
        return gameService.startNewGame();
    }
    
    @RequestMapping(value = "/retrieveGame/{gameId}")
    public Game retrieveGame(@PathVariable Long gameId) {
        return gameService.retrieveGame(gameId);
    }
    
    @RequestMapping(value = "/playCard/{gameId}/{playerNumber}/{fromPileType}/{toPileType}")
    public Response playCard(@PathVariable Long gameId, @PathVariable PlayerNumber playerNumber,
                                           @PathVariable PileType fromPileType, @PathVariable PileType toPileType) {
        return gameService.playCard(gameId, playerNumber, fromPileType, toPileType);
    }
}
