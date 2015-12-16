package Business;

import java.util.Comparator;

public class ComparatorListaPRVolta1 implements Comparator<Listavel>{
	@Override
	public int compare(Listavel o1, Listavel o2) {
		ListaPR l1 = (ListaPR) o1;
		ListaPR l2 = (ListaPR) o2;
		if(l1.ordem1()<l2.ordem1()) return 1;
        if(l1.ordem1()>l2.ordem1()) return -1;
        return 0;
	}
}