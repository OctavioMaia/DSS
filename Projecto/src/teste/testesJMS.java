package teste;

import java.io.File;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import Business.Candidato;
import Business.Circulo;
import Business.Eleitor;
import Business.ListaPR;
import Data.CirculoDAO;
import Data.EleitoresDAO;
import Data.ListaPRDAO;

public class testesJMS {
	
	public static void main(String[] args) {
		ListaPRDAO lpdao = new ListaPRDAO(1);
		lpdao.clear();
		Candidato c = new Candidato("NUNO PANELEIRO", 12345679, "Come CONAS", new GregorianCalendar(1969,0,2), "Braga", "Lisboa", "A tua prima");
		ListaPR lp = new ListaPR(1, 1, true, 1, 2, c);
		ListaPR lp2 = lpdao.put(1, lp);
		if(lp2!=null){
			System.out.println(lp2.toString());
		}else{
			System.out.println("novo");
		}
		System.out.println("FIM");
		System.out.println("REM:" + lpdao.remove(1));
		System.out.println("Size:" + lpdao.size());
		
	
	}
}
