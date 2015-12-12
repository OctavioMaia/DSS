/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
	private boolean permitirVotar;
	private int estado;
	private Set<Integer> votantes;

	public Eleicao(int idEleicao, Date data) {
		this.idEleicao = idEleicao;
		this.data = data;
		this.permitirVotar = false;
		this.estado = CRIADA;
		this.votantes = new HashSet<>();
	}

	public Eleicao(int idEleicao, Date data, int estado, boolean permitirVotar, Set<Integer> vot) {
		this.idEleicao = idEleicao;
		this.data = data;
		this.permitirVotar = permitirVotar;
		this.estado = estado;
		this.votantes = vot;
	}

	public int getIdEleicao() {
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

	public Set<Integer> getVotantes() {
		return votantes;
	}

	public boolean isPermitirVotar() {
		return permitirVotar;
	}

	public void setPermitirVotar(boolean permitirVotar) {
		this.permitirVotar = permitirVotar;
	}

	public void setIdEleicao(int idEleicao) {
		this.idEleicao = idEleicao;
	}

	public void setVotantes(HashSet<Integer> votantes) {
		this.votantes = votantes;
	}

	public void addVotante(int id) {
		this.votantes.add(id);
	}

	public void addVotante(Eleitor e) {
		this.votantes.add(e.getnIdent());
	}

	public void terminar() {
		this.estado = TERMINADA;
		this.permitirVotar = false;
	}

	public boolean estado(int est) {
		if (this.estado == est)
			return true;
		return false;
	}

	public void iniciar() {
	}

	public void votar(int idCirculo, Listavel lista) {
	}
}