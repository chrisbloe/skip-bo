package com.custardcoding.skipbo.repository;

import com.custardcoding.skipbo.beans.Pile;
import com.custardcoding.skipbo.beans.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Kustardio
 */
@Repository
public class PlayerDAO extends HibernateGenericDAO<Player> {
    
    @Autowired
    private PileDAO pileDAO;
    
    public void savePlayer(Player player) {
        for (Pile pile : player.getPiles().values()) {
            pileDAO.makePersistent(pile);
        }
        
        makePersistent(player);
    }
}
