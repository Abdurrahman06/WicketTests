package org.abdurrahman.db;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

// import org.abdurrahman.model.Person;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class Dao implements IDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public <T, K extends Serializable> K save(T object) {
		return (K) getSession().save(object);
	}

	@Override
	public <T> void update(T object) {
		getSession().update(object);
	}

	@Override
	public <T> void saveOrUpdate(T object) {
		getSession().saveOrUpdate(object);
	}

	@Override
	public <T> void merge(T object) {
		getSession().merge(object);
	}

	@Override
	public <T> void delete(T object) {
		getSession().delete(object);
	}

	@Override
	public <T> long countAll(Class<T> clazz) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		query.select(builder.count(query.from(clazz)));
		return getSession().createQuery(query).getSingleResult();
	}

	@Override
	public <T> long count(Class<T> clazz, Filter filter) {
		Criteria c = getSession().createCriteria(clazz);
		c.add(filter.getCriterion());
		c.setProjection(Projections.rowCount());
		Object count = c.uniqueResult();
		return count instanceof Long ? (Long) count : ((Integer) count).longValue();
	}

	@Override
	public <T> List<T> findAll(Class<T> clazz) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		Root<T> root = query.from(clazz);
		return getSession().createQuery(query.select(root)).getResultList();
	}

	@Override
	public <T> List<T> find(Class<T> clazz, Filter filter) {
		Criteria c = getSession().createCriteria(clazz);
		c.add(filter.getCriterion());
		return c.list();
	}

	@Override
	public List<?> runSql(String queryString) {
		NativeQuery query = getSession().createNativeQuery(queryString);
		return query.list();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}
