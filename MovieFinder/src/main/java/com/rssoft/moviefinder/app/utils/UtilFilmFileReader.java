package com.rssoft.moviefinder.app.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.ResourceUtils;

import com.rssoft.moviefinder.app.model.Film;

public class UtilFilmFileReader {
	
	public static List<Film> readFile(final String path, final String separator, 
			final String listSeparator) {
		List<Film> result = new ArrayList<Film>();
		
		try {
			// @formatter:off
			result = Files
				.lines(Paths.get(ResourceUtils.getFile(path).toURI()))
				.skip(1)
				.map(line -> {
					String[] values = line.split(separator);
					return new Film(Long.parseLong(values[0]), values[1], values[2],
										Arrays.asList(values[3].split(listSeparator)));
			}).collect(Collectors.toList());
			// @formatter:on
		} catch (IOException e) {
			System.err.println("Error de lectura en el fichero de datos: imdb_data");
			System.exit(-1);
		}
		
		return result;
	}

}
