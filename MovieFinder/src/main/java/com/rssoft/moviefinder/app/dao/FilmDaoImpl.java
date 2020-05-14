package com.rssoft.moviefinder.app.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rssoft.moviefinder.app.config.AppConfig;
import com.rssoft.moviefinder.app.model.Film;
import com.rssoft.moviefinder.app.utils.UtilFilmFileReader;

@Repository
public class FilmDaoImpl implements FilmDAO {
	
	public List<Film> films = new ArrayList<Film>();
	
	@Autowired
	private AppConfig appConfig;
	
	@PostConstruct
	public void init() {
		this.films = UtilFilmFileReader.readFile(appConfig.getFile(), appConfig.getSeparator(), 
				appConfig.getListSeparator());
	}

	public Film findById(Long id) {
		// @formatter:off
		Optional<Film> result = films
				.stream()
				.filter(f -> f.getId() == id)
				.findFirst();
		// @formatter:on
		return result.orElse(null);
	}

	public Collection<Film> findAll() {
		return this.films;
	}

	public void insert(Film film) {
		this.films.add(film);
	}

	public void edit(Film film) {
		int index = this.getIndexOf(film.getId());
		if(index != -1) {
			this.films.set(index, film);
		}
	}

	public void delete(Long id) {
		int index = this.getIndexOf(id);
		if(index != -1) {
			this.films.remove(index);
		}
	}
	
	private int getIndexOf(Long id) {
		boolean found = false;
		int index = 0;
		
		while(!found && index < this.films.size()) {
			if(this.films.get(index).getId() == id) {
				found = true;
			} else {
				index++;
			}
		}
		
		return (found) ? index : -1;
	}

}
