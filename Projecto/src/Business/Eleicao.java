/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.Date;
import java.util.HashSet;

/**
 *
 * @author Octavio
 */
public abstract class Eleicao {
	
	private static final int CRIADA = -1;
	private static final int ATIVA = 0;
	private static final int TERMINADA = 1;

    private int idEleicao;
    private Date data;
    private boolean primitirVotar;
    private int estado;
    private HashSet<Integer> votantes;
    
    public Eleicao(int idEleicao, Date data){
    	this.idEleicao = idEleicao;
    }
    
    public Eleicao(Date data, int estado,int idEleicao) {
    	this.idEleicao=idEleicao;
        this.data = data;
        this.primitirVotar = false;
        this.estado = estado;
        this.votantes = new HashSet<Integer>();
    }    
    
    public int getIdEleicao(){
    	return this.idEleicao;
    }
    public Date getData() {
        return data;
    }

    public int getEstado() {
        return estado;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
	public HashSet<Integer> getVotantes() {
		return votantes;
	}

	public void addVotante(int id) {
		this.votantes.add(id);
	}
	
	public void addVotante(Eleitor e){
		this.votantes.add(e.getnIdent());
	}
	
    @SuppressWarnings("deprecation")
	public String toString(){
    	return "Eleicao na data " + data.toGMTString();
    }  
}