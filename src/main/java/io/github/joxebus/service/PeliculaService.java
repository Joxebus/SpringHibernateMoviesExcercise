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

import io.github.joxebus.dao.OperacionesCRUD;
import io.github.joxebus.util.Persistible;
import io.github.joxebus.util.Utilities;
import io.github.joxebus.entity.Pelicula;

public class PeliculaService {
	private static final Logger logger = LoggerFactory.getLogger(PeliculaService.class);
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private OperacionesCRUD<Pelicula> operaciones = new OperacionesCRUD <>();
	private Pelicula pelicula;
	
	// Recolecci�n de datos
	public void leerDatosArchivo(){
		pelicula = Utilities.getContext().getBean("pelicula", Pelicula.class);
		logger.info("Registrar una nueva pel�cula");
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("D:\\Peliculas.txt")));
			String linea;
			while((linea = br.readLine())!=null){
				logger.info(linea.split("*")[0]);
				pelicula.setTitulo(linea.split("*")[0]);
				pelicula.setGenero(linea.split("*")[1]);
				pelicula.setProtagonista(linea.split("*")[2]);
				pelicula.setDirector(linea.split("*")[3]);		
				//operaciones.create(pelicula);
			}
			for(Pelicula peliculaLista : list(pelicula)){
				logger.info(peliculaLista.toString());
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
			pelicula.setTitulo(br.readLine());
			System.out.print("Genero: ");	
			pelicula.setGenero(br.readLine());
			System.out.print("Protagonista: ");	
			pelicula.setProtagonista(br.readLine());
			System.out.print("Director: ");	
			pelicula.setDirector(br.readLine());						
		} catch (IOException e) {			
			logger.info("El registro no se pudo llevar a cabo");
		}
	}
	// Persistencia de estado
	public void agregar(){
		pelicula = Utilities.getContext().getBean("pelicula", Pelicula.class);
		leerDatos();
		operaciones.create(pelicula);
	}
	
	public void actualizar(){
		pelicula = Utilities.getContext().getBean("pelicula", Pelicula.class);
		try{
			logger.info("Ingrese el ID del pelicula");
			pelicula.setId(Long.parseLong(br.readLine()));
			pelicula = operaciones.read(pelicula);
			leerDatos();
			operaciones.update(pelicula);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}		
	}
	
	public void eliminar(){
		pelicula = Utilities.getContext().getBean("pelicula", Pelicula.class);
		try{
			logger.info("Ingrese el ID del pelicula");
			pelicula.setId(Long.parseLong(br.readLine()));
			pelicula = operaciones.read(pelicula);
			operaciones.delete(pelicula);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}	
	}
	
	public Pelicula buscar(){
		pelicula = Utilities.getContext().getBean("pelicula", Pelicula.class);
		try{
			logger.info("Ingrese el ID del pelicula");
			pelicula.setId(Long.parseLong(br.readLine()));
			pelicula = operaciones.read(pelicula);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		return pelicula;
	}
	
	@SuppressWarnings("unchecked")
	public List<Pelicula> buscarPorGenero(String genero){
		Session session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		return (List<Pelicula>) session.createCriteria(Pelicula.class)
		.add(Property.forName("genero").like(genero+"%"))
		.addOrder(Property.forName("titulo").asc())
		.setMaxResults(5).list();
		
	}
	@SuppressWarnings("unchecked")
	public List<Pelicula> buscarPorProtagonista(){
		String protagonista = "";
		try {
			System.out.print("Protagonista: ");	
			protagonista = br.readLine();							
		} catch (IOException e) {			
			logger.info("El registro no se pudo llevar a cabo");
		}
		Session session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		return (List<Pelicula>) session.createCriteria(Pelicula.class)
		.add(Property.forName("protagonista").like(protagonista+"%"))
		.addOrder(Property.forName("titulo").asc()).list();
	} 
	
	@SuppressWarnings("unchecked")
	public List<Pelicula> buscarPorDirector(){
		String director = "";
		try {
			System.out.print("Director: ");	
			director = br.readLine();							
		} catch (IOException e) {			
			logger.info("El registro no se pudo llevar a cabo");
		}
		Session session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		return (List<Pelicula>) session.createCriteria(Pelicula.class)
		.add(Property.forName("director").like(director+"%"))
		.setMaxResults(5).list();
	} 
	
	@SuppressWarnings("unchecked")
	public List<Pelicula> buscarPorGeneroProtagonista(){
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
		return (List<Pelicula>) session.createCriteria(Pelicula.class)
		.add(Property.forName("genero").like(genero+"%"))
		.add(Property.forName("protagonista").like(protagonista+"%"))
		.addOrder(Property.forName("titulo").asc())
		.setMaxResults(5).list();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Pelicula> buscarPorGeneroOrProtagonista(){
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
		return (List<Pelicula>) session.createCriteria(Pelicula.class)
			.add(logicalExpression)
			.addOrder(orderTitulo)
			.setMaxResults(5).list();
		
	}
	
	public List<Pelicula> list(Persistible persistible){
		return operaciones.list(persistible);
	}

}
