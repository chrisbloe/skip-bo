package com.custardcoding.skipbo.repository;

import java.util.List;

/**
 *
 * @author Andrew Morgan
 * @param <T>
 */
public interface InterfaceGenericDAO<T> {
    
    /**
     * 
     * @param databaseID
     * @return The element with the given db id.
     */
    T get(Long databaseID);
    
    /**
     * 
     * @return A list of all elements of the given type.
     */
    List<T> getAll();
    
    /**
     * Saves/updates the given entity.
     * 
     * @param entity
     */
    void makePersistent(T entity);
    
    /**
     * Deletes the given entity.
     * 
     * @param entity
     */
    void makeTransient(T entity);
}