/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

/**
 *
 * @author Nuno Oliveira
 * @author jms 04_12_2015
 */
public class Partido implements Votavel {

	private int id;
	private String sigla;
	private String nome;
	private String simbolo;
	private boolean removido;

	public Partido(int id, String sigla, String nome, String simbolo) {
		this.id = id;
		this.sigla = sigla;
		this.nome = nome;
		this.simbolo = simbolo;
		this.removido = false;
	}

	public Partido(int id, String sigla, String nome, String simbolo, boolean removido) {
		this.id = id;
		this.sigla = sigla;
		this.nome = nome;
		this.simbolo = simbolo;
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

	public String getSigla() {
		return sigla;
	}

	public String getNome() {
		return nome;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public String toString() {
		return "Partido " + nome + ", com a sigla " + sigla + " e com o ID " + id;
	}

	@Override
    public boolean equals(Object obj) {
		if(this.getClass() != obj.getClass()) return false;
		Partido part = (Partido) obj;
		return this.nome==part.getNome() && this.sigla==part.getSigla() && this.simbolo==part.getSimbolo();
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
	public Object[] toTable() {
		Object[] partido = { this.nome,this.sigla, this};
		return partido;
	}

}
