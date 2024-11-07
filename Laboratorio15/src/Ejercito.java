import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
public class Ejercito {
	private final int MAX_SOLDADOS = 10;
	private String nombre;
	private String nombreReino;
	private int numSoldados;
	private HashMap<String, Soldado> misSoldados = new HashMap<String, Soldado>();
	
	public Ejercito (String nombreReino) {
		this.nombreReino = nombreReino;
		this.numSoldados = (int)(Math.random()*MAX_SOLDADOS);
		for (int i = 0; i <= numSoldados; i++) 
			misSoldados.put(Integer.toString(i), new Soldado(nombreReino, Integer.toString(i)));
		this.nombre = "Ejerito_"+this.nombreReino+"1xp";
	}
	public Ejercito (String nombreReino, int numSoldados) {
		this.nombreReino = nombreReino;
		if (numSoldados <= 10 && numSoldados >= 0)
			this.numSoldados = numSoldados;
		else {
			this.numSoldados = (int)(Math.random()*MAX_SOLDADOS);
		}
	}
	public void agregarSoldado (String key, String nombre, int na, int nd, int nv, int va) {
		misSoldados.put(key, new Soldado(nombre, this.nombreReino, na, nd, nv, va));
	}
 	public Soldado getSoldadoMayorAtaque() {
	    Soldado maxAtaque = null;
	    for (Soldado soldado : misSoldados.values()) {
	        if (maxAtaque == null || soldado.getNivelAtaque() > maxAtaque.getNivelAtaque()) {
	            maxAtaque = soldado;
	        }
	    }
	    return maxAtaque;
	}
	public void mostrarRankingPorNivelVida() {
	    HashMap<String, Soldado> copiaSoldados = new HashMap<>(misSoldados); // Copia temporal de soldados
	    Soldado maxVidaSoldado, soldado;
	    String maxKey;
	    while (!copiaSoldados.isEmpty()) {
	        maxVidaSoldado = null;
	        maxKey = "";

	        for (Map.Entry<String, Soldado> entry : copiaSoldados.entrySet()) {
	            soldado = entry.getValue();
	            if (maxVidaSoldado == null || soldado.getNivelVida() > maxVidaSoldado.getNivelVida()) {
	                maxVidaSoldado = soldado;
	                maxKey = entry.getKey();
	            }
	        }
	        
	        System.out.println(maxVidaSoldado);
	        copiaSoldados.remove(maxKey);
	    }
	}

	public void mostrarEjercito () {
		System.out.println(misSoldados);
	}
	public void borrarEjercito () {
		misSoldados.clear();
	}
	public void borrarSoldado(String key) {
		misSoldados.remove(key);
	}
	
	public String getNombre() {
		return nombre;
	}
	public HashMap<String, Soldado> getMisSoldados() {
		return misSoldados;
	}
	public String getNombreReino() {
		return nombreReino;
	}
	@Override
	public String toString() {
		String datos = "";
		Iterator<Map.Entry<String, Soldado>> iterador = misSoldados.entrySet().iterator();
        while (iterador.hasNext()) {
            Map.Entry<String, Soldado> entry = iterador.next();            
            datos += "- "+entry.getValue()+"\n";
        }
        iterador.remove();
		return "Reino del ejercito: "+this.nombreReino+"\nLista Soldados: "+datos+"\n";
	}
}
