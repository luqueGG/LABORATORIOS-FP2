public class Soldado {
	private String nombre; //
	private int nivelAtaque; //
	private int nivelDefensa; //
	private int nivelVida; //
	private int vidaActual; //
	private int velocidad;
	private String actitud;
	private boolean vive;
	private int posicion;
	public Soldado () {
		this.nombre = "Soldado";
		this.nivelAtaque = (int)(Math.random()*5+1);
		this.nivelDefensa = (int)(Math.random()*5+1);
		this.nivelVida = (int)(Math.random()*5+1);
		this.vidaActual = this.nivelVida;
		this.velocidad = 1;
		this.actitud = "Desconocido";
		this.posicion = (int)(Math.random()*5+1);
		this.vive = true;
	}
	public Soldado (int equipo, int num) {
		this();
		this.nombre = "Soldado"+equipo+"X"+num;
	}
	public void avanzar() {
		this.posicion += this.velocidad;
	}
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
	}
	public void atacar() {
		this.velocidad += 1;
		this.actitud = "Ofensiva";
		avanzar();
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
	public boolean isVive() {
	    return vive;
	}
	public int getPosicion() {
		return posicion;
	}
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
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	@Override
	public String toString() {
		return "Nombre:"+this.nombre+"\nAtaque:"+this.nivelAtaque+"\nDefensa:"+this.nivelDefensa+
				"\nNivel de Vida:"+this.nivelVida+"\nVida actual:"+this.vidaActual+"\nVelocidad:"+
				this.velocidad+"\nActitud:"+this.actitud+"\nPosicion:"+this.posicion
				+"\nVive:"+this.vive+"\n";
	}
}

