package io.github.joxebus.entity;

import io.github.joxebus.util.Persistible;

public final class DVDEntity implements Comparable<DVDEntity>, Persistible {
	private long id;
	private String titulo;
	private String genero;
	private String director;
	private String protagonista;
	
	public DVDEntity(){}
	
	public int compareTo(DVDEntity dVDEntity){
		return this.titulo.compareTo(dVDEntity.getTitulo());
	}
	
	
	public DVDEntity(String titulo, String genero, String director,
			String protagonista) {
		super();
		this.titulo = titulo;
		this.genero = genero;
		this.director = director;
		this.protagonista = protagonista;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getProtagonista() {
		return protagonista;
	}
	public void setProtagonista(String protagonista) {
		this.protagonista = protagonista;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return getId()+"\t"+getTitulo()+"\t"+getGenero()+"\t"+getProtagonista()+
				"\t"+getDirector();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DVDEntity other = (DVDEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
