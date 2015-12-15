package Business;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import Data.CirculoDAO;
import Data.PartidosDAO;
import Data.ColigacaoDAO;
import Data.Connector;
import Data.EleicaoARDAO;
import Data.EleicaoPRDAO;
import Data.EleitoresDAO;
import java.util.ArrayList;
import java.util.Date;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SGE {

	private static final int CRIADA = -1;
	private static final int ATIVA = 0;
	private static final int TERMINADA = 1;

	private Connector cn;
	private PartidosDAO partidos;
	private CirculoDAO circulos;
	private ColigacaoDAO coligacoes;
	private EleitoresDAO eleitores;
	private EleicaoPRDAO eleicoesPR;
	private EleicaoARDAO eleicoesAR;
	private int ativa;

	public SGE() {
		try {
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

	private int procuraEleicaoAtiva() {
		int idEleicaoAtiva = -1;
		Iterator<EleicaoPR> itPR = this.eleicoesPR.valeus();
		while (itPR.hasNext() && idEleicaoAtiva == -1) {
			EleicaoPR pr = itPR.next();
			if (itPR.next().isAtiva()) {
				idEleicaoAtiva = pr.getIdEleicao();
			}
		}
		Iterator<EleicaoAR> itAR = this.eleicoesAR.valeus();
		while (itAR.hasNext() && idEleicaoAtiva == -1) {
			EleicaoAR ar = itAR.next();
			if (itAR.next().isAtiva()) {
				idEleicaoAtiva = ar.getIdEleicao();
			}
		}
		return idEleicaoAtiva;
	}

	public void inserirCadernoRecenciamento() {

		ArrayList<Eleitor> listaEleitores = new ArrayList<Eleitor>();

		String ficheiroCSV = "Inserir directoria.csv";
		String line = "";
		String cvsSplit = ",";
		BufferedReader br = null;

		try {

			br = new BufferedReader(new FileReader(ficheiroCSV));
			while ((line = br.readLine()) != null) {
				String[] eleitores = line.split(cvsSplit);
				int a = Integer.parseInt(eleitores[1]);
				int b = Integer.parseInt(eleitores[0]);
				Eleitor e = new Eleitor(eleitores[2], a, b, eleitores[3]);
				listaEleitores.add(e);
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

		for (Eleitor e : listaEleitores) {
			eleitores.put(e.getnIdent(), e);
		}

	}

	public void iniciarEleicao(Eleicao e) {
		if (this.ativa == -1) {
			throw new ExceptionEleicaoAtiva("Já existe uma eleição ativa");
		} else {
			e.iniciar();
			if (getClass().getName() == "EleicaoPR") {
				this.eleicoesPR.put(e.getIdEleicao(), (EleicaoPR) e);
			} else {
				this.eleicoesAR.put(e.getIdEleicao(), (EleicaoAR) e);
			}
		}

	}

	public void terminarEleicao(Eleicao e) {
		if (this.ativa == -1) {
			throw new ExceptionEleicaoAtiva("Não existe eleição ativa");
		} else {
			e.iniciar();
			if (getClass().getName() == "EleicaoPR") {
				this.eleicoesPR.put(e.getIdEleicao(), (EleicaoPR) e);
			} else {
				this.eleicoesAR.put(e.getIdEleicao(), (EleicaoAR) e);
			}
		}
	}

	public Map<Integer, ResultadoCirculoPR> verResultadosPR(EleicaoPR e) {
		if (this.ativa == e.getIdEleicao()) {
			throw new ExceptionEleicaoAtiva("Eleição está ativa");
		}
		return e.verResultados();
	}

	public Map<Integer, ResultadoCirculoAR> verResultadoAR(EleicaoPR e) {
		if (this.ativa == e.getIdEleicao()) {
			throw new ExceptionEleicaoAtiva("Eleição está ativa");
		}
		return e.verResultados();
	}

	public EleicaoPR criarEleicaoPR(Date data) {
		EleicaoPR epr = new EleicaoPR(this.eleicoesPR.size() + 1, data);
		this.eleicoesPR.put(epr.getIdEleicao(), epr);
		return epr;
	}

	public EleicaoAR criarEleicaoAR(Date data) {
		EleicaoAR ear = new EleicaoAR(this.eleicoesAR.size() + 1, data);
		this.eleicoesAR.put(ear.getEleicaoAR(), ear);
		return ear;
	}

	public void addListaPR(EleicaoPR e) {
		e.addLista();
		this.eleicoesPR.put(e.getIdEleicao(), e);
	}

	public void addListaAR(EleicaoAR e) {
		e.addLista();
		this.eleicoesAR.put(e.getIdEleicao(), e);
	}

	public Eleicao eleicaoAtiva() {
		Eleicao ativa;
		if ((ativa = this.eleicoesAR.get(this.ativa)) == null) {
			ativa = this.eleicoesPR.get(this.ativa);
		}
		return ativa;
	}

	@SuppressWarnings("unchecked")
	public Set<Eleicao> getEleicoesCriadas() {
		Set<Eleicao> eCriadas = new TreeSet<>(new ComparatorEleicaoData());
		Iterator<Eleicao> it = (Iterator<Eleicao>) this.eleicoesPR.values();
		while (it.hasNext()) {
			Eleicao e = it.next();
			if (e.estado(CRIADA)) {
				eCriadas.add(e);
			}
		}
		it = (Iterator<Eleicao>) this.eleicoesAR.values();
		while (it.hasNext()) {
			Eleicao e = it.next();
			if (e.estado(CRIADA)) {
				eCriadas.add(e);
			}
		}
		return eCriadas;
	}

	public Set<Eleicao> getEleicoesTerminadas() {
		Set<Eleicao> eCriadas = new TreeSet<>(new ComparatorEleicaoData());
		Iterator<Eleicao> it = (Iterator<Eleicao>) this.eleicoesPR.values();
		while (it.hasNext()) {
			Eleicao e = it.next();
			if (e.estado(TERMINADA)) {
				eCriadas.add(e);
			}
		}
		it = (Iterator<Eleicao>) this.eleicoesAR.values();
		while (it.hasNext()) {
			Eleicao e = it.next();
			if (e.estado(TERMINADA)) {
				eCriadas.add(e);
			}
		}
		return eCriadas;
	}

	public void votar(Eleicao e, int idCirculo, Listavel lista) {
		e.votar();
		if (e.getClass().getName() == "EleicaoPR") {
			this.eleicoesPR.put(e.getIdEleicao(), e);
		} else {
			this.eleicoesAR.put(e.getIdEleicao(), e);
		}
	}
	
	public boolean login(int id, String pin){
		return this.eleitores.get(id).autenticar(id,pin);
	}
	
}
