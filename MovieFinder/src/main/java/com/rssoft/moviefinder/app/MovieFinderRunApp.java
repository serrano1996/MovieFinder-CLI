package com.rssoft.moviefinder.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rssoft.moviefinder.app.model.Film;
import com.rssoft.moviefinder.app.service.FilmQueryService;
import com.rssoft.moviefinder.app.service.FilmServiceImpl;

@Component
public class MovieFinderRunApp {
	
	@Autowired
	private FilmServiceImpl filmService;
	
	@Autowired
	private FilmQueryService filmQueryService;
	
	@Autowired
	private MovieFinderHelp movieHelp; 
	
	public void run(String[] args) {
		if(args.length < 1) {
			System.out.println("Error de sintaxis");
			System.out.println(movieHelp.getHelp());
		} else if(args.length == 1) {
			switch(args[0].toLowerCase()) {
				case "-lg":
					filmService.findAllGenres().forEach(System.out::println);
					break;
				case "-h":
					System.out.println(movieHelp.getHelp());
					break;
				default:
					System.out.println("Error de sintaxis");
					System.out.println(movieHelp.getHelp());
			}
		} else if((args.length % 2) != 0) {
			System.out.println("Error de sintaxis");
			System.out.println(movieHelp.getHelp());
		} else if(args.length > 8) {
			System.out.println("Error de sintaxis");
			System.out.println(movieHelp.getHelp());
		} else {
			List<String[]> arguments = new ArrayList<String[]>();
			
			for (int i = 0; i < args.length; i += 2) {
				arguments.add(new String[] { args[i], args[i + 1] });
			}
			
			boolean error = false;

			for(String[] argument : arguments) {
				switch (argument[0].toLowerCase()) {
					case "-ag":
						filmQueryService.anyGenre(argument[1].split(","));
						break;
					case "-tg":
						filmQueryService.allGenres(argument[1].split(","));
						break;
					case "-y":
						filmQueryService.year(argument[1]);
						break;
					case "-b":
						String[] years = argument[1].split(",");
						filmQueryService.betweenYears(years[0], years[1]);
						break;
					case "-t":
						filmQueryService.titleContains(argument[1]);
						break;
					default: error = true;
							 System.out.println("Error de sintaxis");
							 System.out.println(movieHelp.getHelp());
					}
			}
			
			if(!error) {
				Collection<Film> result = filmQueryService.exec();
				System.out.printf("%s\t%-50s\t%s\t%s\n","ID","Título", "Año", "Géneros");
				if(result != null) {
					result.forEach(f -> System.out.printf("%s\t%-50s\t%s\t%s\n", 
							f.getId(), f.getTitle(), f.getYear(), 
							f.getGenres().stream().collect(Collectors.joining(", "))));
				} else {
					System.out.println("No hay películas que cumplan esos criterios. Lo sentimos");
				}
			}
		}	
	}

}
