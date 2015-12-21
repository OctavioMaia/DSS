package Business;

import java.util.HashMap;
import java.util.Map;

public class Hondt {

	/* 
	 * O output e um hashmap<object,integer>. E preciso fazer cast
	 */
	public static HashMap<? extends Object,Integer> getMandatos(int maxMandatos,Map<? extends Object,Integer> votos){
		HashMap<Object,Integer> mandatosLista = new HashMap<>();
		for(Object lista: votos.keySet()){
			mandatosLista.put(lista,0);
		}
		int mandatos = 0;
		while(mandatos < maxMandatos){
			double maiorDivisao = -1;
			Object listaAtribuida = null;
			for(Object lista: votos.keySet()){
				double divisaoLista = (double)votos.get(lista)/(mandatosLista.get(lista)+1);
				if(divisaoLista > maiorDivisao){
					maiorDivisao = divisaoLista;
					listaAtribuida = lista;
				}
				else if(divisaoLista == maiorDivisao){
					if(mandatosLista.get(lista) < mandatosLista.get(listaAtribuida))
						listaAtribuida = lista;
				}
			}
			mandatosLista.put(listaAtribuida,mandatosLista.get(listaAtribuida)+1);
			mandatos++;
		}
		return mandatosLista;
	}
	
	/*
	 * Funcao para teste.
	 * Exemplo tirado de https://pt.wikipedia.org/wiki/M%C3%A9todo_D%27Hondt
 	public static void main(String[] args){

		int maxMandatos = 7;
		HashMap<String,Integer> votos = new HashMap<>();
		votos.put("A",12000);
		votos.put("B",7500);
		votos.put("C",4500);
		votos.put("D",3000);
		HashMap<String,Integer> mandatos = (HashMap<String, Integer>) getMandatos(maxMandatos,votos);
		for(String partido: votos.keySet()){
			System.out.printf("Partido %s: %d mandatos\n",partido,mandatos.get(partido));
		}
	}
 	*/
}
