package Business;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Exception.ExceptionListaExiste;

public class ResultadoCirculoAR {
	private Circulo circulo;
	private int totEleitores;
	private int brancos;
	private int nulos;
	private Map<Lista, Integer> validos;
	private Map<Lista, Integer> mandatos;

	public ResultadoCirculoAR(Circulo c) {
		this.circulo = c;
		this.totEleitores = c.getTotEleitores();
		this.brancos = 0;
		this.nulos = 0;
		this.validos = new HashMap<>();
		this.mandatos = new HashMap<>();
	}

	public ResultadoCirculoAR(Circulo c, int totEleitores, int brancos, int nulos, Map<Lista, Integer> validos,
			Map<Lista, Integer> mandatos) {
		this.circulo = c;
		this.totEleitores = totEleitores;
		this.brancos = brancos;
		this.nulos = nulos;
		this.validos = validos;
		this.mandatos = mandatos;
	}
	
	public int getTotEleitores() {
		return totEleitores;
	}

	public void setTotEleitores(int totEleitores) {
		this.totEleitores = totEleitores;
	}
	
	public void atualizarTotEleitores(){
		this.totEleitores = this.circulo.getTotEleitores();
	}

	public int getBrancos() {
		return brancos;
	}

	public int getNulos() {
		return nulos;
	}
	
	public int getAbstencao(){
		int totVotos=0,abstencao;
		Collection<Integer> c = validos.values();
		Iterator<Integer> i = c.iterator();
		
		while(i.hasNext()){
			totVotos+=i.next();
		}
		
		abstencao = totEleitores-totVotos-brancos-nulos;
		
		return abstencao;
	}

	public void setBrancos(int brancos) {
		this.brancos = brancos;
	}

	public void setNulos(int nulos) {
		this.nulos = nulos;
	}

	public Circulo getCirculo() {
		return circulo;
	}

	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}

	public Map<Lista, Integer> getValidos() {
		return validos;
	}

	public void setValidos(HashMap<Lista, Integer> validos) {
		this.validos = validos;
	}

	public Map<Lista, Integer> getMandatos() {
		return mandatos;
	}

	public void setMandatos(Map<Lista, Integer> mandatos2) {
		this.mandatos = mandatos2;
	}

	public void setMandatosLista(Lista lista, int mandatos) {
		this.mandatos.put(lista, mandatos);
	}
	
	public void addLista(Lista lista){
		this.validos.put(lista,0);
		this.mandatos.put(lista,0);
	}
	
	public void removeLista(Lista lista){
		this.validos.remove(lista);
		this.mandatos.remove(lista);
	}

	public void addVoto(Lista lista) {
		if(this.validos.get(lista)==null){
			this.validos.put(lista, 1);
		}else{
			this.validos.put(lista, this.validos.get(lista) + 1);	
		}
	}

	public void addVotoBranco() {
		this.brancos++;
	}

	public void addVotoNulo() {
		this.nulos++;
	}
}