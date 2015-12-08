/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.HashMap;

import Data.ResultadoPRDAO;

/**
 *
 * @author ruifreitas
 */
public class ResultadoPR {
    private ResultadoPRDAO resultadosC ;
    
    public ResultadoPR(){
    	this.resultadosC = new ResultadoPRDAO();
    }

	public void addVoto(int idCirculo, int idLista){
		ResultadoCirculoPR res = resultadosC.get(idCirculo);
		res.addVoto(idLista);
		resultadosC.put(idCirculo,res);
	}

}
