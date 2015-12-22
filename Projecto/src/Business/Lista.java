package Business;

import java.util.ArrayList;
import java.util.Iterator;

import Exception.ExceptionMandanteInvalido;

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

	public Lista(int id, Circulo circulo, String sigla, String nome, String simbolo, Votavel mandante) {
		this.id=id;
		this.circulo = circulo;
		this.ordem=-1;
		this.sigla = sigla;
		this.nome = nome;
		this.simbolo = simbolo;
		this.mandante = mandante;
		this.candidatos = new ArrayList<>();
	}

	public Lista(int id, Circulo circulo,int ordem, String sigla, String nome, String simbolo, Votavel mandante, ArrayList<CandidatoAR> candidatos) {
		this.id=id;
		this.circulo = circulo;
		this.ordem = ordem;
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

	public Circulo getCirculo() {
		return circulo;
	}

	public void setCirculo(Circulo circulo) {
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

	public void addCandidato(CandidatoAR candidato) throws ExceptionMandanteInvalido{
		if(this.mandante.getClass().getSimpleName().equals("Partido")){
			Partido partido = (Partido)this.mandante;
			if(!candidato.getPartido().equals(partido)){
				throw new ExceptionMandanteInvalido("Partido Inválido");
			}
		}
		else if(this.mandante.getClass().getSimpleName().equals("Coligacao")){
			Coligacao coligacao = (Coligacao)this.mandante;
			if(!coligacao.getPartidos().contains(candidato.getPartido())){
				throw new ExceptionMandanteInvalido("Partido Inválido");
			}
		}
		candidato.setOrdem(this.candidatos.size()+1);
		this.candidatos.add(candidato);
	}

	public void removeCandidato(CandidatoAR candidato){
		int posicao = this.candidatos.indexOf(candidato);
		for(int i = posicao; i<this.candidatos.size(); i++){
			CandidatoAR candidatoAux = this.candidatos.get(i);
			candidatoAux.setOrdem(candidatoAux.getOrdem()-1);
		}
		this.candidatos.remove(candidato);
	}

	public int getNumCandPrim(){
		int count = 0;
		Iterator<CandidatoAR> it = candidatos.iterator();
		while(it.hasNext()){
			CandidatoAR candidato = it.next();
			if(candidato.getTipo() == 'P') count++;
		}
		return count;
	}

	public int getNumCandSec(){
		return this.candidatos.size()-this.getNumCandPrim();
	}

	public CandidatoAR getCandidato(int bi) {
		CandidatoAR candidato = null;
		for(CandidatoAR c: this.candidatos){
			if(c.getBi()==bi)
				return c;
		}
		return candidato;
	}

	@Override
	public boolean equals(Object o){
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Lista lista = (Lista)o;
        return this.circulo.equals(lista.getCirculo())
        		&& this.nome.equals(lista.getNome());
	}

	@Override
	public String[] toTable() {
		String[] lista = {this.nome, Integer.toString(this.nIdent)};
    	return lista;
	}

}
