package Business;

import java.util.Calendar;
import java.util.Date;

public class CandidatoAR extends Candidato {

	private Partido partido;
	private char tipo; 	// P:Primário; S:Secundário

	public CandidatoAR(String nome, int bi, String prof, Calendar dataNasc, String residencia, String naturalidade,Partido partido, char tipo, String foto) {
		super(nome, bi, prof, dataNasc, residencia, naturalidade,foto);
		this.partido = partido;
		this.tipo = tipo;
	}

	public Partido getPartido() {
		return partido;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

	@SuppressWarnings("deprecation")
	public String toString() {
		return "Candidato " + super.getNome() + ", com BI nº"+super.getBi() + ", nascido a " + super.getDataNasc().getTime() + ", pertencente ao partido " + partido;
	}
}
