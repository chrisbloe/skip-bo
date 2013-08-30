package com.custardcoding.skipbo.repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Andrew Morgan
 */
public abstract class HibernateGenericDAO<T> implements InterfaceGenericDAO<T> {
    
    @Autowired
    protected SessionFactory sessionFactory;
    protected Class<T> entityClass;
    
    /**
     * Default constructor.
     */
    public HibernateGenericDAO() {
        this.entityClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    /**
     * 
     * @return The sessionFactory.
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * 
     * @param sessionFactory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    /**
     * 
     * @param databaseID
     * @return The element with the given db id.
     */
    @Override
    public T get(Long databaseID) {
        return (T)sessionFactory.getCurrentSession().get(entityClass, databaseID);
    }
    
    /**
     * 
     * @return A list of all elements of the given type.
     */
    @Override
    public List<T> getAll() {
        return findByCriteria();
    }
    
    /**
     * Saves/updates the given entity.
     * 
     * @param entity
     */
    @Override
    public void makePersistent(T entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
    }
    
    /**
     * Deletes the given entity.
     * 
     * @param entity
     */
    @Override
    public void makeTransient(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }
    
    /**
     *
     * @param criterion A list of criteria
     * @return Elements of type T matching the criteria.
     */
    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(Criterion... criterion) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(entityClass);
        
        for (Criterion c : criterion) {
            crit.add(c);
        }
            
        return crit.list();
    }
    
    /**
     * 
     * @param orderByPropertyName
     * @param asc
     * @param criterion A list of criteria
     * @return Elements of type T matching the criteria.
     */
    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(String orderByPropertyName, boolean asc, Criterion... criterion) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(entityClass);
        
        if (asc) {
            crit.addOrder(Order.asc(orderByPropertyName));
        } else {
            crit.addOrder(Order.desc(orderByPropertyName));
        }
        
        for(Criterion c : criterion) {
            crit.add(c);
        }
            
        return crit.list();
    }
    
    /**
     * 
     * @param maxResults
     * @param orderByPropertyName
     * @param asc
     * @param criterion A list of criteria
     * @return Elements of type T matching the criteria.
     */
    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(int maxResults, String orderByPropertyName, boolean asc, Criterion... criterion) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(entityClass)
                                      .setMaxResults(maxResults);
        
        if (asc) {
            crit.addOrder(Order.asc(orderByPropertyName));
        } else {
            crit.addOrder(Order.desc(orderByPropertyName));
        }
        
        for(Criterion c : criterion) {
            crit.add(c);
        }
            
        return crit.list();
    }
    
    /**
     *
     * @param criterion A list of criteria
     * @return Unique element of type T matching the criteria.
     */
    @SuppressWarnings("unchecked")
    protected T findByCriteriaUniqueResult(Criterion... criterion) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(entityClass);
        
        for (Criterion c : criterion) {
            crit.add(c);
        }
        
        return (T)crit.uniqueResult();
   }
    
    /**
     * 
     * @param maxResults
     * @param orderByPropertyName
     * @param asc
     * @param criterion A list of criteria
     * @return Element of type T matching the criteria.
     */
    @SuppressWarnings("unchecked")
    protected T findByCriteriaUniqueResult(int maxResults, String orderByPropertyName, boolean asc, Criterion... criterion) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(entityClass)
                                      .setMaxResults(maxResults);
        
        if (asc) {
            crit.addOrder(Order.asc(orderByPropertyName));
        } else {
            crit.addOrder(Order.desc(orderByPropertyName));
        }
        
        for (Criterion c : criterion) {
            crit.add(c);
        }
            
        return (T)crit.uniqueResult();
    }
}