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
}
