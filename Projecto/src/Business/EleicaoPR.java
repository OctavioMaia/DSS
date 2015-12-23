package Business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import Comparator.ComparatorListavelVotos;
import Data.EleicaoPRDAO;
import Data.ListaPRDAO;
import Data.ResultadoCirculoPRDAO;
import Exception.ExceptionEleicaoEstado;
import Exception.ExceptionIniciarEleicao;
import Exception.ExceptionLimiteCandidatos;
import Exception.ExceptionListaExiste;
import Exception.ExceptionMandanteInvalido;
import Exception.ExceptionTerminarEleicao;

/**
 * Passar circulos tambem nos construtores
 */

public class EleicaoPR extends Eleicao {
	private boolean volta2;
	private Calendar data2;
	private ResultadoCirculoPRDAO voltaR1;
	private ResultadoCirculoPRDAO voltaR2;
	private ListaPRDAO listas;
	private Set<Integer> votantes2;

	public EleicaoPR(int idEleicao, Calendar data, Collection<Circulo> c) {
		super(idEleicao, data);
		this.volta2 = false;
		this.data2 = defData2(data);
		this.listas = new ListaPRDAO(idEleicao);
		this.votantes2 = new HashSet<>();
		
		EleicaoPRDAO dao = new EleicaoPRDAO();
		dao.put(idEleicao, this);
		this.voltaR1 = initResultadoCirculoPRDAO(idEleicao, 1, c);
		this.voltaR2 = initResultadoCirculoPRDAO(idEleicao, 2, c);
	}

	public EleicaoPR(int idEleicao, Calendar data, int estado, boolean permitirVotar, Set<Integer> vot,
			Collection<Circulo> c, Set<Integer> vot2, boolean volta2, Calendar data2) {
		super(idEleicao, data, estado, permitirVotar, vot);
		this.volta2 = volta2;
		this.data2 = data2;
		this.voltaR1 = initResultadoCirculoPRDAO(idEleicao, 1, c);
		this.voltaR2 = initResultadoCirculoPRDAO(idEleicao, 2, c);
		this.listas = new ListaPRDAO(idEleicao);
		this.votantes2 = vot2;
	}

