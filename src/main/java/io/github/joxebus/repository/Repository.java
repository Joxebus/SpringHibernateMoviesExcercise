package io.github.joxebus.repository;

import java.util.List;

import io.github.joxebus.entity.Entity;


public interface Repository<T> {

	void create(T objeto);

	T read(Entity objeto);

	void update(T objeto);

	void delete(T objeto);

	List<T> list(Entity objeto);

}