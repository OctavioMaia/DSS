/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.HashMap;

import Exception.ExceptionListaNaoExiste;

/**
 * Classe que contem os resultados da Eleição.
 * Onde são guardados os votos.
 * @author ruifreitas
 */
public class ResultadoCirculoPR {
	private Circulo circulo;
	private int brancos;
	private int nulos;
	private HashMap<ListaPR, Integer> validos;

	public ResultadoCirculoPR(Circulo c) {
		this.circulo = c;
		this.brancos = 0;
		this.nulos = 0;
		this.validos = new HashMap<>();
	}

	public ResultadoCirculoPR(Circulo c, int brancos, int nulos, HashMap<ListaPR, Integer> validos) {
		this.circulo = c;
		this.brancos = brancos;
		this.nulos = nulos;
		this.validos = validos;
	}

	public Circulo getCirculo() {
		return circulo;
	}

	public void setCirculo(Circulo circulo) {
		this.circulo = circulo;
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

	public HashMap<ListaPR, Integer> getValidos() {
		return validos;
	}

	public void setValidos(HashMap<ListaPR, Integer> validos) {
		this.validos = validos;
	}

	public void addVoto(ListaPR l) throws ExceptionListaNaoExiste{
		if(this.validos.containsKey(l)){
			this.validos.put(l, this.validos.get(l.getIdListaPR()) + 1);
		}else{
			throw new ExceptionListaNaoExiste("Impossivel votar numa lista que nao existe");
		}
	}

	public void addVotoBranco() {
		this.brancos++;
	}

	public void addVotoNulo() {
		this.nulos++;
	}
}
