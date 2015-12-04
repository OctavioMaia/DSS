/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

/**
 *
 * @author Nuno Oliveira
 */
public class Partido {
    
    private int id;
    private String sigla;
    private String nome;
    private String simbolo;

    public Partido() {
    }
    
    public Partido(int id, String sigla, String nome, String simbolo) {
        this.id = id;
        this.sigla = sigla;
        this.nome = nome;
        this.simbolo = simbolo;
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
    
    public String toString(){
    	return "Partido " + nome + ", com a sigla " + sigla + " e com o ID " + id;
    }
    
}
