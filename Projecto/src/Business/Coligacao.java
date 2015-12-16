package Business;

import java.util.HashSet;
import java.util.Set;

public class Coligacao implements Votavel{
	
	private int id;
	private String sigla;
	private String nome;
	private String simbolo;
	private HashSet<Partido> partidos;
	
	public Coligacao(int id, String sigla, String nome, String simbolo, Set<Partido> partidos) {
		this.id = id;
		this.sigla = sigla;
		this.nome = nome;
		this.simbolo = simbolo;
		this.partidos = new HashSet<Partido>();
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

	public HashSet<Partido> getPartidos() {
		return partidos;
	}

	public void setPartidos(HashSet<Partido> partidos) {
		this.partidos = partidos;
	}
	
	public boolean equals(Object obj) {
		if(this.getClass() != obj.getClass()) return false; 
		Coligacao col = (Coligacao) obj;
		return this.nome==col.getNome() && this.sigla==col.getSigla() && this.simbolo==col.getSimbolo();
	}

}
