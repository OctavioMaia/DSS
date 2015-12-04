/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Data.ResultadoPRDAO;

/**
 *
 * @author ruifreitas
 */
public class ResultadoPR {
    private ResultadoPRDAO resPRDAO;
    
    public ResultadoPR(ResultadoPRDAO res){
    	this.setResPRDAO(res);
    }

	public ResultadoPRDAO getResPRDAO() {
		return resPRDAO;
	}

	public void setResPRDAO(ResultadoPRDAO resPRDAO) {
		this.resPRDAO = resPRDAO;
	}   
}
