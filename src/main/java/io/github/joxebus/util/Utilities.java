package io.github.joxebus.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Utilities {
	
	private static final SessionFactory SESSION_FACTORY = buildSessionFactory();
	
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
	
	private static final ApplicationContext CONTEXT = buildSpringContext(); 
	
	private static ClassPathXmlApplicationContext buildSpringContext(){		
			try{
				return new ClassPathXmlApplicationContext("/ApplicationContext.xml");
			}catch(BeansException ace){
				throw new ExceptionInInitializerError(ace);
			}			
	}
	
	public static final ApplicationContext getContext(){
		return CONTEXT;
	}

}
