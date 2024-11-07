
public class Soldado {
	private String nombre;
	private int nivelAtaque;
	private int nivelDefensa;
	private int nivelVida; 
	private int vidaActual;
	private int velocidad;
	private String actitud;
	private boolean vive;
	private String reino;
	private String key;
	/**
	 * INICIO DE CONSTRUCTORES
	 */
	public Soldado () {
		this.nombre = "soldado";
		this.nivelAtaque = (int)(Math.random()*5+1);
		this.nivelDefensa = (int)(Math.random()*5+1);
		this.nivelVida = (int)(Math.random()*5+1);
		this.vidaActual = this.nivelVida;
		this.velocidad = 1;
		this.actitud = "Desconocido";
		this.vive = true;
		this.reino = "Desconocido";
	}
	public Soldado (String nombre, String reino, int na, int nd, int nv, int va) {
		this.nombre = nombre;
		this.nivelAtaque = na;
		this.nivelDefensa = nd;
		this.nivelVida = nv;
		this.vidaActual = va;
		this.velocidad = 1;
		this.actitud = "Desconocido";
		this.vive = true;
		this.reino = reino;
	}
	public Soldado (String reino, String key) {
		this();
		this.reino = reino;
		this.key = key;
		this.nombre = this.nombre+"_"+this.reino+"-"+this.key;
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
		this.vidaActual = 0;
	}
	public void atacar() {
		this.velocidad += 1;
		this.actitud = "Ofensiva";
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
	public String getReino() {
		return reino;
	}
	public String getKey() {
		return key;
	}
	public boolean isVive() {
	    return vive;
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
	@Override
//	public String toString() {
//		return "Reino: "+this.reino+"\nNombre:"+this.nombre+"\nAtaque:"+this.nivelAtaque+"\nDefensa:"+this.nivelDefensa+
//				"\nNivel de Vida:"+this.nivelVida+"\nVida actual:"+this.vidaActual+"\nVelocidad:"+
//				this.velocidad+"\nActitud:"+this.actitud+"\nVive:"+this.vive+"\n";
//	}
	public String toString() {
		return this.nombre;
	}
}

