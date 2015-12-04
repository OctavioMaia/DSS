package Business;

import java.util.Date;

public class CandidatoAR extends Candidato{
	
	private int partido;
	private char tipo;
	
	public CandidatoAR(String nome, int bi, String prof, Date dataNasc, String residencia,
			String naturalidade,int partido, char tipo) {
		super(nome, bi, prof, dataNasc, residencia, naturalidade);
		this.partido = partido;
		this.tipo = tipo;
	}

	public int getPartido() {
		return partido;
	}

	public void setint(int partido) {
		this.partido = partido;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
}
