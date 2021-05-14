package io.github.joxebus.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import io.github.joxebus.util.Persistible;
import io.github.joxebus.util.Utilities;


public class OperacionesCRUD<T>  {
	private Session session;
	
	
	public final void create(T objeto){
		session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save((T)objeto);
		session.getTransaction().commit();
	}
	
	public final T read(Persistible objeto){
		session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		T objetoNuevo = (T) session.createCriteria(objeto.getClass())
		.add(Restrictions.idEq(objeto.getId()))
		.uniqueResult();
		return objetoNuevo;
		
	}
	
	public final void update(T objeto){
		session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update((T)objeto);
		session.getTransaction().commit();
	}
	
	
	public final void delete(T objeto){
		session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete((T)objeto);
		session.getTransaction().commit();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<T> list(Persistible objeto){
		session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		return (List<T>)session.createCriteria(objeto.getClass()).list();		
	}
}
