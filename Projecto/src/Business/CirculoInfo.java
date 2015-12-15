package Business;

import Data.ListaARDAO;

public class CirculoInfo {
	private Boletim boletim;
	private ListaARDAO listasDAO;
	
	public CirculoInfo(){
		this.boletim = new Boletim();
		this.listasDAO = new ListaARDAO();
	}
	
	public void addLista(Lista lista){
		this.listasDAO.put(lista.getID(),lista);
	}
	
	public void removeLista(Lista lista){
		this.listasDAO.remove(lista.getID());
	}
}
