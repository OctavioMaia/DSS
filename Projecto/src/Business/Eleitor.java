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
public class Eleitor {
    
    private String nome;
    private int circulo;
    private int nIdent;
    private int pin;

    public Eleitor(String nome, int circulo, int nIdent, int pin) {
        this.nome = nome;
        this.circulo = circulo;
        this.nIdent = nIdent;
        this.pin = pin;
    }
    
    public String getNome() {
        return nome;
    }
    
    public int getCirculo () {
        return this.circulo;
    }

    public int getnIdent() {
        return nIdent;
    }

    public int getPin() {
        return pin;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setnIdent(int nIdent) {
        this.nIdent = nIdent;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Eleitor other = (Eleitor) obj;
        
        return (this.nIdent == other.nIdent);
    }
    
    public boolean verificaPin(int pass) {
        return (this.pin == pass);
    }
    
}
