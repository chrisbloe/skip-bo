package com.custardcoding.skipbo.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Custard
 */
@Entity
public class Player implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<PileType, Pile> piles;
    
    public Player() {
        piles = new EnumMap<PileType, Pile>(PileType.class) {{
            put(PileType.DRAW, new Pile());
            put(PileType.HAND1, new Pile());
            put(PileType.HAND2, new Pile());
            put(PileType.HAND3, new Pile());
            put(PileType.HAND4, new Pile());
            put(PileType.HAND5, new Pile());
            put(PileType.DISCARD1, new Pile());
            put(PileType.DISCARD2, new Pile());
            put(PileType.DISCARD3, new Pile());
            put(PileType.DISCARD4, new Pile());
        }};
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pile getPile(PileType pileType) {
        return piles.get(pileType);
    }

    public Map<PileType, Pile> getPiles() {
        return piles;
    }
}
