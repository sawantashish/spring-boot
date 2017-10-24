package com.spring.multitenancy.Multitenancy.dao;

import com.spring.multitenancy.Multitenancy.exception.ApplicationException;
import com.spring.multitenancy.Multitenancy.exception.ErrorCode;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


public class GenericDAO<E, PK extends Serializable> implements IGenericDAO<E, PK> {

    /**
     * Represents the hibernate session factory object.
     */
    protected SessionFactory sessionFactory;

    /**
     * Represents the entity class for which the DAO has been written.
     */
    private final Class<? extends E> _entityClass;

    /**
     * Represents the logger of the application.
     */
    private static final Logger logger = LoggerFactory.getLogger(GenericDAO.class);

    /**
     * Represents the constructor of the DAO object.
     * 
     * @param entityClass
     *            represents the calss for which DAO needs to be created.
     */
    public GenericDAO(final Class<? extends E> entityClass) {
        super();
        _entityClass = entityClass;
    }

    /**
     * Method returns the current Hibernate session for the thread.
     * 
     * @return Session Represents the Hibernate session.
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    protected Session getCrntSession() throws ApplicationException {
        try {
            return sessionFactory.getCurrentSession();
        } catch (final HibernateException e) {
            logger.error("getCrntSession: failed to complete operation");
            throw new ApplicationException("Failed to get thread session ", e);
        }
    }

    /**
     * Setter for the session factory.
     * 
     * @param sessionFactory
     *            Represents the hibernate session factory object.
     */
    @Autowired
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * This method is used to save entity into the database
     * 
     * @param newInstance
     *            Represents the instance to be saved.
     * 
     * @return PK Represents the primary key of the entity
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public PK save(final E newInstance) throws ApplicationException {
        PK id = null;
        final Session session = getCrntSession();
        try {
            logger.debug("save: Adding Object  - {}", newInstance);
            id = (PK) session.save(newInstance);
            logger.debug("save: Insertion completed successfully for  - {}", newInstance);
        } catch (final HibernateException e) {
            logger.error("save: Failed to insert " + newInstance + " successfully", e);
            throw new ApplicationException("Failed to insert  " + newInstance + " successfully",
                    ErrorCode.BASE_DB_ERROR, e);
        }
        return id;
    }

