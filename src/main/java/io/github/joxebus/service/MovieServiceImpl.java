package io.github.joxebus.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.github.joxebus.entity.Movie;
import io.github.joxebus.repository.MovieRepository;
import io.github.joxebus.util.Utilities;

/**
 * This class contains the logic to interact with the
 * movie table, it allows to do CRUD operations and also some
 * search operations.
 *
 */
@Service
public class MovieServiceImpl implements MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);
    private final BufferedReader bufferedReader;
    private final MovieRepository movieRepository;

    /**
     * This constructor is used by Spring to inject the movieRepository dependency
     * see the src/main/resources/applicationContext.xml
     *
     * @param movieRepository
     */
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    private void readData(Movie movie) {
        logger.info("Registrar una nueva película");
        try {
            System.out.print("Titulo: ");
            movie.setTitle(bufferedReader.readLine());
            System.out.print("Genero: ");
            movie.setGenre(bufferedReader.readLine());
            System.out.print("Protagonista: ");
            movie.setLeadCharacter(bufferedReader.readLine());
            System.out.print("Director: ");
            movie.setDirector(bufferedReader.readLine());
        } catch (IOException e) {
            logger.info("El registro no se pudo llevar a cabo");
        }
    }

    // Persistencia de estado
    @Override
    public void create() {
        Movie movie = new Movie();
        readData(movie);
        movieRepository.create(movie);
    }

    @Override
    public void update(Long id) {
        Movie movie = new Movie();
        movie.setId(id);
        movie = movieRepository.read(movie);
        readData(movie);
        movieRepository.update(movie);
    }

    @Override
    public void delete(Long id) {
        Movie movie = new Movie();
        movie.setId(id);
        movie = movieRepository.read(movie);
        movieRepository.delete(movie);
    }

    @Override
    public Movie search(Long id) {
        Movie movie = new Movie();
        movie.setId(id);
        return movieRepository.read(movie);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Movie> searchByGenre(String genre) {
        Session session = Utilities.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        return (List<Movie>) session.createCriteria(Movie.class)
                .add(Property.forName("genre").like(genre + "%"))
                .addOrder(Property.forName("title").asc())
                .setMaxResults(5).list();

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Movie> searchByLeadCharacter(String leadCharacter) {
        Session session = Utilities.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        return (List<Movie>) session.createCriteria(Movie.class)
                .add(Property.forName("leadCharacter").like(leadCharacter + "%"))
                .addOrder(Property.forName("title").asc()).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Movie> searchByDirector(String director) {
        Session session = Utilities.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        return (List<Movie>) session.createCriteria(Movie.class)
                .add(Property.forName("director").like(director + "%"))
                .setMaxResults(5).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Movie> searchByGenreAndLeadCharacter(String genre, String leadCharacter) {
        Session session = Utilities.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        return (List<Movie>) session.createCriteria(Movie.class)
                .add(Property.forName("genre").like(genre + "%"))
                .add(Property.forName("leadCharacter").like(leadCharacter + "%"))
                .addOrder(Property.forName("title").asc())
                .setMaxResults(5).list();

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Movie> searchByGenreOrLeadCharacter(String genreOrLeadCharacter) {
        Session session = Utilities.getSessionFactory().getCurrentSession();
        Order orderTitulo = Property.forName("title").asc();
        Criterion critGen = Restrictions.like("genre", genreOrLeadCharacter + "%");
        Criterion critPro = Restrictions.like("leadCharacter", genreOrLeadCharacter + "%");
        LogicalExpression logicalExpression = Restrictions.or(critGen, critPro);
        session.beginTransaction();
        return (List<Movie>) session.createCriteria(Movie.class)
                .add(logicalExpression)
                .addOrder(orderTitulo)
                .setMaxResults(5).list();

    }

    @Override
    public List<Movie> list() {
        return movieRepository.list(Movie.class);
    }

}
