import java.util.Scanner;

public class VideoJuego {
	public static void main(String[] args) {
		Scanner entrada = new Scanner (System.in);
		String[] nombreReinos = {"Sacro Imperio","Inglaterra","Francia","Castilla Aragon","Moros"};
		Ejercito[][] tableroEjercito = new Ejercito[10][10];
		Soldado[][] tableroSoldado = new Soldado[10][10];
		Reino reino1, reino2; // Declarando 2 reinos, contienen ejercitos y a su vez, soldados.
		reino1 = escojerReino(entrada, nombreReinos); //
		reino2 = escojerReino(entrada, nombreReinos); //
		ubicarTableroEjercito(reino1, reino2, tableroEjercito);
		mostrarTableroEjercito(tableroEjercito);
		tomarControlEjercito(entrada, reino1, tableroEjercito, tableroSoldado);
		mostrarTableroEjercito(tableroEjercito);
		do {
			mostrarTableroEjercito(tableroEjercito);
			tomarControlEjercito(entrada, reino1, tableroEjercito, tableroSoldado);
		} while (tieneEjercitos(reino1) && tieneEjercitos(reino2));		
		System.out.println("Saliendo de la aplicacion.");
		entrada.close();
	}
	// Metodo para escojer que reino pertenecera los ejercitos
	public static Reino escojerReino (Scanner entrada, String[] nombreReinos) {
		byte opcion;		
		Reino reino;
		do {
			System.out.println("Escoje un ejercito: ");
			for (int i = 0; i < nombreReinos.length; i++)
				System.out.println((i+1)+". "+nombreReinos[i]);
			System.out.print(">> ");
			opcion = entrada.nextByte();
			if (opcion == 1 || opcion == 2 || opcion == 3 || opcion == 4 || opcion == 5) {
				reino = new Reino(nombreReinos[opcion-1]);
				break;
			} else
				System.out.println("Ejercito no valido. Por favor vuelva a ingresar.");
			System.out.println("-".repeat(50));
		} while (true);
		return reino;
	}
	// Metodo para ubicar cada ejercito en el tablero de Ejercitos
	public static void ubicarTableroEjercito (Reino reino1, Reino reino2, Ejercito[][] tablero) {
		validarUbicacionEjercito(reino1, tablero);
		validarUbicacionEjercito(reino2, tablero);
	}
	// Metodo para ubicar cada soldado en el tablero de Ejercitos
	public static void ubicarTableroSoldado (Ejercito ejercito1, Ejercito ejercito2, Soldado[][] tablero) {
		validarUbicacionSoldado(ejercito1, tablero);
		validarUbicacionSoldado(ejercito2, tablero);
	}
	//Validacion de la posicion de cada Ejercito en el tablero de Ejercitos
	public static void validarUbicacionEjercito (Reino reino, Ejercito[][] tablero) {
		int fila,columna;
		for (int i = 0; i < reino.getEjercitos().length; i++) {
			do {
				fila = (int) (Math.random()*10);
				columna = (int) (Math.random()*10);				
			} while (tablero[fila][columna] != null);
			tablero[fila][columna] = reino.getEjercitos()[i];
		}
	}
	// Validacion de la posicion de cada Soldado en el tablero de Soldados
	public static void validarUbicacionSoldado (Ejercito ejercito, Soldado[][] tablero) {
		int fila,columna;
		for (int i = 0; i < ejercito.getSoldados().length; i++) {
			do {
				fila = (int) (Math.random() * tablero.length);
				columna = (int) (Math.random() * tablero.length);
			} while (tablero[fila][columna] != null);
			tablero[fila][columna] = ejercito.getSoldados()[i];			
		}
	}
	// Metodo para mostrar el tablero de Ejercitos
	public static void mostrarTableroEjercito (Ejercito[][] tablero) {
		System.out.print("    \t");
		for (int i = 0; i < tablero.length; i++)
			System.out.print("      " + (char) (65 + i) + "    \t");
		System.out.println("\n");
		for (int i = 0; i < tablero.length; i++) {
			System.out.print("   " + (i + 1) + " \t");
			for (int j = 0; j < tablero.length; j++) {
				if (tablero[i][j] != null)
					System.out.print("[" + tablero[i][j] + "]\t");
				else
					System.out.print("[             ]\t");
			}
			System.out.println("\n\n");
		}		
	}
	// Metodo para mostrar el tablero de Soldados
	public static void mostrarTableroSoldado (Soldado[][] tablero) {
		System.out.print("    \t");
		for (int i = 0; i < tablero.length; i++)
			System.out.print("      " + (char) (65 + i) + "    \t");
		System.out.println("\n");
		for (int i = 0; i < tablero.length; i++) {
			System.out.print("   " + (i + 1) + " \t");
			for (int j = 0; j < tablero.length; j++) {
				if (tablero[i][j] != null)
					System.out.print("[" + tablero[i][j] + "]\t");
				else
					System.out.print("[            ]\t");
			}
			System.out.println("\n\n");
		}		
	}
	// Metodo para tomar el control de un ejercito
	public static void tomarControlEjercito (Scanner entrada, Reino reino, Ejercito[][] tablero, Soldado[][] tableroSoldado) {
		int fila, columna, direccion, nuevoNum, nuevoJ;		
		Ejercito auxEjercito;
		do {
			System.out.println("Ingrese el numero: ");
			fila = entrada.nextInt() - 1;
			System.out.println("Ingrese la letra: ");
			columna = Character.getNumericValue(entrada.next().toLowerCase().charAt(0)) - 10;
			if (tablero[fila][columna] == null) {
				System.out.println("No hay ningun ejercito ahi!");
				continue;
			}
			while (true) {
				nuevoNum = fila;
				nuevoJ = columna;
				System.out.println("\nQue hacer con el ejercito?\n");
				System.out.println("1. Dirigirse arriba");
				System.out.println("2. Dirigirse abajo");
				System.out.println("3. Dirigirse izquierda");
				System.out.println("4. Dirigirse derecha");
				direccion = entrada.nextInt();
				if (direccion == 1)
					nuevoNum--;
				else if (direccion == 2)
					nuevoNum++;
				else if (direccion == 3)
					nuevoJ--;
				else if (direccion == 4)
					nuevoJ++;
				else {
					System.out.println("Direccion incorrecta, Intentalo otra vez.");
					continue;
				}
				if (nuevoNum < 0 || nuevoNum > 9 || nuevoJ < 0 || nuevoJ > 9) {
					System.out.println("Llegaste al limite del tablero");
					continue;
				} else if (tablero[nuevoNum][nuevoJ] != null && tablero[nuevoNum][nuevoJ].getReinoPerteneciente().equals(tablero[fila][columna].getReinoPerteneciente())) {
					System.out.println("En esa direccion esta el ejercito de tu propio equipo o llegaste al limite del tablero.");
					continue;
				}
				break; // Si el soldado se puede mover, entonces rompera el bucle
			} // Fin del bucle while			
			if (tablero[nuevoNum][nuevoJ] != null) { // Verifica solamente si es del ejercito rival
				ubicarTableroSoldado(tablero[fila][columna], tablero[nuevoNum][nuevoJ], tableroSoldado);
				realizarBatalla(fila, columna, nuevoNum, nuevoJ, tableroSoldado, tablero, entrada);
			} else {
				auxEjercito = tablero[fila][columna];
				tablero[fila][columna] = null;
				tablero[nuevoNum][nuevoJ] = auxEjercito;
			}				
			break;			
		} while (true);
		
	}
	// Metodo para tomar el control de un soldado
	public static void tomarControlSoldado (Scanner entrada, Soldado[][] tablero, Ejercito e1, Ejercito e2) {
		int fila, columna, direccion, nuevoNum, nuevoJ;		
		Soldado auxSoldado;
		do {
			System.out.println("Ingrese el numero: ");
			fila = entrada.nextInt() - 1;
			System.out.println("Ingrese la letra: ");
			columna = Character.getNumericValue(entrada.next().toLowerCase().charAt(0)) - 10;
			if (tablero[fila][columna] == null) {
				System.out.println("No hay ningun Soldado ahi!");
				continue;
			}
			while (true) {
				nuevoNum = fila;
				nuevoJ = columna;
				System.out.println("\nQue hacer con el soldado?\n");
				System.out.println("1. Dirigirse arriba");
				System.out.println("2. Dirigirse abajo");
				System.out.println("3. Dirigirse izquierda");
				System.out.println("4. Dirigirse derecha");
				direccion = entrada.nextInt();
				if (direccion == 1)
					nuevoNum--;
				else if (direccion == 2)
					nuevoNum++;
				else if (direccion == 3)
					nuevoJ--;
				else if (direccion == 4)
					nuevoJ++;
				else {
					System.out.println("Direccion incorrecta, Intentalo otra vez.");
					continue;
				}
				if (nuevoNum < 0 || nuevoNum > 9 || nuevoJ < 0 || nuevoJ > 9) {
					System.out.println("Llegaste al limite del tablero");
					continue;
				} else if (tablero[nuevoNum][nuevoJ] != null && tablero[nuevoNum][nuevoJ].getReino().equals(tablero[fila][columna].getReino())) {
					System.out.println("En esa direccion esta el soldado de tu propio equipo o llegaste al limite del tablero.");
					continue;
				}
				break; // Si el soldado se puede mover, entonces rompera el bucle
			} // Fin del bucle while			
			if (tablero[nuevoNum][nuevoJ] != null) { // Verifica solamente si es del ejercito rival
				realizarBatalla(fila, columna, nuevoNum, nuevoJ, tablero, e1, e2);
//				mostrarTableroSoldado(tablero);
			} else {
				auxSoldado= tablero[fila][columna];
				tablero[fila][columna] = null;
				tablero[nuevoNum][nuevoJ] = auxSoldado;
			}				
			break;			
		} while (true);
		
	}
	// Metodo para ir a la batalla entre dos ejercitos.
//	public static void realizarBatalla(int fila, int col, int nuFila, int nuCol, Soldado[][]tablero, Ejercito[][] tableroEjercito, Scanner entrada) {
//		while (TieneSoldados(tableroEjercito[fila][col]) && TieneSoldados(tableroEjercito[nuFila][nuCol])) {			
//			mostrarTableroSoldado(tablero);
//			tomarControlSoldado(entrada, tablero, tableroEjercito[fila][col], tableroEjercito[nuFila][nuCol]);
//		}
//		if (!TieneSoldados(tableroEjercito[fila][col])) {
//			tableroEjercito[tableroEjercito[fila][col].getNumOrden()] = null;
//		} else {
//			tableroEjercito[tableroEjercito[nuFila][nuCol].getNumOrden()] = null;
//			tableroEjercito[nuFila][nuCol] = tableroEjercito[fila][col];
//		}
//	}
	// Metodo para ir a la batalla entre dos ejercitos.
	public static void realizarBatalla(int fila, int col, int nuFila, int nuCol, Soldado[][] tablero, Ejercito[][] tableroEjercito, Scanner entrada) {
	    System.out.println("Iniciando batalla entre los ejercitos en (" + fila + ", " + col + ") y (" + nuFila + ", " + nuCol + ")");

	    while (tieneSoldados(tableroEjercito[fila][col]) && tieneSoldados(tableroEjercito[nuFila][nuCol])) {            
	        mostrarTableroSoldado(tablero);
	        tomarControlSoldado(entrada, tablero, tableroEjercito[fila][col], tableroEjercito[nuFila][nuCol]);

	        System.out.println("Estado de Ejercito (" + fila + ", " + col + "): " + 
	        tieneSoldados(tableroEjercito[fila][col]));
	        System.out.println("Estado de Ejercito (" + nuFila + ", " + nuCol + "): " + 
	        tieneSoldados(tableroEjercito[nuFila][nuCol]));
	    }

	    if (!tieneSoldados(tableroEjercito[fila][col])) {
	        System.out.println("Ejercito en posicion (" + fila + ", " + col + ") ha sido derrotado.");
	        tableroEjercito[fila][col] = null;  // 
	    } else {
	        System.out.println("Ejercito en posicion (" + nuFila + ", " + nuCol + ") ha sido derrotado.");
	        tableroEjercito[nuFila][nuCol] = null;
	        tableroEjercito[nuFila][nuCol] = tableroEjercito[fila][col]; 
	        tableroEjercito[fila][col] = null; 
	    }
	}

