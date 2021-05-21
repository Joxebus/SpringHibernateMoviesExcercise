package io.github.joxebus.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This class load the configuration for Hibernate and Spring
 * it has exposed 2 static methods that return the SessionFactory and
 * the ApplicationContext
 */
public class Utilities {
	
	private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

	/**
	 * This method loads the configuration on the
	 * file src/main/resources/hibernate.cfg.xml
	 * this file contains the mappings to connect with
	 * database and entities
	 * @return
	 */
	private static SessionFactory buildSessionFactory(){
		try{
			return new Configuration().configure().buildSessionFactory(); 
		}catch(Throwable ex){
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static SessionFactory getSessionFactory(){
		return SESSION_FACTORY;
	}
	
	private static final ApplicationContext APPLICATION_CONTEXT = buildSpringContext();

	/**
	 * This method loads the configuration on the
	 * file src/main/resources/applicationContext.xml
	 * this file contains all the declared beans available in the application
	 * @return
	 */
	private static ClassPathXmlApplicationContext buildSpringContext(){		
			try{
				return new ClassPathXmlApplicationContext("/applicationContext.xml");
			}catch(BeansException ace){
				throw new ExceptionInInitializerError(ace);
			}			
	}
	
	public static final ApplicationContext getApplicationContext(){
		return APPLICATION_CONTEXT;
	}

}
