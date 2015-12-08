package Business;

import Data.CirculoDAO;
import Data.PartidosDAO;
import Data.ColigacaoDAO;
import Data.Connector;
import Data.EleicaoARDAO;
import Data.EleicaoPRDAO;
import Data.EleitoresDAO;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
	
	public void inserirCadernoRecenciamento(){
			
			ArrayList<Eleitor> listaEleitores = new ArrayList<Eleitor>();
			
			String ficheiroCSV = "Inserir directoria.csv";
			String line = "";
			String cvsSplit = ",";
			BufferedReader br = null;
			
			try{
				
				br = new BufferedReader	(new FileReader(ficheiroCSV));
				while ((line = br.readLine()) != null) {
					String[] eleitores = line.split(cvsSplit);
					int a = Integer.parseInt(eleitores[1]);
					int b = Integer.parseInt(eleitores[0]);
					Eleitor e = new Eleitor(eleitores[2],a,b,eleitores[3]);
					listaEleitores.add(e);
				}
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			catch (IOException e) {
				e.printStackTrace();
			} 
			finally {
				if (br != null) {
					try {
						br.close();
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		for (Eleitor e: listaEleitores) {
			eleitores.put(e.getnIdent(),e);
			}
		
		}
	
	
	public boolean iniciarEleicao(String id){return true;}
	
	public ResultadoPR verResultadosPR(String id){return new ResultadoPR(resultadosPR);}
	
	public ResultadoAR verREsultadoAR(String id){return new ResultaAR(resultadosAR);}
	
	public void criarEleicaoAR(){}
	
	public void addListasPR(){}
	
	public void addListasAR(){}
	
	public void votar(int idEleitor, int idCirculo, int idLista){}
}
