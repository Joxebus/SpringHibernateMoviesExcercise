package mx.com.sintelti.ejercicio.hibernate.bussines;

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

import mx.com.sintelti.ejercicio.hibernate.dao.OperacionesCRUD;
import mx.com.sintelti.ejercicio.hibernate.entity.DVDEntity;
import mx.com.sintelti.ejercicio.hibernate.util.Persistible;
import mx.com.sintelti.ejercicio.hibernate.util.Utilities;

public class Administracion {
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private OperacionesCRUD <DVDEntity> operaciones = new OperacionesCRUD <DVDEntity>();
	private DVDEntity dvd;
	
	// Recolección de datos
	public void leerDatosArchivo(){
		dvd = Utilities.getContext().getBean("dvd",DVDEntity.class);
		System.out.println("Registrar una nueva película");
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("D:\\Peliculas.txt")));
			String linea;
			while((linea = br.readLine())!=null){
				System.out.println(linea.split("*")[0]);
				dvd.setTitulo(linea.split("*")[0]);
				dvd.setGenero(linea.split("*")[1]);
				dvd.setProtagonista(linea.split("*")[2]);
				dvd.setDirector(linea.split("*")[3]);		
				//operaciones.create(dvd);
			}
			for(DVDEntity dvdLista : list(dvd)){
				System.out.println(dvdLista);
			}
		} catch (IOException e) {			
			System.out.println("El registro no se pudo llevar a cabo");
			e.printStackTrace();
		}
	}
	
	private void leerDatos(){
		System.out.println("Registrar una nueva película");		
		try {
			System.out.print("Titulo: ");			
			dvd.setTitulo(br.readLine());
			System.out.print("Genero: ");	
			dvd.setGenero(br.readLine());
			System.out.print("Protagonista: ");	
			dvd.setProtagonista(br.readLine());
			System.out.print("Director: ");	
			dvd.setDirector(br.readLine());						
		} catch (IOException e) {			
			System.out.println("El registro no se pudo llevar a cabo");
		}
	}
	// Persistencia de estado
	public void agregarDVD(){
		dvd = Utilities.getContext().getBean("dvd",DVDEntity.class);
		leerDatos();
		operaciones.create(dvd);
	}
	
	public void actualizar(){
		dvd = Utilities.getContext().getBean("dvd",DVDEntity.class);
		try{
			System.out.println("Ingrese el ID del DVD");
			dvd.setId(Long.parseLong(br.readLine()));
			dvd = operaciones.read(dvd);
			leerDatos();
			operaciones.update(dvd);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}		
	}
	
	public void eliminar(){
		dvd = Utilities.getContext().getBean("dvd",DVDEntity.class);		
		try{
			System.out.println("Ingrese el ID del DVD");
			dvd.setId(Long.parseLong(br.readLine()));
			dvd = operaciones.read(dvd);
			operaciones.delete(dvd);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}	
	}
	
	public DVDEntity buscar(){
		dvd = Utilities.getContext().getBean("dvd",DVDEntity.class);		
		try{
			System.out.println("Ingrese el ID del DVD");
			dvd.setId(Long.parseLong(br.readLine()));
			dvd = operaciones.read(dvd);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		return dvd;
	}
	
	@SuppressWarnings("unchecked")
	public List<DVDEntity> buscarPorGenero(String genero){
		Session session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		return (List<DVDEntity>) session.createCriteria(DVDEntity.class)
		.add(Property.forName("genero").like(genero+"%"))
		.addOrder(Property.forName("titulo").asc())
		.setMaxResults(5).list();
		
	}
	@SuppressWarnings("unchecked")
	public List<DVDEntity> buscarPorProtagonista(){
		String protagonista = "";
		try {
			System.out.print("Protagonista: ");	
			protagonista = br.readLine();							
		} catch (IOException e) {			
			System.out.println("El registro no se pudo llevar a cabo");
		}
		Session session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		return (List<DVDEntity>) session.createCriteria(DVDEntity.class)
		.add(Property.forName("protagonista").like(protagonista+"%"))
		.addOrder(Property.forName("titulo").asc()).list();
	} 
	
	@SuppressWarnings("unchecked")
	public List<DVDEntity> buscarPorDirector(){
		String director = "";
		try {
			System.out.print("Director: ");	
			director = br.readLine();							
		} catch (IOException e) {			
			System.out.println("El registro no se pudo llevar a cabo");
		}
		Session session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		return (List<DVDEntity>) session.createCriteria(DVDEntity.class)
		.add(Property.forName("director").like(director+"%"))
		.setMaxResults(5).list();
	} 
	
	@SuppressWarnings("unchecked")
	public List<DVDEntity> buscarPorGeneroProtagonista(){
		String genero="";
		String protagonista ="";
		try {
			System.out.print("Genero: ");	
			genero = br.readLine();
			System.out.print("Protagonista: ");	
			protagonista = br.readLine();					
		} catch (IOException e) {			
			System.out.println("El registro no se pudo llevar a cabo");
		}
		Session session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		return (List<DVDEntity>) session.createCriteria(DVDEntity.class)
		.add(Property.forName("genero").like(genero+"%"))
		.add(Property.forName("protagonista").like(protagonista+"%"))
		.addOrder(Property.forName("titulo").asc())
		.setMaxResults(5).list();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<DVDEntity> buscarPorGeneroOrProtagonista(){
		String parametro="";
		try {
			System.out.print("Genero ó Protagonista: ");	
			parametro = br.readLine();					
		} catch (IOException e) {			
			System.out.println("El registro no se pudo llevar a cabo");
		}
		Session session = Utilities.getSessionFactory().getCurrentSession();
		Order orderTitulo = Property.forName("titulo").asc();
		Criterion critGen = Restrictions.like("genero",parametro+"%");
		Criterion critPro = Restrictions.like("protagonista",parametro+"%");
		LogicalExpression logicalExpression = Restrictions.or(critGen, critPro) ;
		session.beginTransaction();
		return (List<DVDEntity>) session.createCriteria(DVDEntity.class)
		.add(logicalExpression)
		.addOrder(orderTitulo)
		.setMaxResults(5).list();
		
	}
	
	public List<DVDEntity> list(Persistible persistible){
		return operaciones.list(persistible);
	}

}
