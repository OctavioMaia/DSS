package Business;

import Data.ListaARDAO;
import Exception.ExceptionLimiteCandidatos;
import Exception.ExceptionListaExiste;

public class CirculoInfo {
	private Circulo circulo;
	private Boletim boletim;
	private ListaARDAO listas;
	private int mandatos;
	
	public CirculoInfo(Circulo circulo){
		this.circulo = circulo;
		this.boletim = null;
		this.listas = new ListaARDAO();
		this.mandatos = 0;
	}
	
	public CirculoInfo(Circulo circulo, int mandatos) {
		this.circulo = circulo;
		this.boletim = null;
		this.listas = new ListaARDAO();
		this.mandatos = mandatos;
	}

	public Circulo getCirculo() {
		return circulo;
	}

	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
	}

	public ListaARDAO getListas() {
		return listas;
	}

	public int getMandatos() {
		return mandatos;
	}

	public void setMandatos(int mandatos) {
		this.mandatos = mandatos;
	}

	public void addLista(Lista lista) throws ExceptionListaExiste{

		for(Lista l: this.listas.values()){
			if(l.equals(lista))
				throw new ExceptionListaExiste();
			/*
			 * TODO: Caso a lista pertenca a um partido, verificar se esse partido ja existe
			 * 		 Caso pertenca a uma coligacao, verificar se algum dos partidos da coligacao ja existe
			 * 		 E atirar excepcoes 
			 */
		}
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
