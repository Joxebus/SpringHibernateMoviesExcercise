package io.github.joxebus.repository;

import org.springframework.stereotype.Repository;

import io.github.joxebus.entity.Movie;

/**
 * This class define a Repository to perform CRUD operations
 * for movies
 */
@Repository
public class MovieRepository extends GenericRepository<Movie> {
}
