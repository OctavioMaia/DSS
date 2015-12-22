/**
 * 
 */
package teste;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;

import Business.*;
import Data.*;

/**
 * @author Pedro Pinto
 *
 */
public class cenasPinto {
	public static void main(String[] args) {
		EleicaoARDAO eleicaoDAO = new EleicaoARDAO();
		/*EleicaoAR eleicao = new EleicaoAR(1,new GregorianCalendar(2015,12,22),
				new CirculoDAO().values(),230);
		eleicaoDAO.put(eleicao.getIdEleicao(), eleicao);*/
		eleicaoDAO.get(1);
	}
}
