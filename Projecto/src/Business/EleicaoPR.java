package Business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import Data.ListaPRDAO;
import Data.ResultadoCirculoPRDAO;
import Exception.ExceptionListaExiste;


/**
 * Passar circulos tambem nos construtores
 */

public class EleicaoPR extends Eleicao {
	private boolean volta2;
	private Calendar data2;
	private ResultadoCirculoPRDAO voltaR1;
	private ResultadoCirculoPRDAO voltaR2;
	private Boletim boletim1;
	private Boletim boletim2;
	private ListaPRDAO listas;
	private Set<Integer> votantes2;

	public EleicaoPR(int idEleicao, Calendar data) {
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

	public EleicaoPR(int idEleicao, Calendar data, int estado, boolean permitirVotar, Set<Integer> vot, Set<Integer> vot2,
			boolean volta2, Calendar data2, Boletim boletim1, Boletim boletim2) {
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

	public Calendar getData2() {
		return data2;
	}

	public void setData2(Calendar data2) {
		this.data2 = data2;
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
		if (super.estado(0) && volta2 == false) {
			b = boletim1;
		} else
			b = boletim2;
		return b;
	}

	@Override
	public void iniciar() {
		if (super.estado(-1)) {
			super.setEstado(0);
			super.setPermitirVotar(true);
			this.boletim1 = gerarBoletim();
		} else {
			super.setEstado(0);
			super.setPermitirVotar(true);
			this.boletim2 = gerarBoletim();
		}
	}

	@Override
	public void terminar() {
		if(super.estado(-1))
	}

	private Boletim geraBoletim() {
		Boletim b = new Boletim();
		int nListas = this.listas.size() - 1;
		int rand;
		ArrayList<ListaPR> list = (ArrayList<ListaPR>) this.listas.values();
		while (nListas > 0) {
			Random r = new Random();
			rand = r.nextInt(nListas);
			ListaPR lpr = list.get(rand);
			lpr.setOrdem1(rand);
			b.
			nListas = nListas - 1;

		}
		return null;
	}

}