	/**
	 * Metodo (Experimental) para saber si un ejercito posee o no soldados
	 * Metrica-> Total de vida Actual; Si es '0', entonces no posee ejercitos
	 */
//	public static boolean TieneSoldados (Ejercito ejercito) {
//		int sumaTotalVida = 0;
//		for (int i = 0; i < ejercito.getSoldados().length ; i++) {
//			if (ejercito.getSoldados()[i] != null)
//				sumaTotalVida += ejercito.getSoldados()[i].getVidaActual();
//		}
//		if (sumaTotalVida != 0)
//			return true;
//		return false;
//	}
	// Metodo (Experimental) para saber si un ejercito posee o no soldados
	public static boolean tieneSoldados (Ejercito ejercito) {
	    int sumaTotalVida = 0;
	    for (int i = 0; i < ejercito.getSoldados().length; i++) {
	        if (ejercito.getSoldados()[i] != null && ejercito.getSoldados()[i].getVidaActual() > 0)
	            sumaTotalVida += ejercito.getSoldados()[i].getVidaActual();
	    }
	    return sumaTotalVida > 0;
	}
	// Metodo para saber si un reino posee o no ejercitos
	public static boolean tieneEjercitos (Reino reino) {
		for (int i = 0; i < reino.getEjercitos().length; i++) {
			if (reino.getEjercitos()[i] != null)
				return true;
		}
		return false;
	}
	
