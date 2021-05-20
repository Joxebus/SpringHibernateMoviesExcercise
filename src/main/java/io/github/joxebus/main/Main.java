package io.github.joxebus.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.github.joxebus.service.MovieService;
import io.github.joxebus.entity.Movie;



public class Main {

	public static void main(String[] args) {
		char opcion='S';
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		MovieService admon = new MovieService();
		do{
			
			try {
				Main.menu();
				opcion = br.readLine().toUpperCase().charAt(0);
				System.out.println("**************************************");
				
				switch(opcion){
					case '1':
						admon.agregar();
						break;
					case '2':
						admon.actualizar();
						break;
					case '3':
						System.out.println(admon.buscar());
						break;
					case '4':
						admon.buscarPorProtagonista();
						break;
					case '5':
						admon.buscarPorDirector();
						break;
					case '6':
						admon.buscarPorGeneroOrProtagonista();
						break;
					case '7':
						admon.eliminar();
						break;
					case '8':
						System.out.println("************ peliculas disponibles ************");
						System.out.println("C�digo \tT�tulo \tGenero \tProtagonista \tDirector");
						for(Movie movie : admon.list(new Movie())){
							System.out.println(movie);
						}
						System.out.println("******************************************");
						break;
					case '9':
						admon.leerDatosArchivo();
						break;
						default:
							System.out.println("Opci�n invalida");
							opcion='S';
							break;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}while(opcion!='S');
	}
	
	public static void menu(){
		System.out.println("********** Elija una opcion **********\n" +
				"1.- Agregar pelicula\n" +
				"2.- Modificar pelicula\n" +
				"3.- Buscar pelicula\n" +
				"4.- Buscar por protagonista pelicula\n" +
				"5.- Buscar por director pelicula\n" +
				"6.- Buscar por genero o protagonista pelicula\n" +
				"7.- Eliminar pelicula\n" +
				"8.- Listar peliculas\n" +
				"9.- Cargar datos de archivo" 
				);
	}

}
