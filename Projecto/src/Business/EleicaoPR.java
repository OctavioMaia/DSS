package Business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import Data.ListaPRDAO;
import Data.ResultadoCirculoPRDAO;
import Exception.ExceptionCandidatoExiste;
import Exception.ExceptionListaExiste;
import Exception.ExceptionListaNaoExiste;

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

	public EleicaoPR(int idEleicao, Calendar data, List<Circulo> c) {
		super(idEleicao, data);
		this.volta2 = false;
		this.data2 = null;
		this.voltaR1 = initResultadoCirculoPRDAO(idEleicao, 1, c);
		this.voltaR2 = initResultadoCirculoPRDAO(idEleicao, 2, c);
		this.boletim1 = null;
		this.boletim2 = null;
		this.listas = new ListaPRDAO(idEleicao);
		this.votantes2 = new HashSet<>();
	}

	public EleicaoPR(int idEleicao, Calendar data, int estado, boolean permitirVotar, Set<Integer> vot, List<Circulo> c,
			Set<Integer> vot2, boolean volta2, Calendar data2, Boletim boletim1, Boletim boletim2) {
		super(idEleicao, data, estado, permitirVotar, vot);
		this.volta2 = volta2;
		this.data2 = data2;
		this.voltaR1 = new ResultadoCirculoPRDAO(idEleicao, 1);
		this.voltaR2 = new ResultadoCirculoPRDAO(idEleicao, 2);
		this.boletim1 = boletim1;
		this.boletim2 = boletim2;
		this.listas = new ListaPRDAO(idEleicao);
		this.votantes2 = vot2;
	}

	private ResultadoCirculoPRDAO initResultadoCirculoPRDAO(int idEleicao, int volta, List<Circulo> c) {
		ResultadoCirculoPRDAO resDAO = new ResultadoCirculoPRDAO(idEleicao, volta);
		Iterator<Circulo> itC = c.iterator();
		while (itC.hasNext()) {
			Circulo c1 = itC.next();
			resDAO.put(c1.getId(), new ResultadoCirculoPR(c1));
		}
		return resDAO;
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

	/**
	 * Metodo addLista que adiciona a listaPR com o candidato c á base de dados.
	 * Apenas adiciona as listas e nao aos REsultadosCirculoPR
	 * 
	 * @param c
	 * @throws ExceptionCandidatoExiste
	 */
	public void addLista(Candidato c) throws ExceptionCandidatoExiste {
		ListaPR lista;
		Iterator<ListaPR> itListaPR = this.listas.values().iterator();
		while (itListaPR.hasNext()) {
			lista = (ListaPR) itListaPR.next();
			if (lista.candidatoEquals(c)) {
				throw new ExceptionCandidatoExiste(
						"A lista que deseja adicionar tem um candidato que já se encontra a concorer para a mesma eleição");
			}
		}
		lista = new ListaPR(super.getIdEleicao(), this.listas.size() + 1, c);
		this.listas.put(lista.getIdListaPR(), lista);
	}

	@Override
	public void removeLista(Listavel lista) {
		ListaPR listpr = (ListaPR) lista;
		this.listas.remove(listpr.getIdListaPR());
	}

	@Override
	public void addVoto(Listavel lista) {
		ResultadoCirculoPR resPR;
		ListaPR listpr = (ListaPR) lista;
		if (volta2 == false) {
			resPR = this.voltaR1.get(listpr.getIdListaPR());
			resPR.addVoto(listpr);
			this.voltaR1.put(resPR.getCirculo().getId(), resPR);
		} else {
			resPR = this.voltaR2.get(listpr.getIdListaPR());
			resPR.addVoto(listpr);
			this.voltaR2.put(resPR.getCirculo().getId(), resPR);
		}
	}

	@Override
	public void addVotoNulo(int idCirculo) {
		ResultadoCirculoPR resPR;
		if (volta2 == false) {
			resPR = this.voltaR1.get(idCirculo);
			resPR.addVotoNulo();
			this.voltaR1.put(idCirculo, resPR);
		} else {
			resPR = this.voltaR2.get(idCirculo);
			resPR.addVotoNulo();
			this.voltaR2.put(idCirculo, resPR);
		}
	}

	@Override
	public void addVotoBranco(int idCirculo) {
		ResultadoCirculoPR resPR;
		if (volta2 == false) {
			resPR = this.voltaR1.get(idCirculo);
			resPR.addVotoBranco();
			this.voltaR1.put(idCirculo, resPR);
		} else {
			resPR = this.voltaR2.get(idCirculo);
			resPR.addVotoBranco();
			this.voltaR2.put(idCirculo, resPR);
		}
	}

	@Override
	public Boletim getBoletim(int idCirculo) {
		Boletim b = null;
		if (boletim1 != null) {
			this.criarBoletim();
		}
		if (super.estado(0) && volta2 == false) {
			b = boletim1;
		} else {
			b = boletim2;
		}
		return b;
	}

	private Boletim criarBoletim() {
		Boletim b = new Boletim(this.listas.size());
		Iterator<ListaPR> it = this.listas.values().iterator();
		while (it.hasNext()) {
			b.addLista(it.next());
		}
		return b;
	}

	private Boletim geraBoletim(Collection<ListaPR> collection) {
		Random r = new Random();
		int nListas = collection.size();
		Boletim b = new Boletim(nListas);
		int rand;
		ListaPR listaPR;
		Iterator<ListaPR> list = collection.iterator();
		while (list.hasNext()) {
			listaPR = (ListaPR) list.next();
			rand = r.nextInt(nListas-1);
			if(!this.volta2){
				listaPR.setOrdem1(rand);
			}else{
				listaPR.setOrdem2(rand);
			}
			this.listas.put(listaPR.getIdEleicao(), listaPR);
			b.addLista(listaPR);
			nListas--;
		}
		return b;
	}

	
	/**
	 * Falta para quando inicio a eleiçao colocar as listas nos resultados
	 */
	@Override
	public void iniciar() {
		if (super.estado(-1)) {
			super.setEstado(0);
			super.setPermitirVotar(true);
			this.boletim1 = geraBoletim(this.listas.values());
		} else {
			super.setEstado(0);
			super.setPermitirVotar(true);
		}
	}
	
	private List<ListaPR> listasSegundaVolta(){
		List<ListaPR> listas = new ArrayList<ListaPR>();
		return listas;
	}

	@Override
	public void terminar() {
		if(super.estado(-1))
	}

	private void defData2() {

	}

}
