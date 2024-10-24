import java.util.ArrayList;
import java.util.Scanner;

public class VideoJuego {
	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		Soldado[][] tablero = new Soldado[10][10];
		generarSoldados(entrada, tablero);
		mostrarTablero(tablero);
		System.out.println("TOMANDO EL CONTROL DEL SOLDADO [fila-columna] ... )");
		int turno = 1; // 1 para Jugador 1, 2 para Jugador 2
		while (vidaTotal(tablero, 1) > 0 && vidaTotal(tablero, 2) > 0) {
			System.out.println("Turno del Jugador " + turno + ":");
			tomarControlSoldado(entrada, tablero, turno);
			mostrarTablero(tablero);
			turno = (turno == 1) ? 2 : 1;
		}
		System.out.println(determinarGanador(tablero));
		entrada.close();
	}
	// Logic
	public static void tomarControlSoldado(Scanner entrada, Soldado[][] tablero, int turno) {
		int num, j, direccion, nuevoNum, equipoSoldado, nuevoJ;
		Soldado rival;
		System.out.println("Ingrese el numero: ");
		num = entrada.nextInt() - 1;
		System.out.println("Ingrese la letra: ");
		String letra = entrada.next().toLowerCase();
		j = Character.getNumericValue(letra.charAt(0)) - 10;
		if (num >= 0 && num < tablero.length && j >= 0 && j < tablero[0].length && tablero[num][j] != null) {
			Soldado soldado = tablero[num][j];
			equipoSoldado = Character.getNumericValue(soldado.getNombre().charAt(7));
			if (equipoSoldado != turno) {
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
				break; // Si el valor de la direccion es 1,2,3 o 4. Entonces rompera el bucle
			}
			if (movimientoValido(tablero, nuevoNum, nuevoJ, equipoSoldado)) {
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
				|| Character.getNumericValue(tablero[num][j].getNombre().charAt(7)) != equipoSoldado);
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
	
	// Metodos para generar los soldados, el tablero
	public static void generarSoldados(Scanner entrada, Soldado[][] tablero) {
		int numSoldados1, numSoldados2, total;
		do {
			System.out.println("Cuantos soldados desea en el ejercito 1?");
			numSoldados1 = entrada.nextInt();
			System.out.println("Cuantos soldados desea en el ejercito 2?");
			numSoldados2 = entrada.nextInt();
			total = numSoldados1 + numSoldados2;
		} while (numSoldados1 < 0 || numSoldados1 > 100 || numSoldados2 < 0 || numSoldados2 > 100 || total < 0
				|| total > 100);
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

	public static Soldado soldadoConMayorNivelVida(Soldado[][] tablero, int equipo) {
		Soldado mayor = null;
		for (Soldado[] fila : tablero) {
			for (Soldado soldado : fila) {
				if (soldado != null && Character.getNumericValue(soldado.getNombre().charAt(7)) == equipo) {
					if (mayor == null || soldado.getNivelVida() > mayor.getNivelVida()) {
						mayor = soldado;
					}
				}
			}
		}
		return mayor;
	}

	public static double promedioNivelVida(Soldado[][] tablero, int equipo) {
		double resultado;
		int suma = 0, contador = 0;
		for (Soldado[] fila : tablero) {
			for (Soldado soldado : fila) {
				if (soldado != null && Character.getNumericValue(soldado.getNombre().charAt(7)) == equipo) {
					suma += soldado.getNivelVida();
					contador++;
				}
			}
		}
		resultado = contador > 0 ? (double) suma / contador : 0;
		return resultado;
	}

	public static void imprimirSoldadosPorOrdenCreacion(Soldado[][] tablero, int equipo) {
		for (Soldado[] fila : tablero) {
			for (Soldado soldado : fila) {
				if (soldado != null && Character.getNumericValue(soldado.getNombre().charAt(7)) == equipo)
					System.out.println(soldado);
			}
		}
	}

	public static void rankingPorNivelVidaSelectionSort(Soldado[][] tablero, int equipo) {
		ArrayList<Soldado> listaSoldados = new ArrayList<>();
		Soldado temp;
		int mayor;
		for (Soldado[] fila : tablero) {
			for (Soldado soldado : fila) {
				if (soldado != null && Character.getNumericValue(soldado.getNombre().charAt(7)) == equipo)
					listaSoldados.add(soldado);
			}
		}
		for (int i = 0; i < listaSoldados.size() - 1; i++) {
			mayor = i;
			for (int j = i + 1; j < listaSoldados.size(); j++) {
				if (listaSoldados.get(j).getNivelVida() > listaSoldados.get(mayor).getNivelVida())
					mayor = j;
			}
			temp = listaSoldados.get(mayor);
			listaSoldados.set(mayor, listaSoldados.get(i));
			listaSoldados.set(i, temp);
		}
		for (Soldado soldado : listaSoldados)
			System.out.println(soldado);
	}

	public static void rankingPorNivelVidaInsertionSort(Soldado[][] tablero, int equipo) {
		ArrayList<Soldado> listaSoldados = new ArrayList<>();
		Soldado nuevoSoldado;
		int j;
		for (Soldado[] fila : tablero) {
			for (Soldado soldado : fila) {
				if (soldado != null && Character.getNumericValue(soldado.getNombre().charAt(7)) == equipo) {
					listaSoldados.add(soldado);
				}
			}
		}
		for (int i = 1; i < listaSoldados.size(); i++) {
			nuevoSoldado = listaSoldados.get(i);
			j = i - 1;
			while (j >= 0 && listaSoldados.get(j).getNivelVida() < nuevoSoldado.getNivelVida()) {
				listaSoldados.set(j + 1, listaSoldados.get(j));
				j--;
			}
			listaSoldados.set(j + 1, nuevoSoldado);
		}
		for (Soldado soldado : listaSoldados)
			System.out.println(soldado);
	}

	public static int vidaTotal(Soldado[][] tablero, int equipo) {
		int sumaVida = 0;
		for (Soldado[] fila : tablero) {
			for (Soldado soldado : fila) {
				if (soldado != null && Character.getNumericValue(soldado.getNombre().charAt(7)) == equipo)
					sumaVida += soldado.getVidaActual();
			}
		}
		return sumaVida;
	}

	public static String determinarGanador(Soldado[][] tablero) {
		int vidaEjercito1 = vidaTotal(tablero, 1);
		int vidaEjercito2 = vidaTotal(tablero, 2);
		if (vidaEjercito1 > vidaEjercito2) {
			return "El Ejercito 1 gana";
		} else if (vidaEjercito2 > vidaEjercito1) {
			return "El Ejercito 2 gana";
		} else {
			return "Es un empate";
		}
	}
}
