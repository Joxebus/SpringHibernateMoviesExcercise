package io.github.joxebus.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

import io.github.joxebus.repository.GenericRepository;
import io.github.joxebus.entity.Movie;
import io.github.joxebus.entity.Entity;
import io.github.joxebus.util.Utilities;

public class MovieService {
	private static final Logger logger = LoggerFactory.getLogger(MovieService.class);
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private GenericRepository<Movie> operaciones = new GenericRepository<>();
	private Movie movie;
	
	// Recolecci�n de datos
	public void leerDatosArchivo(){
		movie = Utilities.getContext().getBean("movie", Movie.class);
		logger.info("Registrar una nueva pel�cula");
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("D:\\Peliculas.txt")));
			String linea;
			while((linea = br.readLine())!=null){
				logger.info(linea.split("*")[0]);
				movie.setTitulo(linea.split("*")[0]);
				movie.setGenero(linea.split("*")[1]);
				movie.setProtagonista(linea.split("*")[2]);
				movie.setDirector(linea.split("*")[3]);
				//operaciones.create(movie);
			}
			for(Movie movieLista : list(movie)){
				logger.info(movieLista.toString());
			}
		} catch (IOException e) {			
			logger.info("El registro no se pudo llevar a cabo");
			e.printStackTrace();
		}
	}
	
	private void leerDatos(){
		logger.info("Registrar una nueva pel�cula");		
		try {
			System.out.print("Titulo: ");			
			movie.setTitulo(br.readLine());
			System.out.print("Genero: ");	
			movie.setGenero(br.readLine());
			System.out.print("Protagonista: ");	
			movie.setProtagonista(br.readLine());
			System.out.print("Director: ");	
			movie.setDirector(br.readLine());
		} catch (IOException e) {			
			logger.info("El registro no se pudo llevar a cabo");
		}
	}
	// Persistencia de estado
	public void agregar(){
		movie = Utilities.getContext().getBean("movie", Movie.class);
		leerDatos();
		operaciones.create(movie);
	}
	
	public void actualizar(){
		movie = Utilities.getContext().getBean("movie", Movie.class);
		try{
			logger.info("Ingrese el ID del movie");
			movie.setId(Long.parseLong(br.readLine()));
			movie = operaciones.read(movie);
			leerDatos();
			operaciones.update(movie);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}		
	}
	
	public void eliminar(){
		movie = Utilities.getContext().getBean("movie", Movie.class);
		try{
			logger.info("Ingrese el ID del movie");
			movie.setId(Long.parseLong(br.readLine()));
			movie = operaciones.read(movie);
			operaciones.delete(movie);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}	
	}
	
	public Movie buscar(){
		movie = Utilities.getContext().getBean("movie", Movie.class);
		try{
			logger.info("Ingrese el ID del movie");
			movie.setId(Long.parseLong(br.readLine()));
			movie = operaciones.read(movie);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		return movie;
	}
	
	@SuppressWarnings("unchecked")
	public List<Movie> buscarPorGenero(String genero){
		Session session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		return (List<Movie>) session.createCriteria(Movie.class)
		.add(Property.forName("genero").like(genero+"%"))
		.addOrder(Property.forName("titulo").asc())
		.setMaxResults(5).list();
		
	}
	@SuppressWarnings("unchecked")
	public List<Movie> buscarPorProtagonista(){
		String protagonista = "";
		try {
			System.out.print("Protagonista: ");	
			protagonista = br.readLine();							
		} catch (IOException e) {			
			logger.info("El registro no se pudo llevar a cabo");
		}
		Session session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		return (List<Movie>) session.createCriteria(Movie.class)
		.add(Property.forName("protagonista").like(protagonista+"%"))
		.addOrder(Property.forName("titulo").asc()).list();
	} 
	
	@SuppressWarnings("unchecked")
	public List<Movie> buscarPorDirector(){
		String director = "";
		try {
			System.out.print("Director: ");	
			director = br.readLine();							
		} catch (IOException e) {			
			logger.info("El registro no se pudo llevar a cabo");
		}
		Session session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		return (List<Movie>) session.createCriteria(Movie.class)
		.add(Property.forName("director").like(director+"%"))
		.setMaxResults(5).list();
	} 
	
	@SuppressWarnings("unchecked")
	public List<Movie> buscarPorGeneroProtagonista(){
		String genero="";
		String protagonista ="";
		try {
			System.out.print("Genero: ");	
			genero = br.readLine();
			System.out.print("Protagonista: ");	
			protagonista = br.readLine();					
		} catch (IOException e) {			
			logger.info("El registro no se pudo llevar a cabo");
		}
		Session session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		return (List<Movie>) session.createCriteria(Movie.class)
		.add(Property.forName("genero").like(genero+"%"))
		.add(Property.forName("protagonista").like(protagonista+"%"))
		.addOrder(Property.forName("titulo").asc())
		.setMaxResults(5).list();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Movie> buscarPorGeneroOrProtagonista(){
		String parametro="";
		try {
			System.out.print("Genero � Protagonista: ");	
			parametro = br.readLine();					
		} catch (IOException e) {			
			logger.info("El registro no se pudo llevar a cabo");
		}
		Session session = Utilities.getSessionFactory().getCurrentSession();
		Order orderTitulo = Property.forName("titulo").asc();
		Criterion critGen = Restrictions.like("genero",parametro+"%");
		Criterion critPro = Restrictions.like("protagonista",parametro+"%");
		LogicalExpression logicalExpression = Restrictions.or(critGen, critPro) ;
		session.beginTransaction();
		return (List<Movie>) session.createCriteria(Movie.class)
			.add(logicalExpression)
			.addOrder(orderTitulo)
			.setMaxResults(5).list();
		
	}
	
	public List<Movie> list(Entity entity){
		return operaciones.list(entity);
	}

}
