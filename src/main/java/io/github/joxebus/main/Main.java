package io.github.joxebus.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import io.github.joxebus.entity.Movie;
import io.github.joxebus.service.MovieService;
import io.github.joxebus.util.Utilities;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		logger.info("Starting application");
		char option='S';
		Long movieId = null;
		String searchParam = null;
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		ApplicationContext applicationContext = Utilities.getApplicationContext();
		MovieService movieService = applicationContext.getBean(MovieService.class);
		do{
			try {
				menu();
				option = bufferedReader.readLine().toUpperCase().charAt(0);
				System.out.println("**************************************");
				
				switch(option){
					case '1':
						movieService.create();
						break;
					case '2':
						System.out.println("Ingrese el ID de la película");
						movieId = Long.parseLong(bufferedReader.readLine());
						movieService.update(movieId);
						break;
					case '3':
						System.out.println("Ingrese el ID de la película");
						movieId = Long.parseLong(bufferedReader.readLine());
						System.out.println(movieService.search(movieId));
						break;
					case '4':
						System.out.print("Ingrese Protagonista: ");
						searchParam = bufferedReader.readLine();
						movieService.searchByLeadCharacter(searchParam);
						break;
					case '5':
						System.out.print("Ingrese Director: ");
						searchParam = bufferedReader.readLine();
						movieService.searchByDirector(searchParam);
						break;
					case '6':
						System.out.print("Ingrese Genero: ");
						searchParam = bufferedReader.readLine();
						movieService.searchByGenre(searchParam);
						break;
					case '7':
						System.out.print("Ingrese Genero o Protagonista: ");
						searchParam = bufferedReader.readLine();
						movieService.searchByGenreOrLeadCharacter(searchParam);
						break;
					case '8':
						System.out.println("Ingrese el ID de la película");
						movieId = Long.parseLong(bufferedReader.readLine());
						movieService.delete(movieId);
						break;
					case '9':
						System.out.println("************ peliculas disponibles ************");
						System.out.println("Código \tTítulo \tGenero \tProtagonista \tDirector");
						for(Movie movie : movieService.list()){
							System.out.println(movie);
						}
						System.out.println("******************************************");
						break;
					case 'S':
						System.out.println("Saliendo del sistema");
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
				"6.- Buscar por genero\n" +
				"7.- Buscar por genero o protagonista\n" +
				"8.- Eliminar\n" +
				"9.- Listar\n" +
				"S.- Salir"
				);
	}

}
