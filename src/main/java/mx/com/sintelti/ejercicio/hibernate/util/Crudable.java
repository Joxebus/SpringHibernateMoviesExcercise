package mx.com.sintelti.ejercicio.hibernate.util;

import java.util.List;


public interface Crudable<T> {

	public abstract void create(T objeto);

	public abstract T read(Persistible objeto);

	public abstract void update(T objeto);

	public abstract void delete(T objeto);

	public abstract List<T> list(Persistible objeto);

}