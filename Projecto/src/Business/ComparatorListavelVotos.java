package Business;

import java.util.Comparator;

public class ComparatorListavelVotos implements Comparator<ListavelVotos> {

	public int compare(ListavelVotos lv1, ListavelVotos lv2) {
		if (lv1.getVotos() > lv2.getVotos())
			return 1;
		if (lv1.getVotos() < lv2.getVotos())
			return -1;
		// ver melhor isto que nao sei qual o outro factor por o qual vou
		// comparar para nao eleiminar uma lista
		return 0;
	}
}
