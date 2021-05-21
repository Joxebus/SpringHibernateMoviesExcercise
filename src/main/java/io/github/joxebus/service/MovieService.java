package io.github.joxebus.service;

import java.util.List;

import io.github.joxebus.entity.Movie;

/**
 * The movie service is the interface that provide the methods
 * that will be available to be used, in this case expose the way
 * we can interact with the MovieServiceImplementation and allow us
 * to change in the future for other implementation that used this interface.
 *
 * Read more about SOLID principles.
 */
public interface MovieService {

    void create();

    void update(Long id);

    void delete(Long id);

    Movie search(Long id);

    @SuppressWarnings("unchecked")
    List<Movie> searchByGenre(String genre);

    @SuppressWarnings("unchecked")
    List<Movie> searchByLeadCharacter(String leadCharacter);

    @SuppressWarnings("unchecked")
    List<Movie> searchByDirector(String director);

    @SuppressWarnings("unchecked")
    List<Movie> searchByGenreAndLeadCharacter(String genre, String leadCharacter);

    @SuppressWarnings("unchecked")
    List<Movie> searchByGenreOrLeadCharacter(String genreOrLeadCharacter);

    List<Movie> list();
}
