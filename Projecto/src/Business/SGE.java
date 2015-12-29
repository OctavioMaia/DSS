package Business;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import Comparator.ComparatorEleicaoData;
import Comparator.ComparatorListavelVotos;
import Comparator.ComparatorVotavelVotos;
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
import Data.EleicaoARDAO;
import Data.EleicaoPRDAO;
import Data.EleitoresDAO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SGE {

	private static final int CRIADA = -1;
	private static final int ATIVA = 0;
	private static final int TERMINADA = 1;

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
			this.initCirculos();
			this.coligacoes = new ColigacaoDAO();
			this.eleitores = new EleitoresDAO();
			this.eleicoesPR = new EleicaoPRDAO();
			this.eleicoesAR = new EleicaoARDAO();
			this.ativa = procuraEleicaoAtiva();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initCirculos(){
		if(this.circulos.size()==0){
			this.circulos.put(1, new Circulo(1, "Aveiro", 0));
			this.circulos.put(2, new Circulo(2, "Beja", 0));
			this.circulos.put(3, new Circulo(3, "Braga", 0));
			this.circulos.put(4, new Circulo(4, "Braganca", 0));
			this.circulos.put(5, new Circulo(5, "Castelo Branco", 0));
			this.circulos.put(6, new Circulo(6, "Coimbra", 0));
			this.circulos.put(7, new Circulo(7, "Evora", 0));
			this.circulos.put(8, new Circulo(8, "Faro", 0));
			this.circulos.put(9, new Circulo(9, "Guarda", 0));
			this.circulos.put(10, new Circulo(10, "Leiria", 0));
			this.circulos.put(11, new Circulo(11, "Lisboa", 0));
			this.circulos.put(12, new Circulo(12, "Portalegre", 0));
			this.circulos.put(13, new Circulo(13, "Porto", 0));
			this.circulos.put(14, new Circulo(14, "Santarem", 0));
			this.circulos.put(15, new Circulo(15, "Setubal", 0));
			this.circulos.put(16, new Circulo(16, "Viana do Castelo", 0));
			this.circulos.put(17, new Circulo(17, "Vila Real", 0));
			this.circulos.put(18, new Circulo(18, "Viseu", 0));
			this.circulos.put(19, new Circulo(19, "Acores", 0));
			this.circulos.put(20, new Circulo(20, "Madeira", 0));
			this.circulos.put(21, new Circulo(21, "Europa", 0));
			this.circulos.put(22, new Circulo(22, "Fora da Europa", 0));
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
	
	public boolean eleitorVotar(Eleitor eleitor){
		return eleicaoAtiva().eleitorVotar(eleitor);
	}

	private int procuraEleicaoAtiva() {
		int idEleicaoAtiva = -1;
		Iterator<EleicaoPR> itPR = this.eleicoesPR.values().iterator();
		while (itPR.hasNext() && idEleicaoAtiva == -1) {
			EleicaoPR pr = itPR.next();
			if (pr.estado(ATIVA)) {
				idEleicaoAtiva = pr.getIdEleicao();
			}
		}
		Iterator<EleicaoAR> itAR = this.eleicoesAR.values().iterator();
		while (itAR.hasNext() && idEleicaoAtiva == -1) {
			EleicaoAR ar = itAR.next();
			if (ar.estado(ATIVA)) {
				idEleicaoAtiva = ar.getIdEleicao();
			}
		}
		
		System.out.println("id ativa inicial "+idEleicaoAtiva);
		
		return idEleicaoAtiva;
	}
	
	public void alterarDataEleicao(Eleicao eleicao, Calendar dataInicio) {
		eleicao.alterarData(dataInicio);
		
		if(eleicao.getClass().getSimpleName().equals("EleicaoPR")){
			this.eleicoesPR.put(eleicao.getIdEleicao(), (EleicaoPR) eleicao);
		}else{
			this.eleicoesAR.put(eleicao.getIdEleicao(), (EleicaoAR) eleicao);
		}
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
					//System.out.println(csvSplit);
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
		this.eleitores.clear();
		for (Integer circulo : listaEleitores.keySet()) {
			for (Eleitor el : listaEleitores.get(circulo)) {
				this.eleitores.put(el.getnIdent(), el);
			}
		}
		this.atualizarCirculos(listaEleitores);
	}

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
			System.out.println("ativa id:"+ativa);
			if (e.getClass().getSimpleName().equals("EleicaoPR")) {
				System.out.println("debug1");
				el = this.eleicoesPR.get(e.getIdEleicao());
				System.out.println("debug2");
				el.iniciar();
				System.out.println("added");
				this.eleicoesPR.put(el.getIdEleicao(), (EleicaoPR) el);
				System.out.println("debug3");
			} else {
				System.out.println("debug4");
				el = this.eleicoesAR.get(e.getIdEleicao());
				System.out.println("debug5");
				el.iniciar();
				System.out.println("debug6");
				this.eleicoesAR.put(el.getIdEleicao(), (EleicaoAR) el);
				System.out.println("debug7");
			}
			this.ativa = e.getIdEleicao();
		}
	}

	public void terminarEleicao(Eleicao e) throws ExceptionEleicaoAtiva, ExceptionTerminarEleicao {
		Eleicao el;
		if (this.ativa == -1) {
			throw new ExceptionEleicaoAtiva("Não existe eleição ativa");
		} else {
			if (e.getClass().getSimpleName().equals("EleicaoPR")) {
				el = this.eleicoesPR.get(e.getIdEleicao());
				el.terminar();
				if(el.estado(TERMINADA)) this.ativa=-1;
				this.eleicoesPR.put(el.getIdEleicao(), (EleicaoPR) el);
			} else {
				el = this.eleicoesAR.get(e.getIdEleicao());
				el.terminar();
				this.ativa=-1;
				this.eleicoesAR.put(el.getIdEleicao(), (EleicaoAR) el);
			}
		}
	}

	public EleicaoPR criarEleicaoPR(EleicaoPR eleicao) {
		eleicao.setIdEleicao(this.chaveEleicao());
		this.eleicoesPR.put(eleicao.getIdEleicao(), eleicao);
		EleicaoPR l = this.eleicoesPR.get(eleicao.getIdEleicao());
		l.initResultadoCirculoPRDAO(eleicao.getIdEleicao(), 1, circulos.values());
		l.initResultadoCirculoPRDAO(eleicao.getIdEleicao(), 2, circulos.values());
		this.eleicoesPR.put(l.getIdEleicao(), l);
		return l;
	}

	public EleicaoAR criarEleicaoAR(EleicaoAR eleicao) {
		eleicao.setIdEleicao(this.chaveEleicao());
		this.eleicoesAR.put(eleicao.getIdEleicao(), eleicao);
		eleicao.inicializarCirculos(eleicao.getIdEleicao(),this.circulos.values());
		return eleicao;
	}

	public Eleicao eleicaoAtiva() {
		Eleicao ativa;
		if ((ativa = this.eleicoesAR.get(this.ativa)) == null) {
			ativa = this.eleicoesPR.get(this.ativa);
		}
		return ativa;
	}
	
	public String eleicaoAtivaString(){
		if(eleicaoAtiva()!=null) return eleicaoAtiva().getIdEleicao()+"";
		else return "";
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
		Boletim b;
		if (e.getClass().getSimpleName().equals("EleicaoPR")) {
			EleicaoPR pr = (EleicaoPR) e;
			b = pr.getBoletim();
		} else {
			EleicaoAR ar = (EleicaoAR) e;
			b = ar.getBoletim(eleitor.getCirculo());
		}
		return b;
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
			//System.out.println("ar:"+maxAR);
		}
		if (this.eleicoesPR.size() > 0) {
			maxPR = Collections.max(this.eleicoesPR.keySet());
			//System.out.println("pr:"+maxAR);
		}
		//System.out.println("max"+(Math.max(maxPR, maxAR) + 1));
		return (Math.max(maxPR, maxAR) + 1);
	}

	private int chaveColigacoes() {
		int max = 0;
		if (this.coligacoes.size() > 0) {
			max = Collections.max(this.coligacoes.keySet());
		}
		return max + 1;
	}

	public void addCandidatoPR(EleicaoPR eleicao, Candidato cand) throws ExceptionListaExiste, ExceptionLimiteCandidatos, ExceptionMandanteInvalido, ExceptionEleicaoEstado {
		EleicaoPR pr = this.eleicoesPR.get(eleicao.getIdEleicao());
		ListaPR lista = new ListaPR(0, 0, cand);
		pr.addLista(lista);
		this.eleicoesPR.put(pr.getIdEleicao(), pr);
	}

	public void addCandidatoAR(EleicaoAR eleicao, Lista lista, CandidatoAR cand) throws ExceptionLimiteCandidatos, ExceptionMandanteInvalido {
		EleicaoAR ar = this.eleicoesAR.get(eleicao.getIdEleicao());
		ar.addCandidato(lista, cand);
		this.eleicoesAR.put(ar.getIdEleicao(), ar);
	}
	
	public void removeCandidatoAR(EleicaoAR eleicao, Lista lista, CandidatoAR cand){
		eleicao.removeCandidatoAR(lista, cand);
	}

	public Eleicao getEleicao(int idEleicao) {
		Eleicao el;
		if ((el = this.eleicoesPR.get(idEleicao)) == null) {
			el = this.eleicoesAR.get(idEleicao);
		}
		return el;
	}

	public CandidatoAR getCandidatoAR(int bi){
		CandidatoAR candidato = null;
		for(EleicaoAR eleicao: this.eleicoesAR.values()){
			candidato = eleicao.getCandidato(bi);
			if(candidato != null) return candidato;
		}
		return candidato;
	}
	
	public ResultadoCirculoPR getResultadoCirculoPR(EleicaoPR e, int volta, int circulo) {
		return e.getResultadoCirculo(volta, circulo);
	}

	public ResultadoCirculoAR getResultadoCirculoAR(EleicaoAR e, int circulo) {
		return e.getResultadoCirculo(circulo);
	}

	public ResultadoGlobalPR getResultadoGlobalPR(EleicaoPR e, int volta) {
		return e.getResultadoGlobal(volta);
	}

	public ResultadoGlobalAR getResultadoGlobalAR(EleicaoAR e) {
		return e.getResultadoGlobal();
	}

	public Set<ListavelVotos> ordenarListavel(HashMap<Listavel, Integer> listas) {
		TreeSet<ListavelVotos> listasSeg = new TreeSet<>(new ComparatorListavelVotos());
		for (Listavel lista : listas.keySet()) {
			ListavelVotos lv = new ListavelVotos(lista, listas.get(lista));
			listasSeg.add(lv);
		}
		return listasSeg;
	}
	
	public Set<ListavelVotos> ordenarLista(HashMap<Lista, Integer> listas) {
		TreeSet<ListavelVotos> listasSeg = new TreeSet<>(new ComparatorListavelVotos());
		for (Lista lista : listas.keySet()) {
			ListavelVotos lv = new ListavelVotos(lista, listas.get(lista));
			listasSeg.add(lv);
		}
		return listasSeg;
	}
	
	public Set<VotavelVotos> ordenarVotavel(HashMap<Votavel, Integer> listas){
		TreeSet<VotavelVotos> listasOrdenadas = new TreeSet<>(new ComparatorVotavelVotos());
		for (Votavel vot : listas.keySet()) {
			VotavelVotos vv = new VotavelVotos(vot, listas.get(vot));
			listasOrdenadas.add(vv);
		}
		return listasOrdenadas;
	}
	
	public Circulo getCirculo(int num){
		return this.circulos.get(num);
	}

	
	public Set<Votavel> getVotaveis(){
		Set<Votavel> vot = new HashSet<>();
		for(Partido  p : this.partidos.values()){
			if(!p.isRemovido()){
				vot.add(p);
			}
		}
		for(Coligacao  c : this.coligacoes.values()){
			if(!c.isRemovido()){
				vot.add(c);
			}
		}
		return vot;	
	}

	public ArrayList<Partido> partCandidato(Votavel vot){
		ArrayList<Partido> part = new ArrayList<>();
		if(vot.getClass().getSimpleName().equals("Coligacao")){
			for (Partido partido : ((Coligacao)vot).getPartidos()) {
				part.add(partido);
			}
		}else{
			part.add((Partido)vot);
		}
		return part;
	}

	public Set<Partido> getPartidos(){
		Set<Partido> part = new HashSet<Partido>();
		for(Partido p : this.partidos.values()){
			if(!p.isRemovido()){
				part.add(p);
			}
		}
		return part;
	}
	
	public Set<Coligacao> getColigacoes(){
		Set<Coligacao> col = new HashSet<Coligacao>();
		for(Coligacao c : this.coligacoes.values()){
			if(!c.isRemovido()){
				col.add(c);
			}
		}
		return col;
	}
	
}
