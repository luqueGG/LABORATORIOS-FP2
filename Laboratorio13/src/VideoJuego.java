import java.util.Scanner;
public class VideoJuego {
	final static int MAX_SOLDADOS = 10;
	/**
	 * Aqui va el menu principal, se gestionan los distintos modos de juego.
	 */
	public static void main(String[] args) {		
		Scanner entrada = new Scanner(System.in);
		Soldado[][] tablero = new Soldado[10][10];		
		char opcion;		
		do {
			System.out.println("MENU PRINCIPAL");
			System.out.println("-".repeat(50));
			System.out.println("1. Juego Rapido.");
			System.out.println("2. Juego Personalizado.");
			System.out.println("3. Salir.");
			opcion = entrada.next().charAt(0);
	        if (opcion == '1') {
	    		generarSoldados(entrada, tablero);
	        	jugar(entrada, tablero);
	        } else if (opcion == '2')
	        	juegoPersonalizado(entrada, tablero);
	        else if (opcion == '3')
	        	break;
	        else
	        	System.out.println("Opcion no valida. Intente de nuevo.\n");
		} while(true);
		System.out.println("Saliendo del juego ...");
		entrada.close();
	}	
	// Supremacy Logic
	public static void jugar(Scanner entrada, Soldado[][] tablero) {
		mostrarTablero(tablero);
		System.out.println("TOMANDO EL CONTROL DEL SOLDADO [fila-columna] ... )");
		int turno = 1; // 1 para Jugador 1, 2 para Jugador 2
		while (true) {
			System.out.println("Turno del Jugador " + turno + ":");
			tomarControlSoldado(entrada, tablero, turno);
			mostrarTablero(tablero);
			if (turno == 1) {
				System.out.println("Quedan "+ Soldado.getNumEjercito1() +" soldados en el ejercito "+ turno);
				turno = 2;				
			} else {
				System.out.println("Quedan "+ Soldado.getNumEjercito2() +" soldados en el ejercito "+ turno);
				turno = 1;
			}
			if (Soldado.getNumEjercito1() == 0 || Soldado.getNumEjercito2() == 0)
				break;
		}
		System.out.println(determinarGanador(tablero));
		Soldado.setNumEjercito1(0); // resetear los contadores de soldados
		Soldado.setNumEjercito2(0); // para evitar problemas
		resetearTablero(tablero);
	}
	// Metodo para determinar la posicion de un soldado recien creado en (Juego Personalizado):
	public static void determinarPosicion (Soldado[][] tablero, int numEquipo, String nombre, int nivelVida) {
		int fila = (numEquipo == 1) ? 2 : 7; 
		for (int columna = tablero[fila].length - 1; columna >= 0; columna--) {
			if (tablero[fila][columna] == null) {
				tablero[fila][columna] = new Soldado(nombre, nivelVida, numEquipo);
				break;
			}
		}
	}
	public static void determinarPosicion (Soldado[][] tablero, Soldado clonador, int numEquipo) {
		int fila = (numEquipo == 1) ? 2 : 7; 
		for (int columna = tablero[fila].length - 1; columna >= 0; columna--) {
			if (tablero[fila][columna] == null) {
				tablero[fila][columna] = clonador.clonarSoldado();
				break;
			}
		}
	}
	// Metodo para el juego personalizado (Incluye su propio menu y submenus)
	public static void juegoPersonalizado(Scanner entrada, Soldado[][] tablero) {
		String opcion, nombre;
		int nivelVida, numEquipo, limite1=0, limite2=0; // Los limites de soldados del ejercito 1 y 2 respectivamente.
		int[] punto = new int [2];
		int[] punto1 = new int [2];
		do {
			System.out.println("JUEGO PERSONALIZADO");
			System.out.println("*".repeat(50));
			System.out.println("1. Crear Soldado.");
			System.out.println("2. Eliminar Soldado.");
			System.out.println("3. Clonar Soldado.");
			System.out.println("4. Modificar Soldado.");
			System.out.println("5. Comparar Soldados.");
			System.out.println("6. Intercambiar Soldados.");
			System.out.println("7. Ver Soldado.");
			System.out.println("8. Ver Ejercito.");
			System.out.println("9. Sumar Niveles.");			
			System.out.println("10. Jugar.");
			System.out.println("11. Volver.");
			opcion = entrada.next();
			
			if (opcion.equals("1")) {				
					System.out.println("Que ejercito?");
					numEquipo = entrada.nextInt();
					if (numEquipo == 1 && limite1 < MAX_SOLDADOS) {
						limite1++;
						Soldado.setNumEjercito1(Soldado.getNumEjercito1()+1);
					}else if (numEquipo == 2 && limite2 < MAX_SOLDADOS) {
						limite2++;
						Soldado.setNumEjercito2(Soldado.getNumEjercito2()+1);
					}else {
						System.out.println("Error, el numero de soldados del ejercito "+numEquipo+" esta lleno\n");
						continue;
					}
					System.out.print("Ingrese un nombre para su soldado: ");
					nombre = entrada.next();
					System.out.print("Ingrese el nivel de vida para su soldado: ");
					nivelVida = entrada.nextInt();
					determinarPosicion(tablero, numEquipo, nombre, nivelVida);
					System.out.println("Creando Soldado...");				
			} else if (opcion.equals("2") ) {
				System.out.println("Que ejercito?");
				numEquipo = entrada.nextInt();
				if (vidaTotal(tablero, numEquipo) == 0) // Si retorna 0, significa que el ejercito esta vacio.
					System.out.println("No puede proceder, ya que el ejercito esta vacio.\n");
				else {
					punto = obtenerPosicion(entrada); // Obtener los puntos y alamacenarlos en un arreglo.
					numEquipo = tablero[punto[0]][punto[1]].getEquipo();
					if (numEquipo == 1) {
						limite1--;
						Soldado.setNumEjercito1(Soldado.getNumEjercito1()-1);
					} else if (numEquipo == 2) {
						limite2--;
						Soldado.setNumEjercito2(Soldado.getNumEjercito2()-1);

					} else {
						System.out.println("Error, el numero del ejercito no es valido\n");
						continue;
					}
					tablero[punto[0]][punto[1]] = null;
					System.out.println("Eliminando Soldado...");
				}
			} else if (opcion.equals("3") ) {
				if (vidaTotal(tablero, 1) == 0 && vidaTotal(tablero, 2) == 0) // Si retorna 0, significa que el ejercito esta vacio.
					System.out.println("No puede proceder, ya que ambos ejercitos estan vacios.\n");
				else {					
					punto = obtenerPosicion(entrada); // Obtener puntos
					numEquipo = tablero[punto[0]][punto[1]].getEquipo();
					if (numEquipo == 1 && limite1 < MAX_SOLDADOS) {
						limite1++;
						Soldado.setNumEjercito1(Soldado.getNumEjercito1()+1);
					} else if (numEquipo == 2 && limite2 < MAX_SOLDADOS) {
						limite2++;
						Soldado.setNumEjercito2(Soldado.getNumEjercito2()+1);
					} else {
						System.out.println("Error, el numero de soldados del ejercito "+numEquipo+" esta lleno\n");
						continue;
					}
					determinarPosicion(tablero, tablero[punto[0]][punto[1]], numEquipo);
					System.out.println("Clonando Soldado...");
				}
			} else if (opcion.equals("4") ) {
				punto = obtenerPosicion(entrada); // Obtener puntos
				do {
					System.out.println("1. Cambiar su nivel de Ataque.");
					System.out.println("2. Cambiar su nivel de Defensa.");
					System.out.println("3. Cambiar su nivel de Vida Actual.");
					System.out.println("4. Salir.");
					opcion = entrada.next();					
					if (opcion.equals("4"))
						break;
					System.out.print("Ingrese el valor: ");
					if (opcion.equals("1"))
						tablero[punto[0]][punto[1]].setNivelAtaque(entrada.nextInt());
					else if (opcion.equals("2"))
						tablero[punto[0]][punto[1]].setNivelDefensa(entrada.nextInt());
					else if (opcion.equals("3"))
						tablero[punto[0]][punto[1]].setVidaActual(entrada.nextInt());
					else 
						System.out.println("Ingreso incorrecto. Intente otra vez.");
				} while (true);				
			} else if (opcion.equals("5") ) {
				System.out.println("SOLDADO 1");
				punto = obtenerPosicion(entrada);
				System.out.println("SOLDADO 2");
				punto1 = obtenerPosicion(entrada);
				if (Soldado.comparacionEntre(tablero[punto[0]][punto[1]], tablero[punto1[0]][punto1[1]]))
					System.out.println("Son iguales.");
				else 
					System.out.println("Son diferentes.");
			} else if (opcion.equals("6") ) {
				Soldado auxSoldado;
				System.out.println("SOLDADO 1");
				punto = obtenerPosicion(entrada);
				System.out.println("SOLDADO 2");
				punto1 = obtenerPosicion(entrada);
				// FASE DE INTERCAMBIO
				auxSoldado = tablero[punto[0]][punto[1]];
				tablero[punto[0]][punto[1]] = tablero[punto1[0]][punto1[1]];
				tablero[punto1[0]][punto1[1]] = auxSoldado;
			} else if (opcion.equals("7") ) {
				if (vidaTotal(tablero, 1) == 0 && vidaTotal(tablero, 2) == 0) // Si retorna 0, significa que el ejercito esta vacio.
					System.out.println("No puede proceder, ya que ambos ejercitos estan vacios.\n");
				else {					
					System.out.print("Ingrese el nombre del soldado: ");
					nombre = entrada.next();
					for (int i = 0; i < tablero.length; i++) {
						for (int j = 0; j < tablero[i].length; j++) {
							if (tablero[i][j] != null && nombre.equals(tablero[i][j].getNombre())) {
								System.out.println("SOLDADO ENCONTRADO: ");
								System.out.println(tablero[i][j]);
								nombre = "ENCONTRADO";
								break;
							}
						}
						if (nombre.equals("ENCONTRADO"))
							break;
					}
				}
			} else if (opcion.equals("8") )
				mostrarTablero(tablero);
			else if (opcion.equals("9") ) {
				System.out.println("Que ejercito?");
				numEquipo = entrada.nextInt();
				if (numEquipo == 1 && limite1 < MAX_SOLDADOS)
					limite1++;
				else if (numEquipo == 2 && limite2 < MAX_SOLDADOS)
					limite2++;
				else {
					System.out.println("Error, el numero de soldados del ejercito "+numEquipo+" esta lleno\n");
					continue;
				}
				Soldado superSoldado = new Soldado();				
				for (Soldado[] centuria : tablero) {
			        for (Soldado soldado : centuria) {
			            if (soldado != null && soldado.getEquipo() == numEquipo) 
			                superSoldado = superSoldado.sumar(soldado, numEquipo);
			        }
			    }
				System.out.println("Creando al superSoldado ... ");
				determinarPosicion(tablero, superSoldado, numEquipo);
			} else if (opcion.equals("10") )
				jugar(entrada, tablero);
			else if (opcion.equals("11") ) {
				// Reseteando el tablero.
				resetearTablero(tablero);				
				break;
			} else
				System.out.println("Opcion no valida: Intente de nuevo\n");
			System.out.println("Volviendo al submenu ... \n");
		} while (true);
		System.out.println("Volviendo al menu.\n");
	}	
	// Logic
	public static void tomarControlSoldado(Scanner entrada, Soldado[][] tablero, int turno) {
		int num, j, direccion, nuevoNum, nuevoJ;
		Soldado rival;
		System.out.println("Ingrese el numero: ");
		num = entrada.nextInt() - 1;
		System.out.println("Ingrese la letra: ");
		String letra = entrada.next().toLowerCase();
		j = Character.getNumericValue(letra.charAt(0)) - 10;
		if (num >= 0 && num < tablero.length && j >= 0 && j < tablero[0].length && tablero[num][j] != null) {
			Soldado soldado = tablero[num][j];
			if (soldado.getEquipo() != turno) {
				System.out.println("No puedes controlar soldados del equipo contrario. Pierdes tu turno.");
				return;
			}
			System.out.println(soldado);
			nuevoNum = num;
			nuevoJ = j;
			while (true) {
				System.out.println("\nQue hacer?\n");
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
				break; // Si el valor de la direccion es 1,2,3 o 4; entonces rompera el bucle
			}
			if (movimientoValido(tablero, nuevoNum, nuevoJ, soldado.getEquipo())) {
				if (tablero[nuevoNum][nuevoJ] != null) {
					rival = tablero[nuevoNum][nuevoJ];
					realizarBatalla(soldado, rival);
					if (!rival.isVive()) {
						tablero[nuevoNum][nuevoJ] = soldado;
						tablero[num][j] = null;
					} else
						tablero[num][j] = null;
				} else {
					tablero[nuevoNum][nuevoJ] = soldado;
					tablero[num][j] = null;
				}				
			} else
				System.out.println("Movimiento invalido. Pierdes tu turno.");
		} else
			System.out.println("Coordenadas incorrectas o no hay un soldado en esa posicion. Pierdes tu turno.");		
	}
	public static boolean movimientoValido(Soldado[][] tablero, int num, int j, int equipoSoldado) {
		return num >= 0 && num < tablero.length && j >= 0 && j < tablero[0].length && (tablero[num][j] == null
				|| tablero[num][j].getEquipo() != equipoSoldado);
	}
	// Metodo para obtener la posicion de un soldado (Punto).
	public static int[] obtenerPosicion (Scanner entrada) {
		int[] punto = new int[2];
		System.out.println("Ingrese la fila: ");
		punto[0] = entrada.nextInt() - 1;
		System.out.println("Ingrese la columna: ");
		punto[1] =  Character.getNumericValue(entrada.next().toLowerCase().charAt(0)) - 10;
		return punto;
	}
	public static void realizarBatalla(Soldado soldado, Soldado rival) {
		double probabilidad1, probabilidadRival;
		int ganador, certeza;
	
		ganador = soldado.getVidaActual() + rival.getVidaActual();
		certeza = (int)(Math.random()*ganador + 1);
		probabilidad1 = (soldado.getVidaActual()*100)/ganador;
		probabilidadRival = 100 - probabilidad1;
		
		System.out.println("Batalla entre " + soldado.getNombre() + " ("+probabilidad1+"%) y " + rival.getNombre()
				+ " ("+probabilidadRival+"%)");
		if (soldado.getVidaActual() <= certeza) {
			rival.morir();
			soldado.setVidaActual(soldado.getVidaActual()+1); // Se le aumenta en 1 la vida actual del soldado
			System.out.println(rival.getNombre() + " ha sido derrotado."+"\nEl "+soldado.getNombre()+" ha ganado"
					+ " por probabilidades de "+probabilidad1+"% !");
		} else {
			soldado.morir();
			rival.setVidaActual(rival.getVidaActual()+1); // Se le aumenta en 1 la vida actual del rival
			System.out.println(soldado.getNombre() + " ha sido derrotado."+"\nEl "+rival.getNombre()+" ha ganado"
					+ " por probabilidades de "+probabilidadRival+"% !");
		}
	}
	// NO necesario...
	public static int vidaTotal(Soldado[][] tablero, int equipo) {
		int sumaVida = 0;
		for (Soldado[] fila : tablero) {
			for (Soldado soldado : fila) {
				if (soldado != null && soldado.getEquipo() == equipo)
					sumaVida += soldado.getVidaActual();
			}
		}
		return sumaVida;
	}
	// Modificable
	public static String determinarGanador(Soldado[][] tablero) {
		int vidaEjercito1 = Soldado.getNumEjercito1();
		int vidaEjercito2 = Soldado.getNumEjercito2();
		if (vidaEjercito1 > vidaEjercito2) {
			return "El Ejercito 1 gana";
		} else if (vidaEjercito2 > vidaEjercito1) {
			return "El Ejercito 2 gana";
		} else {
			return "Es un empate";
		}
	}	
	// Metodos para generar los soldados, el tablero
	public static void generarSoldados(Scanner entrada, Soldado[][] tablero) {
		int numSoldados1, numSoldados2, total;
		do {
			System.out.println("Cuantos soldados desea en el ejercito 1?");
			numSoldados1 = entrada.nextInt();
			System.out.println("Cuantos soldados desea en el ejercito 2?");
			numSoldados2 = entrada.nextInt();
			total = numSoldados1 + numSoldados2;
		} while (numSoldados1 < 0 || numSoldados1 > MAX_SOLDADOS || numSoldados2 < 0 || numSoldados2 > MAX_SOLDADOS 
				|| total < 0 || total > 2*MAX_SOLDADOS);
		generarBatallon(tablero, numSoldados1, 1);
		generarBatallon(tablero, numSoldados2, 2);
	}
	public static void generarBatallon(Soldado[][] tablero, int length, int equipo) {
		int fil, col, index = 1;
		while (index <= length) {
			do {
				fil = (int) (Math.random() * tablero.length);
				col = (int) (Math.random() * tablero.length);
			} while (tablero[fil][col] != null);
			tablero[fil][col] = new Soldado(equipo, index);			
			index += 1;
		}
	}
	public static void mostrarTablero(Soldado[][] tablero) {
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
					System.out.print("[          ]\t");
			}
			System.out.println("\n\n");
		}
	}	
	public static void resetearTablero (Soldado[][] tablero) {
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j] != null)
					tablero[i][j] = null;
			}
		}
	}
	 
}