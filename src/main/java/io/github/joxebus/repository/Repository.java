package io.github.joxebus.repository;

import java.util.List;

import io.github.joxebus.entity.TableEntity;


public interface Repository<T extends TableEntity> {

	void create(T objeto);

	T read(TableEntity objeto);

	void update(T objeto);

	void delete(T objeto);

	List<T> list(TableEntity objeto);

}