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
    private Date dataNasc;
    private String nome;
    private String prof;
    private String residencia;
    private String naturalidade;

    public Candidato(String nome, int bi, String prof, Date dataNasc, String residencia, String naturalidade) {
        this.nome = nome;
        this.bi = bi;
        this.prof = prof;
        this.dataNasc = dataNasc;
        this.residencia = residencia;
        this.naturalidade = naturalidade;
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

    public Date getDataNasc() {
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

	public String toString() {
		return "Candidato " + nome + ", com BI nº"+bi + ", nascido a " + dataNasc.toGMTString();
	}
     
}
