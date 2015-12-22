package Comparator;

import java.util.Comparator;

import Business.ListavelVotos;

public class ComparatorListavelVotos implements Comparator<ListavelVotos> {

	public int compare(ListavelVotos lv1, ListavelVotos lv2) {
		if (lv1.getVotos() > lv2.getVotos())
			return 1;
		if (lv1.getVotos() < lv2.getVotos()) {
			return -1;
		}
		return lv1.toString().compareTo(lv2.toString());
	}
}
