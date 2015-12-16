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
public class Eleitor {
    
    private String nome;
    private int circulo;
    private int nIdent;
    private String pin;

    public Eleitor(String nome, int circulo, int nIdent, String pin) {
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

    public String getPin() {
        return pin;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setnIdent(int nIdent) {
        this.nIdent = nIdent;
    }

    public void setPin(String pin) {
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
    
    public boolean verificaPin(String pass) {
        return (this.pin.equals(pass));
    }
    
    public String toString(){
    	return "Eleitor " + nome + ", com o numero de identificacao " + nIdent + " e pertencente ao circulo " + circulo;
    }
    
    public boolean autenticar(int id, String pin){
    	if(this.nIdent == id && this.pin == pin){
    		return true;
    	}
    	return false;
    }
    
    
}
