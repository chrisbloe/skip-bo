package com.custardcoding.skipbo.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Custard
 */
@Entity
public class Pile implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Fetch(FetchMode.SELECT)
    private List<Card> cards;
    
    public Pile() {
        cards = new ArrayList<>();
    }
    
    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
    
    public void addCard(Card card) {
        cards.add(card);
    }
    
    public void addCards(List<Card> cards) {
        cards.addAll(cards);
    }
    
    @JsonIgnore
    public Card getTopCard() {
        return cards.isEmpty() ? null
                               : cards.get(cards.size()-1);
    }
    
    @JsonIgnore
    public Card getTopCardEquivalent() {
        return Card.getEquivalentCard(cards.size());
    }
    
    @JsonIgnore
    public Card removeTopCard() {
        return cards.remove(cards.size()-1);
    }
    
    public void playCard(Card newCard) {
        cards.add(newCard);
    }
    
    @JsonIgnore
    public List<Card> emptyPile() {
        List<Card> allCards = new ArrayList<>();
        
        while (!cards.isEmpty()) {
            allCards.add(removeTopCard());
        }
        
        return allCards;
    }
    
    @JsonIgnore
    public int getSize() {
        return cards.size();
    }
    
    public void shuffle() {
        Collections.shuffle(cards);
    }
    
    @JsonIgnore
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
