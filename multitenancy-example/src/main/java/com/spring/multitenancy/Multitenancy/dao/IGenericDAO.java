
package com.spring.multitenancy.Multitenancy.dao;

import com.spring.multitenancy.Multitenancy.exception.ApplicationException;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;



/**
 * A base interface for all DAOs. This defines the common methods present in all DAOs. 
 * All other DAOs must extend from this DAO.
 * 
 * @author Devang.Ghugharawala
 * 
 * @param <E>  Represents the generic Entity class for which the DAO needs to be returned.
 * @param <PK> Represents the type of primary Key class inside the database.
 * 
 */
@SuppressWarnings({ "rawtypes" })
public interface IGenericDAO<E, PK extends Serializable> {

	/**
	 * This method is used to save entity into the database
	 * 
	 * @param newInstance Represents the instance to be saved.
	 * 
	 * @return PK Represents the primary key of the entity
	 * @throws ApplicationException throws in case of query or DB Error.
	 */
	public PK save(E newInstance) throws ApplicationException;

	/**
	 * This method is used to update the entity into database.
	 * 
	 * @param entity Represents the instance to be updated.
	 * 
	 * @see org.hibernate.Session#saveOrUpdate(Object)
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	void update(E entity) throws ApplicationException;
	
	/**
	 * This method is used to save object into the database
	 * @param object
	 * @return id
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	public PK saveEntity(Object object) throws ApplicationException;
	
	/**
	 * This method is used to update object into the database
	 * @param newInstance
	 * 
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	public void updateEntity(Object newInstance) throws ApplicationException;
	
	/**
	 * This method is used to save Or update all objects into the database
	 * @param objects represents collection of objects
	 * 
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	public void saveOrUpdateAll(Collection<?> objects) throws ApplicationException;

	
	/**
	 * This method is used to get the particular row from database and its columns will mapped to
	 * the object describes in the class signature E.
	 * 
	 * @param id 	  Represents the primary key value for which objects needs to be fetched.
	 * 
	 * @return entity Represents the mapped obejct from database row.
	 * 
	 * @see org.hibernate.Session#get(Class, Serializable)
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	E get(PK id) throws ApplicationException;
	
	/**
	 * This method is used to load the proxy for the object describes in the class signature E. This method 
	 * never hit the database until the property (except id property) of that object() is accessed by getter.
	 * 
	 * @param id	  Represents the id of the object for which the proxy needs to be load.
	 * 
	 * @return entity Represents the proxy object.
	 * 
	 * @see org.hibernate.Session#load(Class, Serializable)
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	E load(PK id) throws ApplicationException;
	
	/**
	 * This method is used to load the proxy for the generic object.This method 
	 * never hit the database until the property (except id property) of that object() is accessed by getter.
	 * 
	 * @param classs Represents the class for which the proxy needs to be generated.
	 * @param id	 Represents the id for which proxy needs to be generated.
	 * 
	 * @return V 	 Represents the proxy object.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	<V extends Object>  V  loadObject(final Class entityClass, final PK id) throws ApplicationException;
	
	/**
	 * This method is used to load the proxy for the generic object.This method 
	 * never hit the database until the property (except id property) of that object() is accessed by getter.
	 * 
	 * @param classs Represents the class for which the proxy needs to be generated.
	 * @param id	 Represents the id for which proxy needs to be generated.
	 * 
	 * @return V 	 Represents the proxy object.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	<V extends Object>  V  loadMasterObject(final Class classs, Integer id) throws ApplicationException;
	
	/**
	 * This method is used to load the generic object from the database.
	 * 
	 * @param classs  Represents the class for which the object needs to be fetched from database.
	 * @param id	  Represents the id for which the object needs to be fetched from database.
	 * 
	 * @return V	  Represents the generic object.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	<V extends Object>  V  getObject(final Class entityClass, final PK id) throws ApplicationException;
	
	/**
	 * This method is used to get the generic object from the database.
	 * 
	 * @param classs  Represents the class for which the object needs to be fetched from database.
	 * @param id	  Represents the id for which the object needs to be fetched from database.
	 * 
	 * @return V	  Represents the generic object.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	<V extends Object>  V  getMasterObject(final Class entityClass, final Integer id) throws ApplicationException;
	
	/**
	 * This method is used to return the criteria object for the class describes in the class signature E..
	 * 	
	 * @return Represents the generic criteria object.
	 * 
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	Criteria getCriteria() throws ApplicationException;
	
	/**
	 * This method is used to return the criteria object for the class describes in the class signature E..
	 * 	
	 * @return Represents the generic criteria object.
	 * 
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	Criteria getCriteriaWithAlias(String alias) throws ApplicationException;
	
	/**
	 * This method is used to get the Generic Criteria object.
	 * 
	 * @param entityclass Represents the class for which Criteria object needs be generated.
	 * 
	 * @return	Criteria  Represents the generic criteria object.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	Criteria getCriteria(final Class entityclass) throws ApplicationException;
	
	
	/**
	 * This method is used to get the Generic Criteria object.
	 * 
	 * @param entityclass Represents the class for which Criteria object needs be generated.
	 * 
	 * @return			  Represents the generic criteria object.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	Criteria getCriteriaWithAlias(final Class entityclass, String alias) throws ApplicationException;
	
	/**
	 * This method is used to get the Generic Criteria object with pagination values.
	 * @param entityclass
	 * @param firstResult
	 * @param maxResults
	 * 
	 * @return Criteria Represents the generic criteria object.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	Criteria getCriteriaWithPagination(final Class entityclass, final int firstResult, final int maxResults) throws ApplicationException;
	
	/**
	 * This method is used to get criteria object with pagination details.
	 * 
	 * @param entityclass
	 * @param alias
	 * @param firstResult
	 * @param maxResults
	 *
	 * @return Criteria Represents the generic criteria object.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	Criteria getCriteriaWithPaginationAndAlias(final Class entityclass, final String alias, final int firstResult, final int maxResults) throws ApplicationException;
	
	/**
	 * This method is used to execute the query of the Criteria object.
	 * 
	 * @param criteria  Represents the criteria object which is going to execute the query.
	 * 
	 * @return List     Represents the results return from the query.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	@Deprecated
	<V> List<V> executeCriteira(final Criteria criteria) throws ApplicationException;
	
	
	/**
	 * This method is used to execute the query of the Criteria object.
	 * 
	 * @param criteria  Represents the criteria object which is going to execute the query.
	 * 
	 * @return List     Represents the results return from the query.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	public <V> List<V> executeCriteria(Criteria criteria) throws ApplicationException;
	

	
	/**
	 * This method is used to get the generic object from database with all mentioned associations.
	 * 	
	 * @param entityClass		 Represents the Class for which the object needs to fetch from database.
	 * @param entityId			 Represents the id for which the obejct needs to be fetch from database.
	 * @param entityAssociations Represents the associations of the object needs to be load with the object.
	 * 
	 * @return V 				 Represents the Object fetched from database.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	<V extends Object> V getEntityWithAssociations(Class entityClass, final PK entityId, final String... entityAssociations) throws ApplicationException;
	
	/**
	 * This method is used to get multiple entities with association.
	 * 
	 * @param entityClass
	 * @param entityIds
	 * @param entityAssociations
	
	 * @return V 				 Represents the Object fetched from database.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	<V> List<V> getEntitiesWithAssociations(Class entityClass, List<PK> entityIds, final String... entityAssociations) throws ApplicationException;
	
	/**
	 * This method is used to get Query object for Hibernate Query.
	 * 
	 * @param hql Represents the Hibernate Query for which the Query object needs to fetched.
	 * 
	 * @return	Represents the Query object.
	 * 
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	Query getQueryObject(final String hql) throws ApplicationException;
	
	/**
	 * This method is used to get Query object for named Query in hibernate.
	 * @param namedQuery
	 * 
	 * @return Represents the Query object.
	 * @throws ApplicationException throws in case of query or DB Error
	 */
	Query getQueryFromNamedQuery(String namedQuery) throws ApplicationException;
	
