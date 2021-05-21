package io.github.joxebus.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import io.github.joxebus.service.MovieService;
import io.github.joxebus.entity.Movie;
import io.github.joxebus.util.Utilities;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		logger.info("Starting application");
		char option='S';
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ApplicationContext applicationContext = Utilities.getApplicationContext();
		MovieService movieService = applicationContext.getBean(MovieService.class);
		do{
			try {
				menu();
				option = br.readLine().toUpperCase().charAt(0);
				System.out.println("**************************************");
				
				switch(option){
					case '1':
						movieService.create();
						break;
					case '2':
						movieService.update();
						break;
					case '3':
						System.out.println(movieService.search());
						break;
					case '4':
						movieService.searchByLeadCharacter();
						break;
					case '5':
						movieService.searchByDirector();
						break;
					case '6':
						movieService.searchByGenederOrLeadCharacter();
						break;
					case '7':
						movieService.delete();
						break;
					case '8':
						System.out.println("************ peliculas disponibles ************");
						System.out.println("Código \tTítulo \tGenero \tProtagonista \tDirector");
						for(Movie movie : movieService.list(new Movie())){
							System.out.println(movie);
						}
						System.out.println("******************************************");
						break;
					case '9':
						System.out.println("Saliendo del sistema");
						option='S';
						break;
					default:
						System.out.println("Opción inválida");
						break;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}while(option!='S');
	}
	
	public static void menu(){
		System.out.println("********** Seleccione una opción del menú de películas **********\n" +
				"1.- Agregar\n" +
				"2.- Modificar\n" +
				"3.- Buscar\n" +
				"4.- Buscar por protagonista\n" +
				"5.- Buscar por director\n" +
				"6.- Buscar por genero o protagonista\n" +
				"7.- Eliminar\n" +
				"8.- Listar\n" +
				"9.- Salir"
				);
	}

}
