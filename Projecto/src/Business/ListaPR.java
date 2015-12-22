package Business;

/*
 * Lista da Presidencia da Republica
 */

public class ListaPR implements Listavel {
	
	private int idEleicao;
	private int idListaPR;
	private boolean volta2;
	private int ordem1;
	private int ordem2;
	private Candidato candidato;

	public ListaPR (int idEleicao,int idListaPR, Candidato candidato){
		this.idEleicao = idEleicao;
		this.idListaPR = idListaPR;
		this.volta2 = false;
		this.ordem1 = -1;
		this.ordem2 = -1;
		this.candidato = candidato;
	}
	
	public ListaPR (int idEleicao,int idListaPR, boolean volta2, int ordem1, int ordem2, Candidato candidato){
		this.idEleicao = idEleicao;
		this.idListaPR = idListaPR;
		this.volta2 = volta2;
		this.ordem1 = ordem1;
		this.ordem2 = ordem2;
		this.candidato = candidato;
	}
	public int getIdEleicao() {
		return idEleicao;
	}
	public void setIdEleicao(int idEleicao) {
		this.idEleicao = idEleicao;
	}
	public int getIdListaPR(){
		return this.idListaPR;
	}
	public boolean getVolta2(){
		return this.volta2;
	}
	public int ordem1(){
		return this.ordem1;
	}
	public int ordem2(){
		return this.ordem2;
	}
	public Candidato getCandidato(){
		return this.candidato;
	}
	public void setIdListaPR(int id){
		this.idListaPR = id;
	}
	public void setVolta2(boolean volta2){
		this.volta2 = volta2;
	}
	public void setOrdem1(int ordem1){
		this.ordem1 = ordem1;
	}
	public void setOrdem2(int ordem2){
		this.ordem2 = ordem2;
	}
	public void setCandidato(Candidato c) {
		this.candidato = c;
	}
	public boolean candidatoEquals(Candidato c){
		boolean r = false;
		r=this.candidato.equals(c);
		return r;
	}
	@Override
	public boolean equals(Object obj) {
		ListaPR lista = (ListaPR) obj;
		return this.idEleicao==lista.idEleicao;
	}
	public boolean equalsID(int idLista){
		return this.idEleicao==idLista;
	}
}