	private ResultadoCirculoPRDAO initResultadoCirculoPRDAO(int idEleicao, int volta, Collection<Circulo> circulos) {
		ResultadoCirculoPRDAO resDAO = new ResultadoCirculoPRDAO(idEleicao, volta);
		for (Circulo c : circulos) {
			if (!resDAO.containsKey(c.getId())) {
				resDAO.put(c.getId(), new ResultadoCirculoPR(c));
			}
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

	@Override
	public void addVoto(Listavel lista, Eleitor eleitor) {
		ResultadoCirculoPR resPR;
		ListaPR listpr = (ListaPR) lista;
		if (volta2 == false) {
			resPR = this.voltaR1.get(eleitor.getCirculo());
			resPR.addVoto(listpr);
			super.addVotante(eleitor);
			this.voltaR1.put(resPR.getCirculo().getId(), resPR);
		} else {
			resPR = this.voltaR2.get(eleitor.getCirculo());
			resPR.addVoto(listpr);
			this.addVotante(eleitor);
			this.voltaR2.put(resPR.getCirculo().getId(), resPR);
		}
	}

	public void addVotante(Eleitor eleitor) {
		this.votantes2.add(eleitor.getnIdent());
	};

	@Override
	public void addVotoNulo(Eleitor eleitor) {
		ResultadoCirculoPR resPR;
		if (volta2 == false) {
			resPR = this.voltaR1.get(eleitor.getCirculo());
			resPR.addVotoNulo();
			this.voltaR1.put(eleitor.getCirculo(), resPR);
		} else {
			resPR = this.voltaR2.get(eleitor.getCirculo());
			resPR.addVotoNulo();
			this.voltaR2.put(eleitor.getCirculo(), resPR);
		}
	}

	@Override
	public void addVotoBranco(Eleitor eleitor) {
		ResultadoCirculoPR resPR;
		if (volta2 == false) {
			resPR = this.voltaR1.get(eleitor.getCirculo());
			resPR.addVotoBranco();
			this.voltaR1.put(eleitor.getCirculo(), resPR);
		} else {
			resPR = this.voltaR2.get(eleitor.getCirculo());
			resPR.addVotoBranco();
			this.voltaR2.put(eleitor.getCirculo(), resPR);
		}
	}

	/**
	 * Metodo que vai criar um objecto boletim com todas as listas que vao
	 * participar na eleiçao
	 */
	public Boletim getBoletim() {
		Boletim b = null;
		if (this.estado(0)) {
			if (!volta2) {
				b = new Boletim(this.listas.size());
				for (ListaPR l : this.listas.values()) {
					b.addLista(l);
				}
			} else {
				b = new Boletim(2);
				for (ListaPR l : this.listas.values()) {
					if (l.ordem2() != -1) {
						b.addLista(l);
					}
				}
			}
		}
		return b;
	}

	/**
	 * Metodo que vai gerar os numeros de ordem para as listas
	 * 
	 * @param collection
	 * @return
	 */
	private void geraBoletim(Collection<ListaPR> listas) {
		ArrayList<Integer> nums = new ArrayList<>();
		Random r = new Random();
		int nListas = listas.size();
		int rand;
		for (ListaPR listaPR : listas) {
			rand = r.nextInt(nListas - 1);
			while (nums.contains(rand)) {
				rand = r.nextInt(nListas - 1);
			}
			if (!this.volta2) {
				listaPR.setOrdem1(rand);
			} else {
				listaPR.setOrdem2(rand);
			}
			nums.add(rand);
			this.listas.put(listaPR.getIdListaPR(), listaPR);
		}
	}

	/**
	 * ter em atenção a esta função possivel local de erro
	 * 
	 * @return
	 */
	public Set<ListavelVotos> resultadosVolta(int volta) {
		HashMap<ListaPR, Integer> validos;
		HashMap<ListaPR, Integer> segVolta = new HashMap<>();
		Collection<ResultadoCirculoPR> reslt;
		if(volta==1){
			reslt = this.voltaR1.values();
		}else{
			reslt = this.voltaR2.values();
		}
		for (ResultadoCirculoPR resC : reslt) {
			validos = resC.getValidos();
			for (ListaPR lista : validos.keySet()) {
				segVolta.put(lista, segVolta.get(lista) + validos.get(lista));
				// linha a rever
			}
		}
		// ordenar as listas pelo numero de votos
		TreeSet<ListavelVotos> listasSeg = new TreeSet<>(new ComparatorListavelVotos());
		for (ListaPR lista : segVolta.keySet()) {
			ListavelVotos lv = new ListavelVotos(lista, segVolta.get(lista));
			listasSeg.add(lv);
		}
		return listasSeg;
	}

	/*
	 * preciso depois verificar se a ordem do comaprador está correta para isto
	 * dar as duas mais votadas
	 */
	private Set<ListaPR> disputaSegundaVolta(Set<ListavelVotos> listas) {
		int n = 0;
		Set<ListaPR> listasSeg = new HashSet<ListaPR>();
		Iterator<ListavelVotos> itListas = listas.iterator();
		while (itListas.hasNext() && n < 2) {
			listasSeg.add((ListaPR) itListas.next().getLista());
		}
		return listasSeg;
	}

	/**
	 * Falta para quando inicio a eleiçao colocar as listas nos resultados
	 * preciso de colocar nos resultados volta1 as listas se for para a volta 2
	 * colocar nos resultados as duas listas anteriores gerarBoletins
	 * 
	 * @throws ExceptionTerminarEleicao
	 */
	@Override
	public void iniciar() throws ExceptionIniciarEleicao {
		if (super.estado(-1)) {// iniciar depois da eleicao ter sido criada
			Collection<ListaPR> list = this.listas.values();
			this.geraBoletim(list);
			for (ResultadoCirculoPR resC : this.voltaR1.values()) {
				resC.addListas(list);
				this.voltaR1.put(resC.getCirculo().getId(), resC);
			}
		} else {
			if (super.estado(0) && this.volta2) { // iniciar segunda volta
				Collection<ListaPR> list = this.disputaSegundaVolta(this.resultadosVolta(1));
				if (list.size() == 2) {
					this.geraBoletim(list);
					for (ResultadoCirculoPR resC : this.voltaR2.values()) {
						resC.addListas(list);
						this.voltaR2.put(resC.getCirculo().getId(), resC);
					}
				}
			} else {
				throw new ExceptionIniciarEleicao("Inpossivel iniciar Elicão;");
			}
		}
	}

	@Override
	public void terminar() throws ExceptionTerminarEleicao {
		if (super.estado(0) && this.volta2 == false) {
			// terminar primeira volta
			super.setPermitirVotar(false);
			// verificar se ouve maioria absoluta
			if (verifacarMaioria() == false) {
				// criar segunda volta
				this.volta2 = true;
			} else {
				super.setEstado(1);
			}
		} else {
			// terminar segunda volta
			if (volta2) {
				super.setPermitirVotar(false);
				super.setEstado(1);
			}
		}
	}

	private Calendar defData2(Calendar data1) {
		Calendar data2aux = new GregorianCalendar();
		data2aux.set(data1.get(Calendar.YEAR), data1.get(Calendar.MONTH), data1.get(Calendar.DAY_OF_YEAR));
		;
		data2aux.add(Calendar.DAY_OF_YEAR, 30);
		while (data2aux.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			data2aux.add(Calendar.DAY_OF_YEAR, 1);
		}
		return data2aux;
	}

	@Override
	public void addLista(Listavel lista)
			throws ExceptionListaExiste, ExceptionLimiteCandidatos, ExceptionMandanteInvalido, ExceptionEleicaoEstado {
		if (super.estado(-1)) {
			ListaPR listaPR = (ListaPR) lista;
			for (ListaPR l : this.listas.values()) {
				if (l.candidatoEquals(listaPR.getCandidato())) {
					throw new ExceptionListaExiste(
							"A lista que deseja adicionar tem um candidato que já se encontra a concorrer para a mesma eleição");
				}
			}
			listaPR.setIdEleicao(super.getIdEleicao());
			listaPR.setIdListaPR(chaveListaPR());
			this.listas.put(listaPR.getIdListaPR(), listaPR);
		} else {
			throw new ExceptionEleicaoEstado("A eleicão não se encontra disponivel para ser gerida.");
		}
	}

	@Override
	public void removeLista(Listavel lista) {
		ListaPR listpr = (ListaPR) lista;
		this.listas.remove(listpr.getIdListaPR());
	}

	public int numeroEleitores() {
		int tot = 0;
		for (ResultadoCirculoPR resC : this.voltaR1.values()) {
			tot += resC.getTotEleitores();
		}
		return tot;
	}

	private boolean verifacarMaioria() {
		boolean ver = false;
		Iterator<ListavelVotos> itLV = this.resultadosVolta(1).iterator();
		if (itLV.hasNext() && itLV.next().getVotos() > (super.numeroVotos() / 2)) {
			ver = true;
		}
		return ver;
	}

	public void atualizarCirculos() {
		for (Integer circulo : this.voltaR1.keySet()) {
			ResultadoCirculoPR resCirc = this.voltaR1.get(circulo);
			resCirc.atualizarTotEleitores();
			this.voltaR1.put(circulo, resCirc);
		}
		for (int circulo : this.voltaR2.keySet()) {
			ResultadoCirculoPR resCirc = this.voltaR2.get(circulo);
			resCirc.atualizarTotEleitores();
			this.voltaR2.put(circulo, resCirc);
		}
	}

	private int chaveListaPR() {
		int max = 0;
		if (this.listas.size() > 0) {
			max = Collections.max(this.listas.keySet());
		}
		return max + 1;
	}

	protected ResultadoCirculoPR getResultadoCirculo(int volta, int idCirculo) {
		ResultadoCirculoPR res = null;
		if (volta == 1) {
			res = this.voltaR1.get(idCirculo);
		} else {
			if (volta2) {
				res = this.voltaR2.get(idCirculo);
			}
		}
		return res;
	}

	protected ResultadoGlobalPR getResultadoGlobal(int volta) {
		ResultadoGlobalPR res = null;
		;
		if (volta == 1) {
			res = new ResultadoGlobalPR(numeroEleitores(), getVotosBrancos(volta), getVotosNulos(volta),
					resultadosVolta(1));
		} else {
			if (volta2) {
				res = new ResultadoGlobalPR(numeroEleitores(), getVotosBrancos(volta), getVotosNulos(volta),
						resultadosVolta(2));
			}
		}
		return res;
	}

	private int getVotosBrancos(int volta) {
		int brancos = 0;
		Collection<ResultadoCirculoPR> c;
		if (volta == 1) {
			c = this.voltaR1.values();
		} else {
			c = this.voltaR2.values();
		}
		for (ResultadoCirculoPR resC : c) {
			brancos += resC.getNulos();
		}
		return brancos;
	}

	private int getVotosNulos(int volta) {
		int nulos = 0;
		Collection<ResultadoCirculoPR> c;
		if (volta == 1) {
			c = this.voltaR1.values();
		} else {
			c = this.voltaR2.values();
		}
		for (ResultadoCirculoPR resC : c) {
			nulos += resC.getNulos();
		}
		return nulos;
	}
}
