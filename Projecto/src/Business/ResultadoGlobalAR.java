/**
 * 
 */
package Business;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Pedro Pinto
 *
 */
public class ResultadoGlobalAR {
	private int brancos;
	private int nulos;
	private int totEleitores;
	private HashMap<Votavel,Integer> validos;
	private HashMap<Votavel,Integer> mandatos;
	
	public ResultadoGlobalAR(){
		this.brancos = 0;
		this.nulos = 0;
		this.totEleitores = 0;
		this.validos = new HashMap<>();
		this.mandatos = new HashMap<>();
	}
	
	public ResultadoGlobalAR(int brancos, int nulos, int totEleitores, Map<Votavel,Integer> validos, Map<Votavel,Integer> mandatos){
		this.brancos = brancos;
		this.nulos = nulos;
		this.totEleitores = totEleitores;
		this.validos = new HashMap<>();
		for(Votavel v: validos.keySet()){
			this.validos.put(v,validos.get(v));
		}
		this.mandatos = new HashMap<>();
		for(Votavel v: mandatos.keySet()){
			this.mandatos.put(v,mandatos.get(v));
		}
	}
	
	public void addResultado(int brancos, int nulos, int totEleitores, Map<Votavel,Integer> validos, Map<Votavel,Integer> mandatos){
		this.brancos += brancos;
		this.nulos += nulos;
		this.totEleitores += totEleitores;
		for(Votavel v: validos.keySet()){
			if(!this.validos.containsKey(v)){
				this.validos.put(v,validos.get(v));
			}
			else this.validos.put(v,this.validos.get(v)+validos.get(v));
		}
		for(Votavel v: mandatos.keySet()){
			if(!this.mandatos.containsKey(v)){
				this.mandatos.put(v,mandatos.get(v));
			}
			else this.mandatos.put(v,this.mandatos.get(v)+mandatos.get(v));
		}
	}

	public int getBrancos() {
		return brancos;
	}

	public int getNulos() {
		return nulos;
	}

	public int getTotEleitores() {
		return totEleitores;
	}

	public HashMap<Votavel, Integer> getValidos() {
		return validos;
	}

	public HashMap<Votavel, Integer> getMandatos() {
		return mandatos;
	}

	public void setBrancos(int brancos) {
		this.brancos = brancos;
	}

	public void setNulos(int nulos) {
		this.nulos = nulos;
	}

	public void setTotEleitores(int totEleitores) {
		this.totEleitores = totEleitores;
	}

	public void setValidos(HashMap<Votavel, Integer> validos) {
		this.validos = validos;
	}

	public void setMandatos(HashMap<Votavel, Integer> mandatos) {
		this.mandatos = mandatos;
	}
}
