package teste;

import java.io.File;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import Business.Candidato;
import Business.Circulo;
import Business.EleicaoPR;
import Business.Eleitor;
import Business.ListaPR;
import Business.ResultadoCirculoPR;
import Data.CirculoDAO;
import Data.EleicaoPRDAO;
import Data.EleitoresDAO;
import Data.ListaPRDAO;
import Data.ResultadoCirculoPRDAO;

public class testesJMS {
	
	public static void main(String[] args) {
		EleicaoPRDAO elidao = new EleicaoPRDAO();
		CirculoDAO cd = new CirculoDAO();
		for (int i = 1; i <23; i++) {
			Circulo c = new Circulo(i, "Circulo " +i, 0);
			cd.put(i, c);
			ListaPRDAO lpdao = new ListaPRDAO(1);
			lpdao.clear();
			
		}
		
		EleicaoPR epr = new EleicaoPR(1, new GregorianCalendar(2017,0,2),cd.values()); 
		elidao.put(1, epr);
		for (int i = 1; i < 11; i++) {
			Candidato c = new Candidato("NUNO PANELEIRO"+i, 12345679+i, "Come CONAS VELHAS " +i, new GregorianCalendar(1969,0,2), "Braga"+i, "Lisboa"+i, "A tua prima " +i);
			ListaPR lp =  new ListaPR(1, i, c);
			for (int j = 1; j < 23; j++) {
				ListaPRDAO lpdao = new ListaPRDAO(1);
				System.out.println("Lista:" + i + "Circulo: " + j);
				ListaPR lp2 = lpdao.put(i, lp);
				if(lp2!=null){
					System.out.println(lp2.toString());
				}else{
					System.out.println("novo");
				}
			}	
		}

		ResultadoCirculoPRDAO rcprdao= new ResultadoCirculoPRDAO(1, 1);
		
		for (int i = 1; i < 23; i++) {
			HashMap<ListaPR,Integer> map = new HashMap<>();
			ListaPRDAO lpdao = new ListaPRDAO(1);
			Collection<ListaPR> ls = lpdao.values();
			int k =0;
			for (ListaPR listaPR : ls) {
				k++;
				map.put(listaPR, 50*k);
				
			}
			for(ListaPR p : map.keySet()){
				System.out.println(p.getCandidato().toString() + " \nVotos: " + map.get(p));
			}
			//new ResultadoCirculoPR(c, totEleitores, brancos, nulos, validos)
			ResultadoCirculoPR rc = new ResultadoCirculoPR(cd.get(i), 500*i, 200*i, 100*i, map);
			System.out.println("Vou Meter");
			rcprdao.put(i, rc);
			System.out.println("Meti");
		}
		
		System.out.println("Size:" + rcprdao.size());
		rcprdao.clear();
		System.out.println("CLEAR\nSize:" + rcprdao.size());
		//System.out.println("Exiete a lista:" + lpdao.containsValue(lp));
		//elidao.remove(1);
		System.out.println("FIM");
		//System.out.println("REM:" + lpdao.remove(1));
		/*System.out.println("Size:" + lpdao.size());
		Collection<ListaPR> ls = lpdao.values();
		for (ListaPR listaPR : ls) {
			System.out.println(listaPR.getCandidato().toString());
		}*/
	
	}
}
