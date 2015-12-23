package Business;

import java.util.Calendar;
import java.util.Date;

public class CandidatoAR extends Candidato {

	private Partido partido;
	private char tipo; 	// P:Primário; S:Secundário
	private int ordem;

	/*
	 * Construtor sem a ordem do candidato na lista. Ao adicionar um candidato
	 * a uma lista, este valor e atualizado automaticamente.
	 */
	public CandidatoAR(String nome, int bi, String prof, Calendar dataNasc, String residencia, String naturalidade,Partido partido, char tipo) {
		super(nome, bi, prof, dataNasc, residencia, naturalidade,"");
		this.partido = partido;
		this.tipo = tipo;
		this.ordem = -1;
	}
	
	/*
	 * Construtor com ordem
	 */
	public CandidatoAR(String nome, int bi, String prof, Calendar dataNasc, String residencia, String naturalidade,Partido partido, char tipo, int ordem) {
		super(nome, bi, prof, dataNasc, residencia, naturalidade,"");
		this.partido = partido;
		this.tipo = tipo;
		this.ordem = ordem;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
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

	public int compareTo(CandidatoAR candidato){
		if(candidato.getOrdem() < this.ordem) return -1;
		else if (candidato.getOrdem() > this.ordem) return 1;
		else return 0;
	}
	
	public String toString() {
		return "Candidato " + super.getNome() + ", com BI nº"+super.getBi() + ", nascido a " + super.getDataNasc().getTime() + ", pertencente ao partido " + partido;
	}
}
