package Business;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.sun.org.apache.bcel.internal.generic.NEW;

import Data.CirculoDAO;
import Data.PartidosDAO;
import Exception.ExceptionColigacaoExiste;
import Exception.ExceptionColigacaoNaoExiste;
import Exception.ExceptionEleicaoAtiva;
import Exception.ExceptionEleicaoEstado;
import Exception.ExceptionIniciarEleicao;
import Exception.ExceptionLimiteCandidatos;
import Exception.ExceptionListaExiste;
import Exception.ExceptionMandanteInvalido;
import Exception.ExceptionPartidoExiste;
import Exception.ExceptionPartidoNaoExiste;
import Exception.ExceptionTerminarEleicao;
import Data.ColigacaoDAO;
import Data.Connector;
import Data.EleicaoARDAO;
import Data.EleicaoPRDAO;
import Data.EleitoresDAO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SGE {

	private static final int CRIADA = -1;
	private static final int ATIVA = 0;
	private static final int TERMINADA = 1;

	/**
	 * ao criaar eleição passar no construtor todos os circulos
	 */
	private static Admin admin = new Admin(0, "0");
	private Eleitor eleitor;
	private PartidosDAO partidos;
	private CirculoDAO circulos;
	private ColigacaoDAO coligacoes;
	private EleitoresDAO eleitores;
	private EleicaoPRDAO eleicoesPR;
	private EleicaoARDAO eleicoesAR;
	private int ativa;

	public SGE() {
		try {
			this.eleitor = null;
			this.partidos = new PartidosDAO();
			this.circulos = new CirculoDAO();
			this.coligacoes = new ColigacaoDAO();
			this.eleitores = new EleitoresDAO();
			this.eleicoesPR = new EleicaoPRDAO();
			this.eleicoesAR = new EleicaoARDAO();
			this.ativa = procuraEleicaoAtiva();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Admin getAdmin() {
		return admin;
	}

	public static void setAdmin(Admin admin) {
		SGE.admin = admin;
	}

	public Eleitor getEleitor() {
		return eleitor;
	}

	public void setEleitor(Eleitor eleitor) {
		this.eleitor = eleitor;
	}

	private int procuraEleicaoAtiva() {
		int idEleicaoAtiva = -1;
		Iterator<EleicaoPR> itPR = this.eleicoesPR.values().iterator();
		while (itPR.hasNext() && idEleicaoAtiva == -1) {
			EleicaoPR pr = itPR.next();
			if (itPR.next().estado(ATIVA)) {
				idEleicaoAtiva = pr.getIdEleicao();
			}
		}
		Iterator<EleicaoAR> itAR = this.eleicoesAR.values().iterator();
		while (itAR.hasNext() && idEleicaoAtiva == -1) {
			EleicaoAR ar = itAR.next();
			if (itAR.next().estado(ATIVA)) {
				idEleicaoAtiva = ar.getIdEleicao();
			}
		}
		return idEleicaoAtiva;
	}

	public Map<Integer, List<Eleitor>> lerCadernoRecenciamento(String path) {

		Map<Integer, List<Eleitor>> listaEleitores = new HashMap<>();
		for (int i = 1; i <= 22; i++) {
			listaEleitores.put(i, new ArrayList<Eleitor>());
		}
		String flin = "sep=";
		String line = "";
		String csvSplit = ",";
		BufferedReader br = null;

		try {

			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {
				if (line.contains(flin)) {
					csvSplit = line.split("=")[1];
					System.out.println(csvSplit);
				} else {
					String[] eleitores = line.split(csvSplit);
					int nCiruclo = Integer.parseInt(eleitores[1]);
					int nIdent = Integer.parseInt(eleitores[0]);
					Eleitor e = new Eleitor(eleitores[2], nCiruclo, nIdent, eleitores[3]);
					listaEleitores.get(nCiruclo).add(e);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return listaEleitores;
	}

	public void confirmarCadernoRecenciamento(Map<Integer, List<Eleitor>> listaEleitores) {
		for (Integer circulo : listaEleitores.keySet()) {
			for (Eleitor el : listaEleitores.get(circulo)) {
				this.eleitores.put(el.getnIdent(), el);
			}
		}
		this.atualizarCirculos(listaEleitores);
	}

	/**
	 * Atualizar totEleitores que esta nos ciruclos das eleiçoes
	 */
	public void atualizarCirculos(Map<Integer, List<Eleitor>> listaEleitores) {
		for (Circulo circulo : this.circulos.values()) {
			circulo.setTotEleitores(listaEleitores.get(circulo.getId()).size());
			this.circulos.put(circulo.getId(), circulo);
		}
		for (EleicaoPR pr : this.eleicoesPR.values()) {
			if (pr.estado(CRIADA)) {
				pr.atualizarCirculos();
				this.eleicoesPR.put(pr.getIdEleicao(), pr);
			}
		}
		for (EleicaoAR ar : this.eleicoesAR.values()) {
			if (ar.estado(CRIADA)) {
				ar.atualizarCirculos();
				this.eleicoesAR.put(ar.getIdEleicao(), ar);
			}
		}
	}

	public void iniciarEleicao(Eleicao e) throws ExceptionEleicaoAtiva, ExceptionIniciarEleicao {
		Eleicao el;
		if (this.ativa != -1) {
			throw new ExceptionEleicaoAtiva("Já existe uma eleição ativa");
		} else {
			if (getClass().getSimpleName() == "EleicaoPR") {
				el = this.eleicoesPR.get(e.getIdEleicao());
				el.iniciar();
				this.eleicoesPR.put(el.getIdEleicao(), (EleicaoPR) el);
			} else {
				el = this.eleicoesAR.get(e.getIdEleicao());
				el.iniciar();
				this.eleicoesAR.put(el.getIdEleicao(), (EleicaoAR) el);
			}
		}
	}

	public void terminarEleicao(Eleicao e) throws ExceptionEleicaoAtiva, ExceptionTerminarEleicao {
		Eleicao el;
		if (this.ativa == -1) {
			throw new ExceptionEleicaoAtiva("Não existe eleição ativa");
		} else {
			if (getClass().getSimpleName() == "EleicaoPR") {
				el = this.eleicoesPR.get(e.getIdEleicao());
				el.terminar();
				this.eleicoesPR.put(el.getIdEleicao(), (EleicaoPR) el);
			} else {
				el = this.eleicoesAR.get(e.getIdEleicao());
				el.terminar();
				this.eleicoesAR.put(el.getIdEleicao(), (EleicaoAR) el);
			}
		}
	}

	public EleicaoPR criarEleicaoPR(EleicaoPR eleicao) {
		eleicao.setIdEleicao(this.chaveEleicao());
		this.eleicoesPR.put(eleicao.getIdEleicao(), eleicao);
		return eleicao;
	}

	public EleicaoAR criarEleicaoAR(EleicaoAR eleicao) {
		eleicao.setIdEleicao(this.chaveEleicao());
		this.eleicoesAR.put(eleicao.getIdEleicao(), eleicao);
		return eleicao;
	}

	public Eleicao eleicaoAtiva() {
		Eleicao ativa;
		if ((ativa = this.eleicoesAR.get(this.ativa)) == null) {
			ativa = this.eleicoesPR.get(this.ativa);
		}
		return ativa;
	}

	public Set<Eleicao> getEleicoesCriadas() {
		Set<Eleicao> eCriadas = new TreeSet<>(new ComparatorEleicaoData());
		for (Eleicao el : this.eleicoesPR.values()) {
			if (el.estado(CRIADA)) {
				eCriadas.add(el);
			}
		}
		for (Eleicao el : this.eleicoesAR.values()) {
			if (el.estado(CRIADA)) {
				eCriadas.add(el);
			}
		}
		return eCriadas;
	}

	public Set<Eleicao> getEleicoesTerminadas() {
		Set<Eleicao> eCriadas = new TreeSet<>(new ComparatorEleicaoData());
		for (Eleicao el : this.eleicoesPR.values()) {
			if (el.estado(TERMINADA)) {
				eCriadas.add(el);
			}
		}
		for (Eleicao el : this.eleicoesAR.values()) {
			if (el.estado(TERMINADA)) {
				eCriadas.add(el);
			}
		}
		return eCriadas;
	}

	public boolean login(int id, String pin) {
		boolean log = false;
		if (admin.verificarPin(pin)) {
			log = true;
		} else {
			if (this.eleitores.get(id).autenticar(id, pin)) {
				this.eleitor = this.eleitores.get(id);
				log = true;
			}
		}
		return log;
	}

	public void addPartido(Partido part) throws ExceptionPartidoExiste {
		Iterator<Partido> itPart = this.partidos.values().iterator();
		while (itPart.hasNext()) {
			if (!itPart.next().equals(part)) {
				throw new ExceptionPartidoExiste("O partido já se encontram registado");
			}
		}
		part.setId(this.chavePartido());
		this.partidos.put(part.getId(), part);
	}

	public void eliminarPartido(Partido p) throws ExceptionPartidoNaoExiste {
		if (this.partidos.remove(p.getId()) == null)
			throw new ExceptionPartidoNaoExiste("O partido nao se encontra registado");
	}

	public Coligacao addColigacao(Coligacao col) throws ExceptionColigacaoExiste {
		Iterator<Coligacao> itCol = this.coligacoes.values().iterator();
		while (itCol.hasNext()) {
			if (!itCol.next().equals(col)) {
				throw new ExceptionColigacaoExiste("O partido já se encontram registado");
			}
		}
		col.setId(this.chaveColigacoes());
		this.coligacoes.put(col.getId(), col);
		return col;
	}

	public void eliminarColigacao(Coligacao col) throws ExceptionColigacaoNaoExiste {
		if (this.coligacoes.remove(col.getId()) == null)
			throw new ExceptionColigacaoNaoExiste("A coligação não se encontra registada");
	}

	public void addLista(Eleicao el, Listavel lista)
			throws ExceptionListaExiste, ExceptionLimiteCandidatos, ExceptionMandanteInvalido, ExceptionEleicaoEstado {
		el.addLista(lista);
	}

	public void removeLista(Eleicao e, Listavel lista) {
		e.removeLista(lista);
	}

	public Boletim getBoletim(Eleicao e, Eleitor eleitor) {
		return e.getBoletim(eleitor.getCirculo());
	}

	public void addVoto(Eleicao e, Listavel lista, Eleitor eleitor) {
		e.addVoto(lista, eleitor);
		if (e.getClass().getSimpleName().equals("EleicaoAR")) {
			EleicaoAR eleicaoAR = (EleicaoAR) e;
			this.eleicoesAR.put(eleicaoAR.getIdEleicao(), eleicaoAR);
		} else if (e.getClass().getSimpleName().equals("EleicaoPR")) {
			EleicaoPR eleicaoPR = (EleicaoPR) e;
			this.eleicoesPR.put(eleicaoPR.getIdEleicao(), eleicaoPR);
		}
	}

	public void addVotoNulo(Eleicao e, Eleitor eleitor) {
		e.addVotoNulo(eleitor);
		if (e.getClass().getSimpleName().equals("EleicaoAR")) {
			EleicaoAR eleicaoAR = (EleicaoAR) e;
			this.eleicoesAR.put(eleicaoAR.getIdEleicao(), eleicaoAR);
		} else if (e.getClass().getSimpleName().equals("EleicaoPR")) {
			EleicaoPR eleicaoPR = (EleicaoPR) e;
			this.eleicoesPR.put(eleicaoPR.getIdEleicao(), eleicaoPR);
		}
	}

	public void addVotoBranco(Eleicao e, Eleitor eleitor) {
		e.addVotoBranco(eleitor);
		if (e.getClass().getSimpleName().equals("EleicaoAR")) {
			EleicaoAR eleicaoAR = (EleicaoAR) e;
			this.eleicoesAR.put(eleicaoAR.getIdEleicao(), eleicaoAR);
		} else if (e.getClass().getSimpleName().equals("EleicaoPR")) {
			EleicaoPR eleicaoPR = (EleicaoPR) e;
			this.eleicoesPR.put(eleicaoPR.getIdEleicao(), eleicaoPR);
		}
	}

	private int chavePartido() {
		int max = 0;
		if (this.partidos.size() > 0) {
			max = Collections.max(this.partidos.keySet());
		}
		return max + 1;
	}

	private int chaveEleicao() {
		int maxPR = 0;
		int maxAR = 0;
		if (this.eleicoesAR.size() > 0) {
			maxAR = Collections.max(this.eleicoesAR.keySet());
		}
		if (this.eleicoesPR.size() > 0) {
			maxAR = Collections.max(this.eleicoesPR.keySet());
		}
		return Math.max(maxPR, maxAR) + 1;
	}

	private int chaveColigacoes() {
		int max = 0;
		if (this.coligacoes.size() > 0) {
			max = Collections.max(this.coligacoes.keySet());
		}
		return max + 1;
	}

	public void addCandidatoPR(EleicaoPR eleicao, Candidato cand) throws ExceptionListaExiste, ExceptionLimiteCandidatos, ExceptionMandanteInvalido, ExceptionEleicaoEstado{
		EleicaoPR pr = this.eleicoesPR.get(eleicao.getIdEleicao());
		ListaPR lista = new ListaPR(0,0, cand);
		pr.addLista(lista);
		this.eleicoesPR.put(pr.getIdEleicao(), pr);
	}
}
