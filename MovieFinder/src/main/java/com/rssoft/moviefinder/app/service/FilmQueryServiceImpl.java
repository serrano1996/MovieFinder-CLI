package com.rssoft.moviefinder.app.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rssoft.moviefinder.app.dao.FilmDAO;
import com.rssoft.moviefinder.app.model.Film;

@Service
public class FilmQueryServiceImpl implements FilmQueryService {
	
	@Autowired
	private FilmDAO filmDAO;
	
	private Predicate<Film> predicate;
	
	@PostConstruct
	public void init() {
		this.predicate = null;
	}

	@Override
	public Collection<Film> exec() {
		// @formatter:off
		return filmDAO.findAll()
				.stream()
				.filter(this.predicate)
				.collect(Collectors.toList());
		// @formatter:on
	}

	@Override
	public FilmQueryService anyGenre(String... genres) {
		Predicate<Film> p = (film -> Arrays.stream(genres)
				.anyMatch(film.getGenres()::contains));
		this.predicate = (predicate == null) ? p : this.predicate.and(p);
		return this;
	}

	@Override
	public FilmQueryService allGenres(String... genres) {
		Predicate<Film> p = (film -> Arrays.stream(genres)
				.allMatch(film.getGenres()::contains));
		this.predicate = (predicate == null) ? p : this.predicate.and(p);
		return this;
	}

	@Override
	public FilmQueryService year(String year) {
		Predicate<Film> p = (film -> film.getYear()
				.equalsIgnoreCase(year));
		this.predicate = (predicate == null) ? p : this.predicate.and(p);
		return this;
	}

	@Override
	public FilmQueryService betweenYears(String from, String to) {
		Predicate<Film> p = (film -> {
			LocalDate fromYear = LocalDate.of(Integer.parseInt(from), 1, 1);
			LocalDate toYear = LocalDate.of(Integer.parseInt(to), 1, 3);
			LocalDate filmYear = LocalDate.of(Integer.parseInt(film.getYear()), 1, 2);
			return filmYear.isAfter(fromYear) && filmYear.isBefore(toYear);
 		});
		this.predicate = (predicate == null) ? p : this.predicate.and(p);
		return this;
	}

	@Override
	public FilmQueryService titleContains(String title) {
		Predicate<Film> p = (film -> film.getTitle().toLowerCase()
				.contains(title.toLowerCase()));
		this.predicate = (predicate == null) ? p : this.predicate.and(p);
		return this;
	}

}
