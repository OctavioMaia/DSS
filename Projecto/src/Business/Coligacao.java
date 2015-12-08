package Business;

import java.util.HashSet;

public class Coligacao implements Votavel{
	
	private int id;
	private String sigla;
	private String nome;
	private String simbolo;
	private HashSet<Integer> partidos;
	
	public Coligacao(int id, String sigla, String nome, String simbolo, HashSet<Integer> partidos) {
		this.id = id;
		this.sigla = sigla;
		this.nome = nome;
		this.simbolo = simbolo;
		this.partidos = new HashSet<Integer>();
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

	public HashSet<Integer> getPartidos() {
		return partidos;
	}

	public void setPartidos(HashSet<Integer> partidos) {
		this.partidos = partidos;
	}

}
