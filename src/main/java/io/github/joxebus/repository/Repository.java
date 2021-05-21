package io.github.joxebus.repository;

import java.util.List;

import io.github.joxebus.entity.TableEntity;

/**
 * This entity provide all the methods required to
 * perform CRUD operations, the implementation of this
 * methods are on the GenericRepository class
 * @param <T>
 */
public interface Repository<T extends TableEntity> {

	void create(T objeto);

	T read(TableEntity objeto);

	void update(T objeto);

	void delete(T objeto);

	List<T> list(Class<T> clazz);

}