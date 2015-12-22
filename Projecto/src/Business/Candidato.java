/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.*;

/**
 *
 * @author Nuno Oliveira
 */
public class Candidato {

	private int bi;
	private Calendar dataNasc;
	private String nome;
	private String prof;
	private String residencia;
	private String naturalidade;
	private String foto;

	public Candidato(String nome, int bi, String prof, Calendar dataNasc, String residencia, String naturalidade,
			String foto) {
		this.nome = nome;
		this.bi = bi;
		this.prof = prof;
		this.dataNasc = dataNasc;
		this.residencia = residencia;
		this.naturalidade = naturalidade;
		this.foto = foto;
	}
	
	public String getNome() {
		return nome;
	}

	public int getBi() {
		return bi;
	}

	public String getProf() {
		return prof;
	}
	
	public Calendar getDataNasc() {
		return dataNasc;
	}

	public String getResidencia() {
		return residencia;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setBi(int bi) {
		this.bi = bi;
	}

	public void setProf(String prof) {
		this.prof = prof;
	}

	public void setResidencia(String residencia) {
		this.residencia = residencia;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Candidato ");
		stringBuilder.append(nome);
		stringBuilder.append(", com BI nº ");
		stringBuilder.append(bi);
		stringBuilder.append(", nascido a ");
		stringBuilder.append(dataNasc.toString());
		stringBuilder.append(", prof: ");
		stringBuilder.append(prof);
		stringBuilder.append(", residencia: ");
		stringBuilder.append(residencia);
		stringBuilder.append(", naturalidade: ");
		stringBuilder.append(naturalidade);
		stringBuilder.append(", foto: ");
		stringBuilder.append(foto);
		return stringBuilder.toString();
	}

	
	@Override
	public boolean equals(Object obj) {
		Candidato c = (Candidato) obj;
		return this.bi == c.getBi();
	}
}
