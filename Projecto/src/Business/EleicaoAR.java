package Business;

import java.util.Date;
import java.util.HashSet;

import Data.CirculoInfoDAO;
import Data.ResultadoCirculoARDAO;

public class EleicaoAR extends Eleicao{
	private CirculoInfoDAO circulos;
	private HashSet<Votavel> concorrentes;
	private ResultadoCirculoARDAO resultado;
	
	public EleicaoAR(int idEleicao, Date data, HashSet<Votavel> concorrentes) {
		super(idEleicao, data);
		this.circulos = new CirculoInfoDAO(idEleicao);
		this.concorrentes = concorrentes;
		this.resultado = new ResultadoCirculoARDAO(idEleicao);
	}
	
	
}
