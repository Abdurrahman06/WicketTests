package org.abdurrahman.db;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

@Transactional
public interface IDao {
	<T> long countAll(Class<T> clazz);

	<T> long count(Class<T> clazz, Filter filter);

	<T, K extends Serializable> K save(T object);

	<T> void saveOrUpdate(T object);

	<T> void update(T object);

	<T> void merge(T object);

	<T> void delete(T object);

	<T> List<T> findAll(Class<T> clazz);

	<T> List<T> find(Class<T> clazz, Filter filter);

	List<?> runSql(String queryString);
}
