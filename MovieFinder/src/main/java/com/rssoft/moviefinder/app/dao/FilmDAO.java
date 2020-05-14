package com.rssoft.moviefinder.app.dao;

import java.util.Collection;

import com.rssoft.moviefinder.app.model.Film;

public interface FilmDAO {
	
	public Film findById(Long id);
	
	public Collection<Film> findAll();
	
	public void insert(Film film);
	
	public void edit(Film film);
	
	public void delete(Long id);

}