	// Metodo para ir a la batalla entre dos soldados.
	public static void realizarBatalla(int i, int j, int nuevoi, int nuevoj, Soldado[][] tablero, Ejercito e1, Ejercito e2) {
	    double probabilidad1, probabilidadRival;
	    int ganador, certeza;

	    ganador = tablero[i][j].getVidaActual() + tablero[nuevoi][nuevoj].getVidaActual();
	    certeza = (int)(Math.random()*ganador+1);
	    probabilidad1 = (tablero[i][j].getVidaActual()*100.0/ganador);
	    probabilidadRival = 100.0 - probabilidad1;

	    System.out.println("Batalla entre " + tablero[i][j].getNombre() + " (" + probabilidad1 + "%) y " + 
	    tablero[nuevoi][nuevoj].getNombre()
	            + " (" + probabilidadRival + "%)");

	    if (tablero[i][j].getVidaActual() < certeza) {
	        System.out.println(tablero[nuevoi][nuevoj].getNombre() + " ha sido derrotado.");
	        e2.getSoldados()[tablero[nuevoi][nuevoj].getNumOrden()] = null;
	        tablero[nuevoi][nuevoj].morir();
	        tablero[nuevoi][nuevoj] = null;
	        tablero[nuevoi][nuevoj] = tablero[i][j];
	        tablero[i][j] = null;
	    } else {
	        System.out.println(tablero[i][j].getNombre() + " ha sido derrotado.");
	        e1.getSoldados()[tablero[nuevoi][nuevoj].getNumOrden()] = null;
	        tablero[i][j].morir();
	        tablero[i][j] = null;  // Limpia la posición del tablero
	        tablero[i][j] = tablero[nuevoi][nuevoj];
	        tablero[nuevoi][nuevoj] = null;
	    }
	}
}
