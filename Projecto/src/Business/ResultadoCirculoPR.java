/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.HashMap;

/**
 *
 * @author ruifreitas
 */
public class ResultadoCirculoPR {
	private int idCirculo;
    private int brancos;
    private int nulos;
    private int totEleitores;
    private HashMap<Integer,Integer> validos;
    private int idcirculo;
    
    public ResultadoCirculoPR(){
    	this.brancos = 0;
    	this.nulos = 0;
    	this.totEleitores = 0;
    	this.validos =  new HashMap<>();
    	this.idcirculo=0;
    }
    
	public ResultadoCirculoPR(int brancos, int nulos, int totEleitores, HashMap<Integer, Integer> validos, int idcirculo) {
		this.brancos = brancos;
		this.nulos = nulos;
		this.totEleitores = totEleitores;
		this.validos = validos;
		this.idcirculo=idcirculo;
	}

	public int getIdcirculo(){
		return this.idcirculo;
	}
	
	public void setIdcirculo(int idcirculo){
		this.idcirculo=idcirculo;
	}
	
	public int getBrancos() {
		return brancos;
	}

	public void setBrancos(int brancos) {
		this.brancos = brancos;
	}

	public int getNulos() {
		return nulos;
	}

	public void setNulos(int nulos) {
		this.nulos = nulos;
	}

	public int getTotEleitores() {
		return totEleitores;
	}

	public void setTotEleitores(int totEleitores) {
		this.totEleitores = totEleitores;
	}

	public HashMap<Integer, Integer> getValidos() {
		return validos;
	}

	public void setValidos(HashMap<Integer, Integer> validos) {
		this.validos = validos;
	}

	public void addVoto(int idLista){
		this.validos.put(idLista, this.validos.get(idLista)+1);
	}     
}
