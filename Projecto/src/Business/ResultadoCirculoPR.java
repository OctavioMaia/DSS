/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import Exception.ExceptionListaNaoExiste;

/**
 * Classe que contem os resultados da Eleição. Onde são guardados os votos.
 * 
 * @author ruifreitas
 */
public class ResultadoCirculoPR {
	private Circulo circulo;
	private int totEleitores;
	private int brancos;
	private int nulos;
	private HashMap<ListaPR, Integer> validos;

	public ResultadoCirculoPR(Circulo c) {
		this.circulo = c;
		this.totEleitores = c.getTotEleitores();
		this.brancos = 0;
		this.nulos = 0;
		this.validos = new HashMap<>();
	}

	public ResultadoCirculoPR(Circulo c, int totEleitores, int brancos, int nulos, HashMap<ListaPR, Integer> validos) {
		this.circulo = c;
		this.totEleitores = totEleitores;
		this.brancos = brancos;
		this.nulos = nulos;
		this.validos = validos;
	}

	public int getTotEleitores() {
		return totEleitores;
	}

	public void setTotEleitores(int totEleitores) {
		this.totEleitores = totEleitores;
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
	
	public int getAbstencao(){
		int totVotos=0,abstencao;
		Collection<Integer> c = validos.values();
		Iterator<Integer> i = c.iterator();
		
		while(i.hasNext()){
			totVotos+=i.next();
		}
		
		abstencao = totEleitores-totVotos-brancos-nulos;
		
		return abstencao;
	}

	public HashMap<ListaPR, Integer> getValidos() {
		return validos;
	}

	public void setValidos(HashMap<ListaPR, Integer> validos) {
		this.validos = validos;
	}

	public void addVoto(ListaPR l) {
		this.validos.put(l, this.validos.get(l) + 1);
	}

	public void addVotoBranco() {
		this.brancos++;
	}

	public void addVotoNulo() {
		this.nulos++;
	}

	public void addListas(Collection<ListaPR> listas) {
		for (ListaPR lista : listas) {
			if (!this.validos.containsKey(lista)) {
				this.validos.put(lista, 0);
			}
		}
	}

	public int votosLista(ListaPR lista) {
		return this.validos.get(lista);
	}

	public void atualizarTotEleitores(){
		this.totEleitores = this.circulo.getTotEleitores();
	}
}
