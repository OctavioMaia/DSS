package Business;

import Data.ListaARDAO;

public class CirculoInfo {
	private Circulo circulo;
	private Boletim boletim;
	private ListaARDAO listasDAO;
	private int mandatos;
	
	public CirculoInfo(){
		this.boletim = null;
		this.listasDAO = new ListaARDAO();
		this.mandatos = 0;
	}
	
	public CirculoInfo(int maxCand) {
		this.boletim = null;
		this.listasDAO = new ListaARDAO();
		this.mandatos = maxCand;
	}

	public void addLista(Lista lista){
		this.listasDAO.put(lista.getID(),lista);
	}
	
	public void removeLista(Lista lista){
		this.listasDAO.remove(lista.getID());
	}
	
	public void addCandidatoLista(Lista l, Candidato c){
		Lista lista = this.listasDAO.get(l.getID());
		lista.getCandidatos();
		
		jhjm
	}
}
