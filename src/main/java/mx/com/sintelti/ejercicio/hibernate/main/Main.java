package mx.com.sintelti.ejercicio.hibernate.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import mx.com.sintelti.ejercicio.hibernate.bussines.Administracion;
import mx.com.sintelti.ejercicio.hibernate.entity.DVDEntity;



public class Main {

	public static void main(String[] args) {
		char opcion='S';
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Administracion admon = new Administracion();
		do{
			
			try {
				Main.menu();
				opcion = br.readLine().toUpperCase().charAt(0);
				System.out.println("**************************************");
				
				switch(opcion){
					case '1':
						admon.agregarDVD();
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
						System.out.println("************ DVDs disponibles ************");
						System.out.println("Código \tTítulo \tGenero \tProtagonista \tDirector");
						for(DVDEntity dvd : admon.list(new DVDEntity())){
							System.out.println(dvd);
						}
						System.out.println("******************************************");
						break;
					case '9':
						admon.leerDatosArchivo();
						break;
						default:
							System.out.println("Opción invalida");
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
				"1.- Agregar dvd\n" +
				"2.- Modificar dvd\n" +
				"3.- Buscar dvd\n" +
				"4.- Buscar por protagonista dvd\n" +						
				"5.- Buscar por director dvd\n" +
				"6.- Buscar por genero o protagonista dvd\n" +
				"7.- Eliminar dvd\n" +
				"8.- Listar dvds\n" +
				"9.- Cargar datos de archivo" 
				);
	}

}
