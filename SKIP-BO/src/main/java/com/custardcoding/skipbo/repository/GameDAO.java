package com.custardcoding.skipbo.repository;

import com.custardcoding.skipbo.beans.Game;
import com.custardcoding.skipbo.beans.Pile;
import com.custardcoding.skipbo.beans.Player;
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
        for (Pile pile : game.getPiles().values()) {
            pileDAO.makePersistent(pile);
        }
        
        for (Player player : game.getPlayers().values()) {
            playerDAO.savePlayer(player);
        }
        
        makePersistent(game);
    }
}
