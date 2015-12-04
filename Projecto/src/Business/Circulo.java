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
public class Circulo {
    
    private int id;
    private String nome;
    private int totEleitores;

    public Circulo () {
        id = 0;
        nome = null;
        totEleitores = 0;
    }
    
    public Circulo(int id, String nome, int totEleitores) {
        this.id = id;
        this.nome = nome;
        this.totEleitores = totEleitores;
    }
    
    public void addEleitor() {
        this.totEleitores++;
    }
    
    public void resetEleitores() {
        this.totEleitores = 0;
    }
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getTotEleitores() {
        return totEleitores;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTotEleitores(int totEleitores) {
        this.totEleitores = totEleitores;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return (this==(Circulo) obj);
    }
    
}
