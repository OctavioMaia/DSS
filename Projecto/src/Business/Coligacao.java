package Business;

import java.util.HashSet;
import java.util.Set;

public class Coligacao implements Votavel {

	private int id;
	private String sigla;
	private String nome;
	private String simbolo;
	private Set<Partido> partidos;
	private boolean removido;

	public Coligacao(int id, String sigla, String nome, String simbolo, Set<Partido> partidos) {
		this.id = id;
		this.sigla = sigla;
		this.nome = nome;
		this.simbolo = simbolo;
		this.partidos = partidos;
		this.removido = false;
	}

	public Coligacao(int id, String sigla, String nome, String simbolo, Set<Partido> partidos, boolean removido) {
		super();
		this.id = id;
		this.sigla = sigla;
		this.nome = nome;
		this.simbolo = simbolo;
		this.partidos = partidos;
		this.removido = removido;
	}

	public boolean isRemovido() {
		return removido;
	}

	public void setRemovido(boolean removido) {
		this.removido = removido;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public Set<Partido> getPartidos() {
		return partidos;
	}

	public void setPartidos(HashSet<Partido> partidos) {
		this.partidos = partidos;
	}

	@Override
	public boolean equals(Object obj) {
		if (this.getClass() != obj.getClass())
			return false;
		Coligacao col = (Coligacao) obj;
		return this.nome == col.getNome() && this.sigla == col.getSigla() && this.simbolo == col.getSimbolo();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
		result = prime * result + ((simbolo == null) ? 0 : simbolo.hashCode());
		return result;
	}

	@Override
	public String[] toTable() {
		String[] partido = { this.sigla, this.nome, Integer.toString(this.id)};
		return partido;
	}

}
