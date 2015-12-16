package Business;

import java.util.ArrayList;
import java.util.List;

public class Boletim {
	private List<Listavel> listas;
	
	public Boletim(int nListas) {
		listas = new ArrayList<Listavel>(nListas);
	}

	public List<Listavel> getListas() {
		return listas;
	}

	public void setListas(List<Listavel> listas) {
		this.listas = listas;
	}
	
	public void addLista(Listavel l){
		if(l.getClass().getName())
	}
	
}
