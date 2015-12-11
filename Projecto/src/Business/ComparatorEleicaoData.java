package Business;

import java.util.Comparator;
import java.util.Date;

public class ComparatorEleicaoData implements Comparator<Eleicao>{

	public int compare(Eleicao e1, Eleicao e2) {
        if(e1.getData().before(e2.getData())) return 1;
        if(e1.getData().after(e2.getData())) return -1;
        return 0;
    }
}