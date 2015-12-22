/**
 * 
 */
package teste;

import Business.*;
import Data.*;

/**
 * @author Pedro Pinto
 *
 */
public class cenasPinto {
	public static void main(String[] args) {
		EleicaoARDAO eleicao = new EleicaoARDAO();
		CirculoDAO circulos = new CirculoDAO();
		for (int i=1;i<23;i++){
			Circulo c = new Circulo(i,"circulo "+i,0);
			Circulo s = circulos.put(i, c);
			if(s!=null){
				System.out.println(s.toString());
			}else{
				System.out.println("novo");
			}
		}
		System.out.println("Fim");
		System.out.println(circulos.size());
	}
}
