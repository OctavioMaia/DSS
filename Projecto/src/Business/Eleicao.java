/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import Exception.*;

/**
 *
 * @author Octavio
 */
public abstract class Eleicao {

	private static final int CRIADA = -1;
	private static final int ATIVA = 0;
	private static final int TERMINADA = 1;

	private int idEleicao;
	private Calendar data;
	private boolean permitirVotar;
	private int estado;
	private Set<Integer> votantes;

	public Eleicao(int idEleicao, Calendar data) {
		this.idEleicao = idEleicao;
		this.data = data;
		this.permitirVotar = false;
		this.estado = CRIADA;
		this.votantes = new HashSet<>();
	}

	public Eleicao(int idEleicao, Calendar data, int estado, boolean permitirVotar, Set<Integer> vot) {
		this.idEleicao = idEleicao;
		this.data = data;
		this.permitirVotar = permitirVotar;
		this.estado = estado;
		this.votantes = vot;
	}

	public int getIdEleicao() {
		return this.idEleicao;
	}

	public Calendar getData() {
		return data;
	}

	public int getEstado() {
		return estado;
	}

	public void setData(Calendar data) {
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

	public int numeroVotos() {
		return this.votantes.size();
	}

	public boolean estado(int est) {
		if (this.estado == est)
			return true;
		return false;
	}

	public abstract void iniciar() throws ExceptionIniciarEleicao;

	public abstract void terminar() throws ExceptionTerminarEleicao;

	public abstract void addLista(Listavel lista)
			throws ExceptionListaExiste, ExceptionLimiteCandidatos, ExceptionMandanteInvalido, ExceptionEleicaoEstado;

	public abstract void removeLista(Listavel lista);

	public abstract void addVoto(Listavel lista, Eleitor eleitor);

	public abstract void addVotoNulo(Eleitor eleitor);

	public abstract void addVotoBranco(Eleitor eleitor);
	
	public abstract Object[] toTable();

	public void alterarData(Calendar dataInicio) {
		data=dataInicio;
	}

	public abstract boolean eleitorVotar(Eleitor e);
}