    /**
     * This method is used to update the entity into database.
     * 
     * @param entity
     *            Represents the instance to be updated.
     * 
     * @see Session#saveOrUpdate(Object)
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public void update(final E entity) throws ApplicationException {
        try {
            getCrntSession().update(entity);
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while refresh entity ", ErrorCode.BASE_DB_ERROR, e);
        }

    }

    /**
     * This method is used to save Entity into the database
     * 
     * @param object
     * @return id
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public PK saveEntity(Object newInstance) throws ApplicationException {
        PK id = null;
        final Session session = getCrntSession();
        try {
            logger.debug("save: Adding Object  - {}", newInstance);
            id = (PK) session.save(newInstance);
            logger.debug("save: Insertion completed successfully for  - {}", newInstance);
        } catch (final HibernateException e) {
            logger.error("save: Failed to insert " + newInstance + " successfully", e);
            throw new ApplicationException("Failed to insert  " + newInstance + " successfully",
                    ErrorCode.BASE_DB_ERROR, e);
        }
        return id;
    }

    /**
     * This method is used to update object into the database
     * 
     * @param newInstance
     *            Represents newInstance
     * 
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public void updateEntity(Object newInstance) throws ApplicationException {
        try {
            getCrntSession().update(newInstance);
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while refresh entity ", ErrorCode.BASE_DB_ERROR, e);
        }
    }

    /**
     * This method is used to save Or update all objects into the database
     * 
     * @param objects
     *            represents collection of objects
     * 
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public void saveOrUpdateAll(Collection<?> objects) throws ApplicationException {
        try {
            for (Object object : objects) {
                getCrntSession().saveOrUpdate(object);
            }
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while save or update All ", ErrorCode.BASE_DB_ERROR, e);
        }
    }

    /**
     * This method is used to get the particular row from database and its columns will mapped to the object describes
     * in the class signature E.
     * 
     * @param id
     *            Represents the primary key value for which objects needs to be fetched.
     * 
     * @return entity Represents the mapped obejct from database row.
     * 
     * @see Session#get(Class, Serializable)
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public E get(final PK id) throws ApplicationException {
        try {
            return (E) getCrntSession().get(_entityClass, id);
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while get entity ", ErrorCode.BASE_DB_ERROR, e);
        }
    }

    /**
     * This method is used to load the proxy for the object describes in the class signature E. This method never hit
     * the database until the property (except id property) of that object() is accessed by getter.
     * 
     * @param id
     *            Represents the id of the object for which the proxy needs to be load.
     * 
     * @return entity Represents the proxy object.
     * 
     * @see Session#load(Class, Serializable)
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public E load(final PK id) throws ApplicationException {
        try {
            return (E) getCrntSession().load(_entityClass, id);
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while load entity ", ErrorCode.BASE_DB_ERROR, e);
        }

    }

    /**
     * This method is used to load the proxy for the generic object.This method never hit the database until the
     * property (except id property) of that object() is accessed by getter.
     * 
     * @param classs
     *            Represents the class for which the proxy needs to be generated.
     * @param id
     *            Represents the id for which proxy needs to be generated.
     * 
     * @return V Represents the proxy object.
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public <V extends Object> V loadObject(final Class classs, final PK id) throws ApplicationException {
        try {
            return (V) getCrntSession().load(classs, id);
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while load entity ", ErrorCode.BASE_DB_ERROR, e);
        }

    }

    /**
     * This method is used to load the proxy for the generic object.This method never hit the database until the
     * property (except id property) of that object() is accessed by getter.
     * 
     * @param classs
     *            Represents the class for which the proxy needs to be generated.
     * @param id
     *            Represents the id for which proxy needs to be generated.
     * 
     * @return V Represents the proxy object.
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public <V extends Object> V loadMasterObject(final Class classs, Integer id) throws ApplicationException {
        try {
            return (V) getCrntSession().load(classs, id);
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while load entity ", ErrorCode.BASE_DB_ERROR, e);
        }

    }

    /**
     * This method is used to load the generic object from the database.
     * 
     * @param classs
     *            Represents the class for which the object needs to be fetched from database.
     * @param id
     *            Represents the id for which the object needs to be fetched from database.
     * 
     * @return V Represents the generic object.
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public <V extends Object> V getObject(final Class classs, final PK id) throws ApplicationException {
        try {
            return (V) getCrntSession().get(classs, id);
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while get entity ", ErrorCode.BASE_DB_ERROR, e);
        }
    }

    /**
     * This method is used to get the generic object from the database.
     * 
     * @param classs
     *            Represents the class for which the object needs to be fetched from database.
     * @param id
     *            Represents the id for which the object needs to be fetched from database.
     * 
     * @return V Represents the generic object.
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public <V extends Object> V getMasterObject(final Class classs, final Integer id) throws ApplicationException {
        try {
            return (V) getCrntSession().get(classs, id);
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while get entity ", ErrorCode.BASE_DB_ERROR, e);
        }
    }

    /**
     * This method is used to return the criteria object for the class describes in the class signature E..
     * 
     * @return Represents the generic criteria object.
     * 
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public Criteria getCriteria() throws ApplicationException {
        try {
            return getCrntSession().createCriteria(_entityClass);
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while creating criteria ", ErrorCode.BASE_DB_ERROR, e);
        }
    }

    /**
     * This method is used to return the criteria object for the class describes in the class signature E..
     * 
     * @return Represents the generic criteria object.
     * 
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public Criteria getCriteriaWithAlias(final String alias) throws ApplicationException {
        try {
            return getCrntSession().createCriteria(_entityClass, alias);
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while creating criteria ", ErrorCode.BASE_DB_ERROR, e);
        }
    }

    /**
     * This method is used to get the Criteria object with pagination values.
     * 
     * @param entityclass
     * @param firstResult
     *            Represents the starting point for the result set .
     * @param maxResults
     *            Represents the max number for records to be fetched
     * 
     * @return Criteria Represents the generic criteria object.
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public Criteria getCriteriaWithPagination(Class entityclass, int firstResult, int maxResults)
            throws ApplicationException {
        try {

            Criteria criteria = getCrntSession().createCriteria(entityclass);
            ;
            criteria.setFirstResult(firstResult);
            criteria.setMaxResults(maxResults);
            return criteria;
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while creating criteria with pagination",
                    ErrorCode.BASE_DB_ERROR, e);
        }
    }

    /**
     * This method is used to get the Criteria object with pagination values.
     * 
     * @param entityclass
     * @param firstResult
     *            Represents the starting point for the result set .
     * @param maxResults
     *            Represents the max number for records to be fetched
     * 
     * @return Criteria Represents the generic criteria object.
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public Criteria getCriteriaWithPaginationAndAlias(Class entityclass, final String alias, int firstResult,
            int maxResults) throws ApplicationException {
        try {

            Criteria criteria = getCrntSession().createCriteria(entityclass, alias);
            criteria.setFirstResult(firstResult);
            criteria.setMaxResults(maxResults);
            return criteria;
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while creating criteria with pagination",
                    ErrorCode.BASE_DB_ERROR, e);
        }
    }

    /**
     * This method is used to get the Generic Criteria object.
     * 
     * @param entityclass
     *            Represents the class for which Criteria object needs be generated.
     * 
     * @return Criteria Represents the generic criteria object.
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public Criteria getCriteria(final Class entityclass) throws ApplicationException {
        try {
            return getCrntSession().createCriteria(entityclass);
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while creating criteria ", ErrorCode.BASE_DB_ERROR, e);
        }
    }

    /**
     * This method is used to get the Generic Criteria object.
     * 
     * @param entityclass
     *            Represents the class for which Criteria object needs be generated.
     * 
     * @return Represents the generic criteria object.
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public Criteria getCriteriaWithAlias(final Class entityclass, final String alias) throws ApplicationException {
        try {
            return getCrntSession().createCriteria(entityclass, alias);
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while creating criteria ", ErrorCode.BASE_DB_ERROR, e);
        }
    }

    /**
     * This method is used to execute the query of the Criteria object.
     * 
     * @param criteria
     *            Represents the criteria object which is going to execute the query.
     * 
     * @return List Represents the results return from the query.
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    @Deprecated
    public <V> List<V> executeCriteira(final Criteria criteria) throws ApplicationException {
        try {
            return criteria.list();
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while executing criteria " + criteria, ErrorCode.BASE_DB_ERROR, e);
        }
    }
    
    @Override
    
    public <V> List<V> executeCriteria(final Criteria criteria) throws ApplicationException {
        try {
            return criteria.list();
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while executing criteria " + criteria, ErrorCode.BASE_DB_ERROR, e);
        }
    }

    /**
     * This method is used to get the generic object from database with all mentioned associations.
     * 
     * @param entityClass
     *            Represents the Class for which the object needs to fetch from database.
     * @param entityId
     *            Represents the id for which the obejct needs to be fetch from database.
     * @param entityAssociations
     *            Represents the associations of the object needs to be load with the object.
     * 
     * @return V Represents the Object fetched from database.
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public <V extends Object> V getEntityWithAssociations(Class entityClass, PK entityId, String... entityAssociations)
            throws ApplicationException {

        V dataObject = null;
        final Criteria criteria = getCriteria(entityClass);
        try {
            for (final String associationName : entityAssociations) {
                criteria.createAlias(associationName, associationName, JoinType.LEFT_OUTER_JOIN);
            }
            criteria.add(Restrictions.eq("id", entityId));
            final List<V> entityList = executeCriteira(criteria);
            if (entityList.size() > 0) {
                dataObject = entityList.get(0);
            }
        } catch (final HibernateException e) {
            logger.error("getEntityWithAssociations : failed to get entity class for entityId {}", entityId);
            throw new ApplicationException("Exception while executing getEntityWithAssociations " + criteria,
                    ErrorCode.BASE_DB_ERROR, e);
        }
        return dataObject;
    }

    /**
     * This method is used to get the generic object from database with all mentioned associations.
     * 
     * @param entityClass
     *            Represents the Class for which the object needs to fetch from database.
     * @param entityIds
     *            Represents the id for which the obejct needs to be fetch from database.
     * @param entityAssociations
     *            Represents the associations of the object needs to be load with the object.
     * 
     * @return V Represents the Object fetched from database.
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public <V> List<V> getEntitiesWithAssociations(Class entityClass, List<PK> entityIds, String... entityAssociations)
            throws ApplicationException {

        List<V> entityList = null;
        final Criteria criteria = getCriteria(entityClass);
        try {
            for (final String associationName : entityAssociations) {
                criteria.createAlias(associationName, associationName, JoinType.LEFT_OUTER_JOIN);
            }
            criteria.add(Restrictions.in("id", entityIds));
            entityList = executeCriteira(criteria);
        } catch (final HibernateException e) {
            logger.error("getEntityWithAssociations : failed to get entity class for entityId {}", entityIds);
            throw new ApplicationException("Exception while executing getEntityWithAssociations " + criteria,
                    ErrorCode.BASE_DB_ERROR, e);
        }
        return entityList;
    }

    /**
     * This method is used to get Query object for Hibernate Query.
     * 
     * @param hql
     *            Represents the Hibernate Query for which the Query object needs to fetched.
     * 
     * @return Represents the Query object.
     * 
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public Query getQueryObject(final String hql) throws ApplicationException {
        try {
            Query query = getCrntSession().createQuery(hql);
            return query;
        } catch (final HibernateException e) {
        	logger.info("exception is ", e);
            throw new ApplicationException("Exception while creating query object " + hql, ErrorCode.BASE_DB_ERROR, e);
        }

    }

    /**
     * This method is used to get Query object for SQL Query.
     * 
     * @param sql
     *            Represents the Hibernate Query for which the Query object needs to fetched.
     * 
     * @return Represents the Query object.
     * 
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public Query getSQLQueryObject(final String sql) throws ApplicationException {
        try {
            Query query = getCrntSession().createSQLQuery(sql);
            return query;
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while creating sql query object " + sql, ErrorCode.BASE_DB_ERROR,
                    e);
        }

    }

    /**
     * This method is used to get Query object for named Query in hibernate.
     * 
     * @param namedQuery
     * 
     * @return Represents the Query object.
     * @throws ApplicationException
     *             throws in case of query or DB Error
     */
    @Override
    public Query getQueryFromNamedQuery(String namedQuery) throws ApplicationException {

        try {
            return getCrntSession().getNamedQuery(namedQuery);
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while executing named HQL query " + namedQuery,
                    ErrorCode.BASE_DB_ERROR, e);
        }
    }

    /**
     * This method is used to get the results from the Hibernate DDL Query object.
     * 
     * @param query
     *            Represents the hibernate query object for which the results needs to be fetched.
     * 
     * @return List Represents the result returns from the database.
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public <V> List<V> executeQuery(final Query query) throws ApplicationException {
        try {
            return query.list();
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while executing hq query  " + query, ErrorCode.BASE_DB_ERROR, e);
        }

    }
    
    @Override
    public  <V extends Object> V executeUniqueResultQuery(final Query query) throws ApplicationException {
    	try {
    		return (V) query.uniqueResult();
        } catch (final HibernateException e) {
            throw new ApplicationException("Result should be unique  " + query, ErrorCode.BASE_DB_ERROR, e);
        }
    	
    }

    /**
     * This method is used to execute Hibernate DML Query object.
     * 
     * @param query
     *            Represents the hibernate query object for which the results needs to be fetched.
     * 
     * @return represents number of rows inserted/updated or deleted.
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public int executeHQLDMLQuery(final Query query) throws ApplicationException {
        try {
            return query.executeUpdate();
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while executing hq query  " + query, ErrorCode.BASE_DB_ERROR, e);
        }

    }

    /**
     * This method is used to get Query object for SQL Query.
     * 
     * @param sql
     *            Represents the Hibernate Query for which the Query object needs to fetched.
     * 
     * @return Represents the Query object.
     * 
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    @Override
    public SQLQuery getSQLQueryObj(final String sql) throws ApplicationException {
        try {
            SQLQuery sqlQuery = getCrntSession().createSQLQuery(sql);
            return sqlQuery;
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while creating sql query object " + sql, ErrorCode.BASE_DB_ERROR,
                    e);
        }

    }

    @Override
    public void delete(E entity) throws ApplicationException {

        try {
            logger.debug("delete:  Object  - " + entity);
            getCrntSession().delete(entity);
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while executing delete query ", ErrorCode.BASE_DB_ERROR);
        }
        logger.debug("delete: Operation completed successfully for  - " + entity);
    }
    
    /**
     * This method is used to load the generic object from the database.
     * 
     * @param classs
     *            Represents the class for which the object needs to be fetched from database.
     * @param id
     *            Represents the id for which the object needs to be fetched from database.
     * 
     * @return V Represents the generic object.
     * @throws ApplicationException
     *             throws in case of query or DB Error.
     */
    
    @Override
    public <V extends Object> V getSerialzableObject(final Class classs, final Serializable id) throws ApplicationException {
        try {
            return (V) getCrntSession().get(classs, id);
        } catch (final HibernateException e) {
            throw new ApplicationException("Exception while get entity ", ErrorCode.BASE_DB_ERROR, e);
        }
    }

}
