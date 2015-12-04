package Business;

import java.util.Date;

public class CandidatoAR extends Candidato {

	private Partido partido;
	private char tipo;

	public CandidatoAR(String nome, int bi, String prof, Date dataNasc, String residencia, String naturalidade,Partido partido, char tipo) {
		super(nome, bi, prof, dataNasc, residencia, naturalidade);
		this.partido = partido;
		this.tipo = tipo;
	}

	public String toString() {
		return "Candidato " + super.getNome() + ", com BI nº"+super.getBi() + ", nascido a " + super.getDataNasc().toGMTString() + ", filiado ao partido " + partido;
	}
	
}