
public class Soldado {
	private static int numEjercito1 = 0;
	private static int numEjercito2 = 0;
	private String nombre; //
	private int nivelAtaque; //
	private int nivelDefensa; //
	private int nivelVida; //
	private int vidaActual; //
	private int velocidad;
	private String actitud;
	private boolean vive;
	private int equipo;
	/**
	 * INICIO DE CONSTRUCTORES
	 */
	public Soldado () {
		this.nombre = "Soldado";
		this.nivelAtaque = (int)(Math.random()*5+1);
		this.nivelDefensa = (int)(Math.random()*5+1);
		this.nivelVida = (int)(Math.random()*5+1);
		this.vidaActual = this.nivelVida;
		this.velocidad = 1;
		this.actitud = "Desconocido";
		this.vive = true;
	}
	public Soldado (int equipo, int num) {
		this();
		this.nombre = this.nombre+equipo+"X"+num;
		this.equipo = equipo;
		if (this.equipo == 1)
			numEjercito1++;
		else
			numEjercito2++;
	}
	// Personalizado
	public Soldado (String nombre, int nV, int equipo) {
		this();
		this.nombre = (nombre.length()<8) ? nombre : nombre.substring(0,7);
		this.nombre += equipo +"xP";
		this.nivelVida = nV;
		this.vidaActual = this.nivelVida;
		this.equipo = equipo;
	}
	// Personalizacion Avanzada
	public Soldado(String nombre, int nivelAtaque, int nivelDefensa, int nivelVida, int vidaActual, int velocidad,
			String actitud, boolean vive, int equipo) {
		this.nombre = nombre;
		this.nivelAtaque = nivelAtaque;
		this.nivelDefensa = nivelDefensa;
		this.nivelVida = nivelVida;
		this.vidaActual = vidaActual;
		this.velocidad = velocidad;
		this.actitud = actitud;
		this.vive = vive;
		this.equipo = equipo;
	}
	/**
	 * FIN DE CONSTRUCTORES
	 */
	
	// Supremacy Logic
	public Soldado clonarSoldado() {
		Soldado copia = new Soldado(this.nombre, this.nivelAtaque, this.nivelDefensa, this.nivelVida, this.vidaActual,
				this.velocidad, this.actitud, this.vive, this.equipo);
		return copia;
	}
	public static boolean comparacionEntre(Soldado s1, Soldado s2) {
		if (
			s1.getNombre().equals(s2.getNombre()) && s1.getNivelAtaque() == s2.getNivelAtaque()
			&& s1.getNivelDefensa() == s2.getNivelDefensa() && s1.getVidaActual() == s2.getVidaActual()
			&& (s1.isVive() && s2.isVive())
			)
			return true;
		else
			return false;			
	}
	public Soldado sumar(Soldado otro, int numEquipo) {
	    return new Soldado(
	        "SuperSol",
	        this.nivelAtaque += otro.nivelAtaque,
	        this.nivelDefensa += otro.nivelDefensa,
	        this.nivelVida += otro.nivelVida,
	        this.vidaActual += otro.vidaActual, 
	        this.velocidad += otro.velocidad, 
	        "Sobrecargado", 
	        true, 
	        this.equipo = numEquipo 
	    );
	}


	// #######
//	public void avanzar() {
//		this.posicion += this.velocidad;
//	}
	public void retroceder() {
		if (this.velocidad > 0) {
			defender();
		} else if (velocidad == 0) {
			this.velocidad -= 1;
		}
	}
	public void huir () {
		this.velocidad += 2;
	}
	public void morir () {
		this.vive = false;
		if (this.equipo == 1)
			numEjercito1--;
		else
			numEjercito2--;
	}
	public void atacar() {
		this.velocidad += 1;
		this.actitud = "Ofensiva";
		//avanzar();
	}
	public void defender() {
		this.velocidad = 0;
		this.actitud = "Defensiva";
	}
	public void serAtacado(Soldado atacante) {
		this.vidaActual -= atacante.getNivelAtaque();
		if (this.vidaActual <= 0)
			morir();
	}
	
	public static int getNumEjercito1() {
		return numEjercito1;
	}
	public static int getNumEjercito2() {
		return numEjercito2;
	}
	public static void setNumEjercito1(int numEjercito1) {
		Soldado.numEjercito1 = numEjercito1;
	}
	public static void setNumEjercito2(int numEjercito2) {
		Soldado.numEjercito2 = numEjercito2;
	}
	
	// Getters
	public String getNombre() {
	    return nombre;
	}
	public int getNivelAtaque() {
	    return nivelAtaque;
	}
	public int getNivelDefensa() {
	    return nivelDefensa;
	}
	public int getNivelVida() {
	    return nivelVida;
	}
	public int getVidaActual() {
	    return vidaActual;
	}
	public int getVelocidad() {
	    return velocidad;
	}
	public String getActitud() {
	    return actitud;
	}
	public int getEquipo() {
		return equipo;
	}
	public boolean isVive() {
	    return vive;
	}
//	public int getPosicion() {
//		return posicion;
//	}
	// Setters
	public void setNombre(String nombre) {
	    this.nombre = nombre;
	}
	public void setNivelAtaque(int nivelAtaque) {
	    this.nivelAtaque = nivelAtaque;
	}
	public void setNivelDefensa(int nivelDefensa) {
	    this.nivelDefensa = nivelDefensa;
	}
	public void setNivelVida(int nivelVida) {
	    this.nivelVida = nivelVida;
	}
	public void setVidaActual(int vidaActual) {
	    this.vidaActual = vidaActual;
	}
	public void setVelocidad(int velocidad) {
	    this.velocidad = velocidad;
	}
	public void setActitud(String actitud) {
	    this.actitud = actitud;
	}
	public void setVive(boolean vive) {
	    this.vive = vive;
	}
//	public void setPosicion(int posicion) {
//		this.posicion = posicion;
//	}
	@Override
	public String toString() {
		return "Nombre:"+this.nombre+"\nAtaque:"+this.nivelAtaque+"\nDefensa:"+this.nivelDefensa+
				"\nNivel de Vida:"+this.nivelVida+"\nVida actual:"+this.vidaActual+"\nVelocidad:"+
				this.velocidad+"\nActitud:"+this.actitud+"\nPosicion:"
				+"\nVive:"+this.vive+"\n";
	}
}

