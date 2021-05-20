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
import io.github.joxebus.entity.TableEntity;
import io.github.joxebus.repository.MovieRepository;
import io.github.joxebus.util.Utilities;

@Service
public class MovieServiceImpl implements MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);
    private final BufferedReader bufferedReader;
    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    private void readData(Movie movie) {
        logger.info("Registrar una nueva película");
        try {
            System.out.print("Titulo: ");
            movie.setTitulo(bufferedReader.readLine());
            System.out.print("Genero: ");
            movie.setGenero(bufferedReader.readLine());
            System.out.print("Protagonista: ");
            movie.setProtagonista(bufferedReader.readLine());
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
    public void update() {
        Movie movie = new Movie();
        try {
            logger.info("Ingrese el ID de la película");
            movie.setId(Long.parseLong(bufferedReader.readLine()));
            movie = movieRepository.read(movie);
            readData(movie);
            movieRepository.update(movie);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void delete() {
        Movie movie = new Movie();
        try {
            logger.info("Ingrese el ID de la película");
            movie.setId(Long.parseLong(bufferedReader.readLine()));
            movie = movieRepository.read(movie);
            movieRepository.delete(movie);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public Movie search() {
        Movie movie = new Movie();
        try {
            logger.info("Ingrese el ID de la película");
            movie.setId(Long.parseLong(bufferedReader.readLine()));
            movie = movieRepository.read(movie);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return movie;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Movie> searchByGender(String genero) {
        Session session = Utilities.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        return (List<Movie>) session.createCriteria(Movie.class)
                .add(Property.forName("genero").like(genero + "%"))
                .addOrder(Property.forName("titulo").asc())
                .setMaxResults(5).list();

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Movie> searchByLeadCharacter() {
        String protagonista = "";
        try {
            System.out.print("Protagonista: ");
            protagonista = bufferedReader.readLine();
        } catch (IOException e) {
            logger.info("El registro no se pudo llevar a cabo");
        }
        Session session = Utilities.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        return (List<Movie>) session.createCriteria(Movie.class)
                .add(Property.forName("protagonista").like(protagonista + "%"))
                .addOrder(Property.forName("titulo").asc()).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Movie> searchByDirector() {
        String director = "";
        try {
            System.out.print("Director: ");
            director = bufferedReader.readLine();
        } catch (IOException e) {
            logger.info("El registro no se pudo llevar a cabo");
        }
        Session session = Utilities.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        return (List<Movie>) session.createCriteria(Movie.class)
                .add(Property.forName("director").like(director + "%"))
                .setMaxResults(5).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Movie> searchByGenderAndLeadCharacter() {
        String genero = "";
        String protagonista = "";
        try {
            System.out.print("Genero: ");
            genero = bufferedReader.readLine();
            System.out.print("Protagonista: ");
            protagonista = bufferedReader.readLine();
        } catch (IOException e) {
            logger.info("El registro no se pudo llevar a cabo");
        }
        Session session = Utilities.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        return (List<Movie>) session.createCriteria(Movie.class)
                .add(Property.forName("genero").like(genero + "%"))
                .add(Property.forName("protagonista").like(protagonista + "%"))
                .addOrder(Property.forName("titulo").asc())
                .setMaxResults(5).list();

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Movie> searchByGenederOrLeadCharacter() {
        String parametro = "";
        try {
            System.out.print("Genero o Protagonista: ");
            parametro = bufferedReader.readLine();
        } catch (IOException e) {
            logger.info("El registro no se pudo llevar a cabo");
        }
        Session session = Utilities.getSessionFactory().getCurrentSession();
        Order orderTitulo = Property.forName("titulo").asc();
        Criterion critGen = Restrictions.like("genero", parametro + "%");
        Criterion critPro = Restrictions.like("protagonista", parametro + "%");
        LogicalExpression logicalExpression = Restrictions.or(critGen, critPro);
        session.beginTransaction();
        return (List<Movie>) session.createCriteria(Movie.class)
                .add(logicalExpression)
                .addOrder(orderTitulo)
                .setMaxResults(5).list();

    }

    @Override
    public List<Movie> list(TableEntity tableEntity) {
        return movieRepository.list(tableEntity);
    }

}
