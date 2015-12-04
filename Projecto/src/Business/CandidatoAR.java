package Business;

import java.util.Date;

<<<<<<< HEAD
public class CandidatoAR extends Candidato{
	
	private int partido;
	private char tipo;
	
	public CandidatoAR(String nome, int bi, String prof, Date dataNasc, String residencia,
			String naturalidade,int partido, char tipo) {
=======
public class CandidatoAR extends Candidato {

	private Partido partido;
	private char tipo;

	public CandidatoAR(String nome, int bi, String prof, Date dataNasc, String residencia, String naturalidade,Partido partido, char tipo) {
>>>>>>> refs/remotes/origin/master
		super(nome, bi, prof, dataNasc, residencia, naturalidade);
		this.partido = partido;
		this.tipo = tipo;
	}

<<<<<<< HEAD
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
=======
	public String toString() {
		return "Candidato " + super.getNome() + ", com BI nº"+super.getBi() + ", nascido a " + super.getDataNasc().toGMTString() + ", filiado ao partido " + partido;
	}
	
}
>>>>>>> refs/remotes/origin/master
