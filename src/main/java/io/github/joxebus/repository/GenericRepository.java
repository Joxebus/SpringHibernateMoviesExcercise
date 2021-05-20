package io.github.joxebus.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import io.github.joxebus.entity.Entity;
import io.github.joxebus.util.Utilities;


public class GenericRepository<T>  {
	private Session session;
	
	
	public final void create(T entity) {
		session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save((T)entity);
		session.getTransaction().commit();
	}
	
	public final T read(Entity entity) {
		session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		T objetoNuevo = (T) session.createCriteria(entity.getClass())
		.add(Restrictions.idEq(entity.getId()))
		.uniqueResult();
		return objetoNuevo;
		
	}
	
	public final void update(T entity) {
		session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update((T)entity);
		session.getTransaction().commit();
	}
	
	
	public final void delete(T entity) {
		session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete((T)entity);
		session.getTransaction().commit();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<T> list(Entity entity) {
		session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		return (List<T>)session.createCriteria(entity.getClass()).list();		
	}
}
