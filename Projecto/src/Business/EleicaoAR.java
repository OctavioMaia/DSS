package Business;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Data.CirculoInfoDAO;
import Data.ResultadoCirculoARDAO;

public class EleicaoAR extends Eleicao {
	private CirculoInfoDAO circulos;
	private HashSet<Votavel> concorrentes;
	private ResultadoCirculoARDAO resultado;

	public EleicaoAR(int idEleicao, Date data, Map<Integer, Integer> totEleitores) {
		super(idEleicao, data);
		this.circulos = new CirculoInfoDAO(idEleicao);
		for (int i = 1; i <= 22; i++) {
			if (!this.circulos.containsKey(i))
				this.circulos.put(i, new CirculoInfo());
		}
		this.concorrentes = new HashSet<>();
		this.resultado = new ResultadoCirculoARDAO(idEleicao);
		for (int i = 1; i <= 22; i++) {
			if (!this.resultado.containsKey(i))
				this.resultado.put(i, new ResultadoCirculoAR(totEleitores.get(i)));
		}
	}

	public EleicaoAR(int idEleicao, Date data, int estado, boolean permitirVotar, Set<Integer> vot,
			Map<Integer, Integer> totEleitores, HashSet<Votavel> concorrentes) {
		super(idEleicao, data,estado,permitirVotar,vot);
		this.circulos = new CirculoInfoDAO(idEleicao);
		for (int i = 1; i <= 22; i++) {
			if (!this.circulos.containsKey(i))
				this.circulos.put(i, new CirculoInfo());
		}
		this.concorrentes = concorrentes;
		this.resultado = new ResultadoCirculoARDAO(idEleicao);
		for (int i = 1; i <= 22; i++) {
			if (!this.resultado.containsKey(i))
				this.resultado.put(i, new ResultadoCirculoAR(totEleitores.get(i)));
		}
	}
	
	public CirculoInfoDAO getCirculoInfo() {
		return circulos;
	}

	public HashSet<Votavel> getConcorrentes() {
		return concorrentes;
	}

	public ResultadoCirculoARDAO getResultado() {
		return resultado;
	}

	public void setTotEleitores(Map<Integer, Integer> totEleitores) {
		for (int i : totEleitores.keySet()) {
			if (i >= 1 && i <= 22) {
				ResultadoCirculoAR rc = this.resultado.get(i);
				rc.setTotEleitor(totEleitores.get(i));
				this.resultado.put(i, rc);
			}
		}
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
			totEleitores += this.resultado.get(circulo).getTotEleitor();
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
