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
		//this.boletim.addLista(lista);
	}
	
	public void removeLista(int lista){
		this.listasDAO.remove(lista);
		//this.boletim.removeLista(lista);
	}
}
