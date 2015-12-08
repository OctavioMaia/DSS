package Business;

import Data.CirculoDAO;
import Data.PartidosDAO;
import Data.ColigacaoDAO;
import Data.Connector;
import Data.EleicaoARDAO;
import Data.EleicaoPRDAO;
import Data.EleitoresDAO;

public class SGE {
	private Connector cn;
	private PartidosDAO partidos;
	private CirculoDAO circulos;
	private ColigacaoDAO coligacoes;
	private EleitoresDAO eleitores;
	private EleicaoPRDAO eleicoesPR;
	private EleicaoARDAO eleicoesAR;
	private int ativa;
	
	
	public SGE(){
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
	
	private int procuraEleicaoAtiva(){
		
		return -1;
		
	}
	
	public boolean inserirCadernoRecenciamento(){return true;}
	
	public boolean iniciarEleicao(String id){return true;}
	
	public ResultadoPR verResultadosPR(String id){return new ResultadoPR(resultadosPR);}
	
	public ResultadoAR verREsultadoAR(String id){return new ResultaAR(resultadosAR);}
	
	public void criarEleicaoAR(){}
	
	public void addListasPR(){}
	
	public void addListasAR(){}
	
	public void votar(int idEleitor, int idCirculo, int idLista){}
}
