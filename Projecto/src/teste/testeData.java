package teste;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class testeData {
	public static void main (String[] args){
		Calendar data1 = new GregorianCalendar();
		System.out.println(data1.getTime());
		data1.add(Calendar.DAY_OF_YEAR, 30);
		System.out.println(data1.getTime());
		while(data1.get(Calendar.DAY_OF_WEEK)!=Calendar.SUNDAY){
			data1.add(Calendar.DAY_OF_YEAR, 1);
			System.out.println(data1.getTime());
		}
	}
}
