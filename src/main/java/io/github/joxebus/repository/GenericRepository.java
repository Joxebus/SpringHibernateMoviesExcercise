package io.github.joxebus.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import io.github.joxebus.entity.TableEntity;
import io.github.joxebus.util.Utilities;

/**
 * This is a generic class that can perform CRUD operations for
 * any entity that implements TableEntity interface.
 *
 * See MovieRepository for an example of how to use it.
 * @param <T>
 */
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
	public List<T> list(Class<T> clazz) {
		session = Utilities.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		return (List<T>)session.createCriteria(clazz).list();
	}
}
