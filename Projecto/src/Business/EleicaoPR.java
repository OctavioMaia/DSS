package Business;

import java.util.Date;
import java.util.Map;
import Data.ListaPRDAO;
public class EleicaoPR extends Eleicao{
	private int IdEleicao;
	private boolean volta2;
	private Date data2;
	private ResultadoCirculoPRDAO voltaR1;
	private ResultadoCirculoPRDAO voltaR2;
	private Boletim boletim1;
	private Boletim boletim2;
	private ListaPRDAO listas;
	
	
	public EleicaoPR(Date data, int estado, int idEleicao, boolean volta2, Date data2, Boletim boletim1, Boletim boletim2) {
		super(data, estado, idEleicao);
		this.IdEleicao = idEleicao;
		this.volta2 = volta2;
		this.data2 = data2;
		this.voltaR1 = new ResultadoCirculoPRDAO(idEleicao,1);
		this.voltaR2 = new ResultadoCirculoPRDAO(idEleicao,2);
		this.boletim1 = boletim1;
		this.boletim2 = boletim2;
		this.listas= new ListaPRDAO();
	}

	public int getIdEleicao() {
		return IdEleicao;
	}
	public void setIdEleicao(int idEleicao) {
		IdEleicao = idEleicao;
	}
	public boolean isVolta2() {
		return volta2;
	}
	public void setVolta2(boolean volta2) {
		this.volta2 = volta2;
	}
	public Date getData2() {
		return data2;
	}
	public void setData2(Date data2) {
		this.data2 = data2;
	}
	
	
}
