package io.github.joxebus.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * An entity is a class directly mapped to a table on the database
 * in this case hibernate does the magic here, with the annotations
 * to see the hibernate configuration.
 *
 * See the file src/resources/hibernate.cfg.xml
 */
@Entity
public final class Movie implements Comparable<Movie>, TableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String title;
	private String genre;
	private String director;
	private String leadCharacter;
	
	public Movie(){}

	public Movie(String title, String genre, String director,
				 String leadCharacter) {
		super();
		this.title = title;
		this.genre = genre;
		this.director = director;
		this.leadCharacter = leadCharacter;
	}

	public int compareTo(Movie movieEntity){
		return this.title.compareTo(movieEntity.getTitle());
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String titulo) {
		this.title = titulo;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genero) {
		this.genre = genero;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getLeadCharacter() {
		return leadCharacter;
	}
	public void setLeadCharacter(String protagonista) {
		this.leadCharacter = protagonista;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return getId()+"\t"+ getTitle()+"\t"+ getGenre()+"\t"+ getLeadCharacter()+
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
		Movie other = (Movie) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
