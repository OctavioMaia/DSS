package Business;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Data.ListaPRDAO;
import Data.ResultadoCirculoPRDAO;

public class EleicaoPR extends Eleicao {
	private boolean volta2;
	private Date data2;
	private ResultadoCirculoPRDAO voltaR1;
	private ResultadoCirculoPRDAO voltaR2;
	private Boletim boletim1;
	private Boletim boletim2;
	private ListaPRDAO listas;
	private Set<Integer> votantes2;

	public EleicaoPR(int idEleicao, Date data) {
		super(idEleicao, data);
		this.volta2 = false;
		this.data2 = null;
		this.voltaR1 = new ResultadoCirculoPRDAO(idEleicao, 1);
		this.voltaR2 = new ResultadoCirculoPRDAO(idEleicao, 2);
		this.boletim1 = null;
		this.boletim2 = null;
		this.listas = new ListaPRDAO();
		this.votantes2 = new HashSet<>();
	}

	public EleicaoPR(int idEleicao, Date data, int estado, boolean permitirVotar, Set<Integer> vot,Set<Integer> vot2, boolean volta2, Date data2,
			Boletim boletim1, Boletim boletim2) {
		super(idEleicao, data, estado, permitirVotar, vot);
		this.volta2 = volta2;
		this.data2 = data2;
		this.voltaR1 = new ResultadoCirculoPRDAO(idEleicao, 1);
		this.voltaR2 = new ResultadoCirculoPRDAO(idEleicao, 2);
		this.boletim1 = boletim1;
		this.boletim2 = boletim2;
		this.listas = new ListaPRDAO();
		this.votantes2 = vot2;
	}
	
	public ResultadoCirculoPRDAO getVoltaR1(){
		return this.voltaR1;
	}
	public ResultadoCirculoPRDAO getVoltaR2(){
		return this.voltaR2;
	}
	
	public ListaPRDAO getListas(){
		return this.listas;
	}
	
	public Set<Integer> getVotantes2(){
		return this.votantes2;
	}
	public boolean isVolta2() {
		return volta2;
	}

	public void setVolta2(boolean volta2) {
		this.volta2 = volta2;
	}

	public Date getData2() {
		return data2;
	}

	public void setData2(Date data2) {
		this.data2 = data2;
	}

}
