package Business;

import java.util.HashMap;

import Data.ListaARDAO;
import Exception.ExceptionLimiteCandidatos;
import Exception.ExceptionListaExiste;
import Exception.ExceptionMandanteInvalido;

public class CirculoInfo {
	private Circulo circulo;
	private ListaARDAO listas;
	private int mandatos;
	
	public CirculoInfo(int idEleicao, Circulo circulo){
		this.circulo = circulo;
		this.listas = new ListaARDAO(idEleicao,circulo.getId());
		this.mandatos = 0;
	}
	
	public CirculoInfo(int idEleicao, Circulo circulo, int mandatos) {
		this.circulo = circulo;
		this.listas = new ListaARDAO(idEleicao,circulo.getId());
		this.mandatos = mandatos;
	}

	public Circulo getCirculo() {
		return circulo;
	}

	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}

	public HashMap<Integer,Lista> getListas() {
		HashMap<Integer,Lista> listasRet = new HashMap<>();
		for(int idLista: this.listas.keySet()){
			listasRet.put(idLista,this.listas.get(idLista));
		}
		return listasRet;
	}

	public int getMandatos() {
		return mandatos;
	}

	public void setMandatos(int mandatos) {
		this.mandatos = mandatos;
	}

	public void addLista(Lista lista) throws ExceptionListaExiste, ExceptionLimiteCandidatos{
		if(lista.getNumCandPrim() >= this.mandatos) throw new ExceptionLimiteCandidatos("Limite de candidatos primarios ("+this.mandatos+") excedido");
		for(Lista l: this.listas.values()){
			if(l.equals(lista))
				throw new ExceptionListaExiste();
		}
		lista.setID(this.listas.size()+1);
		this.listas.put(lista.getID(),lista);
	}
	
	public void removeLista(Lista lista){
		this.listas.remove(lista.getID());
	}
	
	public void addCandidatoLista(Lista l, CandidatoAR c) throws ExceptionLimiteCandidatos{
		if((c.getTipo() == 'P') && l.getNumCandPrim() >= this.mandatos){
			throw new ExceptionLimiteCandidatos("Limite de candidatos excedido ("+this.mandatos+")");
		}
		l.addCandidato(c);
		this.listas.put(l.getID(),l);
	}
	
	public void removeCandidato(Lista l, CandidatoAR c){
		l.removeCandidato(c);
		this.listas.put(l.getID(),l);
	}
}
