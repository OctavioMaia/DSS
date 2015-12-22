package Business;

import java.util.HashMap;
import java.util.Set;

public class ResultadoGlobalPR {
	private int totEleitores;
	private int brancos;
	private int nulos;
	private Set<ListavelVotos> validos;
	
	public ResultadoGlobalPR(int totEleitores, int brancos, int nulos, Set<ListavelVotos> validos) {
		this.totEleitores = totEleitores;
		this.brancos = brancos;
		this.nulos = nulos;
		this.validos = validos;
	}

	public int getTotEleitores() {
		return totEleitores;
	}

	public void setTotEleitores(int totEleitores) {
		this.totEleitores = totEleitores;
	}

	public int getBrancos() {
		return brancos;
	}

	public void setBrancos(int brancos) {
		this.brancos = brancos;
	}

	public int getNulos() {
		return nulos;
	}

	public void setNulos(int nulos) {
		this.nulos = nulos;
	}

	public Set<ListavelVotos> getValidos() {
		return validos;
	}

	public void setValidos(Set<ListavelVotos> validos) {
		this.validos = validos;
	}
	
	
	
	
}
