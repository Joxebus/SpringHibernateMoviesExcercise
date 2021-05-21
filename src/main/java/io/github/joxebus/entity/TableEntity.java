package io.github.joxebus.entity;

/**
 * This interface is used to identify the classes that
 * are entities, we use this to have the GenericRepository
 * compatible with the different clases that can be interacting
 * with the database.
 *
 * This is not an standard and this is only used to represent
 * how Spring Data Repositories works.
 */
public interface TableEntity {
	long getId();
}