	/**
	 * This method is used to get the results from the Hibernate DDL Query object.
	 * 
	 * @param query	Represents the hibernate query object for which the results needs to be fetched.
	 * 
	 * @return List Represents the result returns from the database.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	<V> List<V> executeQuery(final Query query) throws ApplicationException;
	
	/**
	 * This method is used to execute Hibernate DML Query object.
	 * 
	 * @param query	Represents the hibernate query object for which the results needs to be fetched.
	 * 
	 * @return represents number of rows inserted/updated or deleted.
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	int executeHQLDMLQuery(final Query query) throws ApplicationException;
	

	/**
	 * This method is used to get query object for the sql queries.
	 * 
	 * @param sql
	 * @return
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	Query getSQLQueryObject(String sql) throws ApplicationException;

	/**
	 * This method is used to get the SQLQuery object for sql query
	 * 
	 * @param sql
	 * @return
	 * @throws ApplicationException throws in case of query or DB Error. 
	 */
	SQLQuery getSQLQueryObj(String sql) throws ApplicationException;

	/**
	 * @param entity
	 * @throws ApplicationException
	 */
	void delete(E entity) throws ApplicationException;
	
	public <V extends Object> V executeUniqueResultQuery(final Query query) throws ApplicationException;

	 public <V extends Object> V getSerialzableObject(final Class classs, final Serializable id) throws ApplicationException;

	
}
