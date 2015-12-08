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
public class Eleicao {
    private int idEleicao;
    private Date data;
    private int estado;
    private HashSet<Integer> votantes;
    
    public Eleicao(Date data, int estado,int idEleicao) {
    	this.idEleicao=idEleicao;
        this.data = data;
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

	public void addVotante(Integer id) {
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