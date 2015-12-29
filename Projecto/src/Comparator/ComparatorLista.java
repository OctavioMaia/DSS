package Comparator;

import java.util.Comparator;

import Business.Lista;

public class ComparatorLista implements Comparator<Lista>{

	public int compare(Lista arg0, Lista arg1) {
		if (arg0.getID() > arg1.getID())
			return -1;
		if (arg0.getID() < arg1.getID()) {
			return 1;
		}
		return 0;
	}


}
