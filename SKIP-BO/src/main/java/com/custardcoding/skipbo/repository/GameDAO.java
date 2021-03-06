package com.custardcoding.skipbo.repository;

import com.custardcoding.skipbo.beans.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Kustardio
 */
@Repository
public class GameDAO extends HibernateGenericDAO<Game> {
    
    @Autowired
    private PileDAO pileDAO;
    
    @Autowired
    private PlayerDAO playerDAO;
    
    public void saveGame(Game game) {
        game.getPiles().values().forEach((pile) -> {
            pileDAO.makePersistent(pile);
        });
        
        game.getPlayers().values().forEach((player) -> {
            playerDAO.savePlayer(player);
        });
        
        makePersistent(game);
    }
}
