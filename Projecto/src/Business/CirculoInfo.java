package Business;

import Data.ListaARDAO;

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

	public void addLista(Lista lista){
		this.listas.put(lista.getID(),lista);
	}
	
	public void removeLista(Lista lista){
		this.listas.remove(lista.getID());
	}
	
	public void addCandidatoLista(Lista l, CandidatoAR c){
		l.addCandidato(c);
		this.listas.put(l.getID(),l);
	}
	
	public void removeCandidato(Lista l, CandidatoAR c){
		l.removeCandidato(c);
		this.listas.put(l.getID(),l);
	}
}
