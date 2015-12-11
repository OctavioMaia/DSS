package Business;

import java.util.HashMap;

public class ResultadoCirculoAR {
    private int brancos;
    private int nulos;
    private int totEleitor;
    private HashMap<Integer,Integer> votos;
    private HashMap<Integer,Integer> mandatos;

    public ResultadoCirculoAR(){
    	this.brancos = 0;
    	this.nulos = 0;
    	this.totEleitor = 0;
    	this.votos = new HashMap<>();
    	this.mandatos = new HashMap<>();
    }
    
    public ResultadoCirculoAR(int totEleitor){
    	this.brancos = 0;
    	this.nulos = 0;
    	this.totEleitor = totEleitor;
    	this.votos = new HashMap<>();
    	this.mandatos = new HashMap<>();
    }
    
    public ResultadoCirculoAR(int brancos, int nulos, int totEleitor) {
        this.brancos = brancos;
        this.nulos = nulos;
        this.totEleitor = totEleitor;
        this.votos = new HashMap<>();
        this.mandatos = new HashMap<>();
    }

    public ResultadoCirculoAR(int brancos, int nulos, int totEleitor, HashMap<Integer, Integer> votos,
			HashMap<Integer, Integer> mandatos) {
		this.brancos = brancos;
		this.nulos = nulos;
		this.totEleitor = totEleitor;
		this.votos = votos;
		this.mandatos = mandatos;
	}

	public int getBrancos() {
        return brancos;
    }

    public int getNulos() {
        return nulos;
    }

    public int getTotEleitor() {
        return totEleitor;
    }

    public void setBrancos(int brancos) {
        this.brancos = brancos;
    }

    public void setNulos(int nulos) {
        this.nulos = nulos;
    }

    public void setTotEleitor(int totEleitor) {
        this.totEleitor = totEleitor;
    }
    
    public HashMap<Integer, Integer> getVotos() {
		return votos;
	}

	public void setVotos(HashMap<Integer, Integer> votos) {
		this.votos = votos;
	}

	public HashMap<Integer, Integer> getMandatos() {
		return mandatos;
	}

	public void setMandatos(HashMap<Integer, Integer> mandatos) {
		this.mandatos = mandatos;
	}
	
	public void setMandatosLista(int lista, int mandatos){
		this.mandatos.put(lista,mandatos);
	}

	public void addVoto(int lista){
    	this.votos.put(lista, this.votos.get(lista)+1);
    }
}
