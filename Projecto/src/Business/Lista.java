package Business;

import java.util.ArrayList;

/*
 * Lista da Assembleia da República
 */
public class Lista {
	private int id;
	private String sigla;
	private String nome;
	private String simbolo;
	private Votavel mandante;
	private ArrayList<Candidato> candidatos;
	
	public Lista(int id, String sigla, String nome, String simbolo, Votavel mandante) {
		this.id=id;
		this.sigla = sigla;
		this.nome = nome;
		this.simbolo = simbolo;
		this.mandante = mandante;
		this.candidatos = new ArrayList<>();
	}

	public Lista(int id,String sigla, String nome, String simbolo, Votavel mandante, ArrayList<Candidato> candidatos) {
		this.id=id;
		this.sigla = sigla;
		this.nome = nome;
		this.simbolo = simbolo;
		this.mandante = mandante;
		this.candidatos = candidatos;
	}
	
	public int getID(){
		return id;
	}
	
	public void setID(int id){
		this.id=id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public Votavel getMandante() {
		return mandante;
	}

	public void setMandante(Votavel mandante) {
		this.mandante = mandante;
	}

	public ArrayList<Candidato> getCandidatos(){
		return this.candidatos;
	}
	
	public void addCandidato(Candidato candidato){
		if(candidato != null) this.candidatos.add(candidato);
	}

	public void removeCandidato(Candidato candidato){
		this.candidatos.remove(candidato);
	}
}
