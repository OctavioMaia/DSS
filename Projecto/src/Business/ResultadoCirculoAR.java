package Business;

import java.util.HashMap;

public class ResultadoCirculoAR {
    private int brancos;
    private int nulos;
    private int totEleitor;
    private HashMap<Lista,Integer> validos;
    private HashMap<Lista,Integer> mandatos;

    public ResultadoCirculoAR(){
    	this.brancos = 0;
    	this.nulos = 0;
    	this.totEleitor = 0;
    	this.validos = new HashMap<>();
    	this.mandatos = new HashMap<>();
    }
    
    public ResultadoCirculoAR(int totEleitor){
    	this.brancos = 0;
    	this.nulos = 0;
    	this.totEleitor = totEleitor;
    	this.validos = new HashMap<>();
    	this.mandatos = new HashMap<>();
    }
    
    public ResultadoCirculoAR(int brancos, int nulos, int totEleitor) {
        this.brancos = brancos;
        this.nulos = nulos;
        this.totEleitor = totEleitor;
        this.validos = new HashMap<>();
        this.mandatos = new HashMap<>();
    }

    public ResultadoCirculoAR(int brancos, int nulos, int totEleitor, HashMap<Lista, Integer> validos,
			HashMap<Lista, Integer> mandatos) {
		this.brancos = brancos;
		this.nulos = nulos;
		this.totEleitor = totEleitor;
		this.validos = validos;
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
    
    public HashMap<Lista, Integer> getValidos() {
		return validos;
	}

	public void setValidos(HashMap<Lista, Integer> validos) {
		this.validos = validos;
	}

	public HashMap<Lista, Integer> getMandatos() {
		return mandatos;
	}

	public void setMandatos(HashMap<Lista, Integer> mandatos) {
		this.mandatos = mandatos;
	}
	
	public void setMandatosLista(Lista lista, int mandatos){
		this.mandatos.put(lista,mandatos);
	}

	public void addVoto(Lista lista){
    	this.validos.put(lista, this.validos.get(lista)+1);
    }
}
