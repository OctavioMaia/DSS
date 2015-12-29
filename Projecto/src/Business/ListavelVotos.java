package Business;

public class ListavelVotos {
	private Listavel lista;
	private int votos;
	
	public ListavelVotos(Listavel lista, int votos) {
		this.lista = lista;
		this.votos = votos;
	}

	public Listavel getLista() {
		return lista;
	}

	public void setLista(Listavel lista) {
		this.lista = lista;
	}

	public int getVotos() {
		return votos;
	}

	public void setVotos(int votos) {
		this.votos = votos;
	}

}
