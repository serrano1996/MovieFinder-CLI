package com.rssoft.moviefinder.app.service;

import java.util.Collection;

import com.rssoft.moviefinder.app.model.Film;

public interface FilmService {

	public Collection<String> findAllGenres();
	
	public Collection<Film> findByAnyGenre(String... genres);
	
	public Collection<Film> findByAllGenres(String... genres);

	public Collection<Film> findByYear(String year);

	public Collection<Film> findBetweenYears(String from, String to);

	public Collection<Film> findByTitleContains(String title);

	public Collection<Film> findAll();

}
