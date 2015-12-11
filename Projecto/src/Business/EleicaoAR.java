package Business;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Data.CirculoInfoDAO;
import Data.ResultadoCirculoARDAO;

public class EleicaoAR extends Eleicao {
	private CirculoInfoDAO circulos;
	private HashSet<Votavel> concorrentes;
	private ResultadoCirculoARDAO resultado;

	public EleicaoAR(int idEleicao, Date data, Map<Integer, Integer> totEleitores) {
		super(idEleicao, data);
		this.circulos = new CirculoInfoDAO(idEleicao);
		for (int i = 1; i <= 22; i++) {
			if (!this.circulos.containsKey(i))
				this.circulos.put(i, new CirculoInfo());
		}
		this.concorrentes = new HashSet<>();
		this.resultado = new ResultadoCirculoARDAO(idEleicao);
		for (int i = 1; i <= 22; i++) {
			if (!this.resultado.containsKey(i))
				this.resultado.put(i, new ResultadoCirculoAR(totEleitores.get(i)));
		}
	}

	public EleicaoAR(int idEleicao, Date data, int estado, boolean permitirVotar, Set<Integer> vot,
			Map<Integer, Integer> totEleitores, HashSet<Votavel> concorrentes) {
		super(idEleicao, data,estado,permitirVotar,vot);
		this.circulos = new CirculoInfoDAO(idEleicao);
		for (int i = 1; i <= 22; i++) {
			if (!this.circulos.containsKey(i))
				this.circulos.put(i, new CirculoInfo());
		}
		this.concorrentes = concorrentes;
		this.resultado = new ResultadoCirculoARDAO(idEleicao);
		for (int i = 1; i <= 22; i++) {
			if (!this.resultado.containsKey(i))
				this.resultado.put(i, new ResultadoCirculoAR(totEleitores.get(i)));
		}
	}

	public void setTotEleitores(Map<Integer, Integer> totEleitores) {
		for (int i : totEleitores.keySet()) {
			if (i >= 1 && i <= 22) {
				ResultadoCirculoAR rc = this.resultado.get(i);
				rc.setTotEleitor(totEleitores.get(i));
				this.resultado.put(i, rc);
			}
		}
	}

}
