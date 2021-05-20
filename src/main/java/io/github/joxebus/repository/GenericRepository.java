package io.github.joxebus.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import io.github.joxebus.entity.TableEntity;
import io.github.joxebus.util.Utilities;


public abstract class GenericRepository<T extends TableEntity> implements Repository<T>  {
	private Session session;
	
	public final void create(T tableEntity) {
		session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save((T)tableEntity);
		session.getTransaction().commit();
	}
	
	public final T read(TableEntity tableEntity) {
		session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		T objetoNuevo = (T) session.createCriteria(tableEntity.getClass())
		.add(Restrictions.idEq(tableEntity.getId()))
		.uniqueResult();
		return objetoNuevo;
		
	}
	
	public final void update(T tableEntity) {
		session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.update((T)tableEntity);
		session.getTransaction().commit();
	}
	
	
	public final void delete(T entity) {
		session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.delete((T)entity);
		session.getTransaction().commit();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<T> list(TableEntity tableEntity) {
		session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		return (List<T>)session.createCriteria(tableEntity.getClass()).list();
	}
}
