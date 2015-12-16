package Business;

import java.util.ArrayList;

/*
 * Lista da Assembleia da Republica
 */
public class Lista implements Listavel{
	private int id;
	private Circulo circulo;
	private int ordem;
	private String sigla;
	private String nome;
	private String simbolo;
	private Votavel mandante;
	private ArrayList<CandidatoAR> candidatos;
	
	public Lista(int id, int circulo, String sigla, String nome, String simbolo, Votavel mandante) {
		this.id=id;
		this.circulo = circulo;
		this.ordem=-1;
		this.sigla = sigla;
		this.nome = nome;
		this.simbolo = simbolo;
		this.mandante = mandante;
		this.candidatos = new ArrayList<>();
	}

	public Lista(int id, int circulo, String sigla, String nome, String simbolo, Votavel mandante, ArrayList<CandidatoAR> candidatos) {
		this.id=id;
		this.circulo = circulo;
		this.ordem=-1;
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

	public int getCirculo() {
		return circulo;
	}

	public void setCirculo(int circulo) {
		this.circulo = circulo;
	}

	public int getOrdem(){
		return ordem;
	}
	
	public void setOrdem(int ordem){
		this.ordem=ordem;
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

	public ArrayList<CandidatoAR> getCandidatos(){
		return this.candidatos;
	}
	
	public void addCandidato(CandidatoAR candidato){
		if(candidato != null) this.candidatos.add(candidato);
	}

	public void removeCandidato(Candidato candidato){
		this.candidatos.remove(candidato);
	}
	
	public int numCandPrim(){
		Iterator<Candidatos> it = candidatos.iterator();
		while(it.has)
	}
}
