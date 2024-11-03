
public class Ejercito {
	private String nombre;
	private String reinoPerteneciente; // El nombre del reino a la que el ejercito pertenece
	private int numOrden;
	private int numSoldados; // El numero d soldados que puede tener 1-10
	private Soldado[] soldados = new Soldado[10]; // en cada celda se aloja un soldado
	
	public Ejercito (String reinoPerteneciente, int numOrden) {		
		if (reinoPerteneciente.contains(" "))			
			this.reinoPerteneciente = reinoPerteneciente.substring(0,1)+
			reinoPerteneciente.substring(reinoPerteneciente.indexOf(" ")+1,reinoPerteneciente.indexOf(" ")+2);
		else
			this.reinoPerteneciente = reinoPerteneciente.substring(0,2);
		this.reinoPerteneciente = this.reinoPerteneciente.toUpperCase();
		this.numOrden = numOrden;
		this.nombre = "ejercito_"+this.reinoPerteneciente+"-"+numOrden;
		this.numSoldados = (int)(Math.random()*10+1);
		for (int i = 0; i < this.numSoldados; i++)
			soldados[i] = new Soldado(this.reinoPerteneciente, i); // Añade un soldado especificando su reino y numero de orden
	}
	public void mostrarEjercito() {
		System.out.println("- Ejercito N°"+this.numOrden+" de "+this.reinoPerteneciente);
		for (Soldado soldado : soldados) {
			System.out.println("  - "+soldado);
		}
	}
	public Soldado[] getSoldados() {
		return soldados;
	}
	public String getReinoPerteneciente() {
		return reinoPerteneciente;
	}
	public int getNumOrden() {
		return numOrden;
	}
	@Override
	public String toString () {
		return this.nombre;
	}
}
