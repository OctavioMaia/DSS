/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Data.ValidosPRDAO;

/**
 *
 * @author ruifreitas
 */
public class ResultadoCirculoPR {
    private int brancos;
    private int nulos;
    private int totEleitor;
    private ValidosPRDAO validos;

    public ResultadoCirculoPR(int brancos, int nulos, int totEleitor) {
        this.brancos = brancos;
        this.nulos = nulos;
        this.totEleitor = totEleitor;
    }

    public int getBrancos() {
        return brancos;
    }

    public int getNulos() {
        return nulos;
    }

    public int getTotEleitor() {
        return totEleitor;
    }

    public void setBrancos(int brancos) {
        this.brancos = brancos;
    }

    public void setNulos(int nulos) {
        this.nulos = nulos;
    }

    public void setTotEleitor(int totEleitor) {
        this.totEleitor = totEleitor;
    }

    @Override
    public String toString() {
        return "ResultadoCirculoPR{" + "brancos=" + brancos + ", nulos=" + nulos + ", totEleitor=" + totEleitor + ", validos=" + validos + '}';
    }


    
    
    
    
    
            
            
            
}
