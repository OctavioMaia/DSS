package teste;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class testeMax {
	public static void main(String[] args){
		Map<String, Integer> values = new HashMap<String, Integer>() {{
		    //put("0", 22);
		    //put("1", 6763);
		    //put("2", 32);
		    //put("3", 42);
		    //put("4", 33);
		}};
		int max = Collections.max(values.values());
		System.out.println(max);
	}
}
