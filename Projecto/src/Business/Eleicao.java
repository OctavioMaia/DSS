/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.Date;

/**
 *
 * @author Octavio
 */
public class Eleicao {
    
    private Date data;
    private int estado;
    
    public Eleicao(Date data, int estado) {
        this.data = data;
        this.estado = estado;
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
    
    public String toString(){
    	return "Eleicao na data " + data.toGMTString();
    }
    
}