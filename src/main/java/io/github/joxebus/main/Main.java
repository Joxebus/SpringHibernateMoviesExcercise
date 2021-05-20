package io.github.joxebus.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;

import io.github.joxebus.service.MovieService;
import io.github.joxebus.entity.Movie;
import io.github.joxebus.util.Utilities;

public class Main {

	public static void main(String[] args) {
		char opcion='S';
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ApplicationContext applicationContext = Utilities.getApplicationContext();
		MovieService admon = applicationContext.getBean(MovieService.class);
		do{
			try {
				menu();
				opcion = br.readLine().toUpperCase().charAt(0);
				System.out.println("**************************************");
				
				switch(opcion){
					case '1':
						admon.create();
						break;
					case '2':
						admon.update();
						break;
					case '3':
						System.out.println(admon.search());
						break;
					case '4':
						admon.searchByLeadCharacter();
						break;
					case '5':
						admon.searchByDirector();
						break;
					case '6':
						admon.searchByGenederOrLeadCharacter();
						break;
					case '7':
						admon.delete();
						break;
					case '8':
						System.out.println("************ peliculas disponibles ************");
						System.out.println("Código \tTítulo \tGenero \tProtagonista \tDirector");
						for(Movie movie : admon.list(new Movie())){
							System.out.println(movie);
						}
						System.out.println("******************************************");
						break;
					case '9':
						System.out.println("Saliendo del sistema");
						opcion='S';
						break;
					default:
						System.out.println("Opción inválida");
						break;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}while(opcion!='S');
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
