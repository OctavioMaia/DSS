package Business;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Data.ListaPRDAO;
import Data.ResultadoCirculoPRDAO;
import Exception.ExceptionListaExiste;

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

	public EleicaoPR(int idEleicao, Date data, int estado, boolean permitirVotar, Set<Integer> vot, Set<Integer> vot2,
			boolean volta2, Date data2, Boletim boletim1, Boletim boletim2) {
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

	public ResultadoCirculoPRDAO getVoltaR1() {
		return this.voltaR1;
	}

	public ResultadoCirculoPRDAO getVoltaR2() {
		return this.voltaR2;
	}

	public ListaPRDAO getListas() {
		return this.listas;
	}

	public Set<Integer> getVotantes2() {
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

	@Override
	public void iniciar() {
		super.setEstado(0);
		super.setPermitirVotar(true);
	}

	@Override
	public void addLista(Listavel lista) {
		ListaPR listpr = (ListaPR) lista;
		if (listpr.equals(this.listas.get(listpr.getIdListaPR()))) {
			throw new ExceptionListaExiste("A Lista já se encontra registada");
		}
		listas.put(listpr.getIdListaPR(), listpr);
	}

	@Override
	public void removeLista(Listavel lista) {
		ListaPR listpr = (ListaPR) lista;
		this.listas.remove(listpr.getIdListaPR());
	}

	@Override
	public void addVoto(Listavel lista) {
		ListaPR listpr = (ListaPR) lista;
		if (volta2 == false) {
			ResultadoCirculoPR resPR = this.voltaR1.get(listpr.getIdListaPR());
			resPR.addVoto(listpr.getIdListaPR());
			this.voltaR1.put(resPR.getIdcirculo(), resPR);
		} else {
			ResultadoCirculoPR resPR = this.voltaR2.get(listpr.getIdListaPR());
			resPR.addVoto(listpr.getIdListaPR());
			this.voltaR2.put(resPR.getIdcirculo(), resPR);
		}
	}

	@Override
	public void addVotoNulo(int idCirculo) {
		if (volta2 == false) {
			ResultadoCirculoPR resPR = this.voltaR1.get(idCirculo);
			resPR.addVotoNulo();
			this.voltaR1.put(resPR.getIdcirculo(), resPR);
		} else {
			ResultadoCirculoPR resPR = this.voltaR2.get(idCirculo);
			resPR.addVotoNulo();
			this.voltaR2.put(resPR.getIdcirculo(), resPR);
		}
	}

	@Override
	public void addVotoBranco(int idCirculo) {
		if (volta2 == false) {
			ResultadoCirculoPR resPR = this.voltaR1.get(idCirculo);
			resPR.addVotoBranco();
			this.voltaR1.put(resPR.getIdcirculo(), resPR);
		} else {
			ResultadoCirculoPR resPR = this.voltaR2.get(idCirculo);
			resPR.addVotoBranco();
			this.voltaR2.put(resPR.getIdcirculo(), resPR);
		}
	}

	@Override
	public Boletim getBoletim(int idCirculo) {
		Boletim b = null;
		if(super.estado(0) && volta2==true ){
			b = ;
		}
		return null;
	}

}
