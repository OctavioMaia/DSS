package Business;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;

import Data.CirculoInfoDAO;
import Data.ResultadoCirculoARDAO;

public class EleicaoAR extends Eleicao{
	private CirculoInfoDAO circulos;
	private HashSet<Votavel> concorrentes;
	private ResultadoCirculoARDAO resultado;
	
	/*
	 * Este construtor inicia todos os resultados a 0.
	 * É necessário, posteriormente,
	 * atualizar o total de eleitores em cada círculo.
	 */
	public EleicaoAR(int idEleicao, Date data) {
		super(idEleicao, data);
		this.circulos = new CirculoInfoDAO(idEleicao);
		for(int i = 1; i<=22; i++){
			if(!this.circulos.containsKey(i))
			this.circulos.put(i,new CirculoInfo());
		}
		this.concorrentes = new HashSet<>();
		this.resultado = new ResultadoCirculoARDAO(idEleicao);
		for(int i = 1; i<=22; i++){
			if(!this.resultado.containsKey(i))
			this.resultado.put(i,new ResultadoCirculoAR());
		}
	}
	
	public EleicaoAR(int idEleicao, Date data, Map<Integer,Integer> totEleitores){
		super(idEleicao, data);
		this.circulos = new CirculoInfoDAO(idEleicao);
		for(int i = 1; i<=22; i++){
			if(!this.circulos.containsKey(i))
			this.circulos.put(i,new CirculoInfo());
		}
		this.concorrentes = new HashSet<>();
		this.resultado = new ResultadoCirculoARDAO(idEleicao);
		for(int i = 1; i<=22; i++){
			if(!this.resultado.containsKey(i))
			this.resultado.put(i,new ResultadoCirculoAR(totEleitores.get(i)));
		}
	}
	
	public void setTotEleitores(Map<Integer,Integer> totEleitores){
		for(int i: totEleitores.keySet()){
			if(i>= 1 && i <= 22){
				ResultadoCirculoAR rc = this.resultado.get(i);
				rc.setTotEleitor(totEleitores.get(i));
				this.resultado.put(i,rc);
			}
		}
	}
	
}
