package Business;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import Data.CirculoInfoDAO;
import Data.ResultadoCirculoARDAO;

public class EleicaoAR extends Eleicao {
	private int mandatosAssembleia;
	private CirculoInfoDAO circulos;
	private ResultadoCirculoARDAO resultado;
	
	public EleicaoAR(int idEleicao, Calendar data,Collection<Circulo> circulos,int mandatosAssembleia) {
		super(idEleicao, data);
		this.mandatosAssembleia = mandatosAssembleia;
		this.circulos = new CirculoInfoDAO(idEleicao);
		this.resultado = new ResultadoCirculoARDAO(idEleicao);
		Iterator<Circulo> it = circulos.iterator();
		while(it.hasNext()){
			Circulo circulo = it.next();
			if (!this.circulos.containsKey(circulo.getId()))
				this.circulos.put(circulo.getId(), new CirculoInfo(circulo));
			if (!this.resultado.containsKey(circulo.getId()))
				this.resultado.put(circulo.getId(), new ResultadoCirculoAR(circulo));
			}
	}

	public EleicaoAR(int idEleicao, Calendar data, Collection<Circulo> circulos, int estado,int mandatosAssembleia, boolean permitirVotar, Set<Integer> vot) {
		super(idEleicao, data,estado,permitirVotar,vot);
		this.mandatosAssembleia = mandatosAssembleia;
		this.circulos = new CirculoInfoDAO(idEleicao);
		this.resultado = new ResultadoCirculoARDAO(idEleicao);
		Iterator<Circulo> it = circulos.iterator();
		while(it.hasNext()){
			Circulo circulo = it.next();
			if (!this.circulos.containsKey(circulo.getId()))
				this.circulos.put(circulo.getId(), new CirculoInfo(circulo));
			if (!this.resultado.containsKey(circulo.getId()))
				this.resultado.put(circulo.getId(), new ResultadoCirculoAR(circulo));
		}
	}
	
	public int getMandatosAssembleia() {
		return mandatosAssembleia;
	}

	public void setMandatosAssembleia(int mandatosAssembleia) {
		this.mandatosAssembleia = mandatosAssembleia;
	}

	public CirculoInfoDAO getCirculoInfo() {
		return circulos;
	}

	public ResultadoCirculoARDAO getResultado() {
		return resultado;
	}

	public ResultadoGlobalAR getResultadoGlobal(){
		int nulos = 0;
		int brancos = 0;
		int totEleitores = 0;
		HashMap<Votavel,Integer> validos = new HashMap<>();
		HashMap<Votavel,Integer> mandatos = new HashMap<>();
		for(int circulo: this.circulos.keySet()){
			nulos += this.resultado.get(circulo).getNulos();
			brancos += this.resultado.get(circulo).getBrancos();
			totEleitores += this.resultado.get(circulo).getCirculo().getTotEleitores();
			HashMap<Lista,Integer> validosCirculo = this.resultado.get(circulo).getValidos();
			for(Lista lista: validosCirculo.keySet()){
				Votavel mandante = lista.getMandante();
				if(!validos.containsKey(mandante))
					validos.put(mandante, validosCirculo.get(lista));
				else
					validos.put(mandante, validos.get(mandante) + validosCirculo.get(mandante));
			}
			HashMap<Lista,Integer> mandatosCirculo = this.resultado.get(circulo).getMandatos();
			for(Lista lista: mandatosCirculo.keySet()){
				Votavel mandante = lista.getMandante();
				if(!mandatos.containsKey(mandante))
					mandatos.put(mandante, mandatosCirculo.get(lista));
				else
					mandatos.put(mandante, mandatos.get(mandante) + mandatosCirculo.get(mandante));
			}
		}
		return new ResultadoGlobalAR(nulos,brancos,totEleitores,validos,mandatos); 
	}
	
	public ResultadoCirculoAR getResultadoCirculo(int idCirculo){
		return this.resultado.get(idCirculo);
	}
	
	@Override
	public void iniciar(){
		super.setEstado(0);
		super.setPermitirVotar(true);
	}
	
	@Override
	public void addLista(Listavel lista){
		Lista l = (Lista)lista;
		this.circulos.get(l.getCirculo()).addLista(l);
	}
	
	
	/**
	 * atenção ao fazer alteração no DAO das listas alterar tambem no HASHMAP do DAO dos resultados
	 * 
	 */
	@Override
	public void removeLista(Listavel lista){
		Lista l = (Lista)lista;
		this.circulos.get(l.getCirculo()).removeLista(l);
	}
	
	@Override
	public void addVoto(Listavel lista){
		Lista l = (Lista)lista;
    	this.resultado.get(l.getCirculo()).addVoto(l);
    }
	
	@Override
	public void addVotoBranco(int idCirculo){
		this.resultado.get(idCirculo).addVotoBranco();
	}
	
	@Override
	public void addVotoNulo(int idCirculo){
		this.resultado.get(idCirculo).addVotoNulo();
	}
	
}
