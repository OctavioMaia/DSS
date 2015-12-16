package Business;

import java.util.Set;
import java.util.TreeSet;

public class Boletim {
	private Set<Listavel> listas;
	
	public Boletim() {
		listas = new TreeSet<Listavel>(new ComparatorListaPRVolta1());
	}

	public Set<Listavel> getListas() {
		return listas;
	}

	public void setListas(Set<Listavel> listas) {
		this.listas = listas;
	}
	
	public void addListavel(Listavel){
		listas.add(e)
	}
	
}
