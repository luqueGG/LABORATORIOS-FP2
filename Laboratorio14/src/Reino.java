public class Reino {
	private String nombreReino;
	private int numEjercitos;
	private Ejercito[] ejercitos = new Ejercito[10];
	
	public Reino () {
		this.nombreReino = "Desconocido";
		this.numEjercitos = (int)(Math.random()*10+1);
	}
	public Reino (String nombreReino) {
		this();
		this.nombreReino = nombreReino;
		for (int i = 0; i < this.numEjercitos; i++)
			ejercitos[i]= new Ejercito(nombreReino, i);
	}
	
	public void mostrarReino () {
		System.out.println("Reino de "+this.nombreReino);
		for (Ejercito ejercito : ejercitos) {
			ejercito.mostrarEjercito();
		}
	}
	public Ejercito[] getEjercitos() {
		return ejercitos;
	}
	public int getNumEjercitos() {
		return numEjercitos;
	}
	@Override
	public String toString() {
		return this.nombreReino;
	}
}
