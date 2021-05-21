package io.github.joxebus.service;

import java.util.List;

import io.github.joxebus.entity.Movie;
import io.github.joxebus.entity.TableEntity;

public interface MovieService {
    // Persistencia de estado
    void create();

    void update();

    void delete();

    Movie search();

    @SuppressWarnings("unchecked")
    List<Movie> searchByGenre(String genero);

    @SuppressWarnings("unchecked")
    List<Movie> searchByLeadCharacter();

    @SuppressWarnings("unchecked")
    List<Movie> searchByDirector();

    @SuppressWarnings("unchecked")
    List<Movie> searchByGenderAndLeadCharacter();

    @SuppressWarnings("unchecked")
    List<Movie> searchByGenederOrLeadCharacter();

    List<Movie> list(TableEntity tableEntity);
}
