package com.rssoft.moviefinder.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.rssoft.moviefinder.app.config.AppConfig;

public class MovieFinderApp {

	public static void main(String[] args) {
		ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
		
		MovieFinderRunApp runApp = appContext.getBean(MovieFinderRunApp.class);
		runApp.run(args);
		
		((AnnotationConfigApplicationContext) appContext).close();
	}

}
