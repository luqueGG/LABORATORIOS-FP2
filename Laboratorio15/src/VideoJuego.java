import java.util.Scanner;

public class VideoJuego {
	
	public static void main(String[] args) {
		Scanner entrada = new Scanner (System.in);
		Ejercito[][] tableroEjercito = new Ejercito[10][10];
		String[] nombreReinos = {"Sacro Imperio","Inglaterra","Francia","Castilla Aragon","Moros"};
		String[] menu = {"Crear ejercitos aleatorios", "Crear un Ejercito", "Salir"};
		String nombre;
		int opcion, numSoldados, na, nd, nv, va;
		do {
			for (int i = 0; i < menu.length; i++)
				System.out.println((i+1)+". "+menu[i]);
			System.out.println("Escoje una opcion:");
			opcion = entrada.nextInt();
			if (opcion == 1) {
				ubicarEnTablero(tableroEjercito, escojerReino(entrada, nombreReinos));
				ubicarEnTablero(tableroEjercito, escojerReino(entrada, nombreReinos));
				mostrarTableroEjercito(tableroEjercito);
				limpiarTableroEjercito(tableroEjercito);
			} else if (opcion == 2) {			 
				do {
					System.out.println("1. Ingrese numero de soldados (max 10, min 1)");
					numSoldados = entrada.nextInt();
				} while (numSoldados > 10 || numSoldados < 1);
				Ejercito nuevoEjercito = new Ejercito(escojerReino(entrada, nombreReinos), numSoldados);	
				for (int i = 0; i < numSoldados; i++) {
					System.out.println("Ingrese el nombre: ");
					nombre = entrada.next();
					System.out.println("Ingrese el nivel de ataque: ");
					na = entrada.nextInt();
					System.out.println("Ingrese el nivel de defensa: ");
					nd = entrada.nextInt();
					System.out.println("Ingrese el nivel de vida: ");
					nv = entrada.nextInt();
					System.out.println("Ingrese la vida actual: ");
					va = entrada.nextInt();
					nuevoEjercito.agregarSoldado(Integer.toString(i), nombre, na, nd, nv, va);
				}		
				System.out.println("Mostrando ejercito: ");
				System.out.println(nuevoEjercito);			    
			    Soldado soldadoMayorAtaque = nuevoEjercito.getSoldadoMayorAtaque();
			    System.out.println("Soldado con mayor nivel de ataque: " + soldadoMayorAtaque);			    			    
			    System.out.println("Ranking de soldados por nivel de vida (descendente):");
			    nuevoEjercito.mostrarRankingPorNivelVida();			    
			    System.out.println("Datos del ejercito:");
			    nuevoEjercito.mostrarEjercito();
			} else if (opcion == 3) {
				break;
			} else
				System.out.println("Ingreso invalido...\nRegresando al menu Principal.\n");
		} while (true);
		System.out.println("Saliendo del programa.");
		entrada.close();
	}
	public static String escojerReino (Scanner entrada, String[] nombreReinos) {
		byte opcion;		
		String reino;
		do {
			System.out.println("Escoje un ejercito: ");
			for (int i = 0; i < nombreReinos.length; i++)
				System.out.println((i+1)+". "+nombreReinos[i]);
			System.out.print(">> ");
			opcion = entrada.nextByte();
			if (opcion == 1 || opcion == 2 || opcion == 3 || opcion == 4 || opcion == 5) {
				reino = new String(nombreReinos[opcion-1]);
				break;
			} else
				System.out.println("Ejercito no valido. Por favor vuelva a ingresar.");
			System.out.println("-".repeat(50));
		} while (true);
		if (reino.contains(" ")) {
			int espacio = reino.indexOf(" ")+1;
			return (reino.substring(0,1) + reino.substring(espacio, espacio+1)).toUpperCase();
		} else {
			return reino.substring(0,2).toUpperCase();
		}
	}

	public static void ubicarEnTablero(Ejercito[][] tablero, String reino) {
		int fila,columna;
		for (int i = 1; i <= 10; i++) {
			do {
				fila = (int) (Math.random()*10);
				columna = (int) (Math.random()*10);				
			} while (tablero[fila][columna] != null);
			tablero[fila][columna] = new Ejercito(reino);
		}
	}
	public static void mostrarTableroEjercito (Ejercito[][] tablero) {
		System.out.print("    \t");
		for (int i = 0; i < tablero.length; i++)
			System.out.print("      " + (char) (65 + i) + "    \t");
		System.out.println("\n");
		for (int i = 0; i < tablero.length; i++) {
			System.out.print("   " + (i + 1) + " \t");
			for (int j = 0; j < tablero.length; j++) {
				if (tablero[i][j] != null)
					System.out.print("[" + tablero[i][j].getNombre() + "]\t");
				else
					System.out.print("[             ]\t");
			}
			System.out.println("\n\n");
		}		
	}
	public static void limpiarTableroEjercito (Ejercito[][] tablero) {
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j] != null) {
					tablero[i][j].borrarEjercito();
					tablero[i][j] = null;
				}
			}
		}
	}
	
}
