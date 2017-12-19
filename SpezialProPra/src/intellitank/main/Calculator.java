package intellitank.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import intellitank.utils.PriceList;
import intellitank.utils.Timestamp;

public class Calculator
{
	// 1.	YYYY-MM-DD HH:MM:SS+HH bis zu dem Sie die Benzinpreise als bekannt annehmen und f�r Ihre Berechnungen verwenden d�rfen
	// 2.	geforderten	Zeitpunkt f�r die Vorhersage
	// 2.	Tankstellen-ID f�r die Vorhersage
	
	// 2015-02-10 12:18:01+01	;	2015-02-15 21:18:01+01	;	24
	
	// tats�chlich: 2015-02-15 17:50:01+01;1309
	public static int forecastPrice(int id, Timestamp knowPrice, Timestamp forecastTime)
	{
		int result = 0;
		
		PriceList prices = PriceList.fromString("https://raw.githubusercontent.com/InformatiCup/InformatiCup2018/master/Eingabedaten/Benzinpreise/" + id +".csv");
		prices.clean();
		knowPrice.clean();
		
		int daysback = 5;
		
		Timestamp lastTime = knowPrice.clone();
		lastTime.setDay(lastTime.getDay() - daysback);
		
		System.out.println(lastTime + " - " + knowPrice);
		
		LinkedHashMap<Timestamp, Integer> map = new LinkedHashMap<>();
		
		int i = 1;
		
		for(Timestamp time : prices.getValues().keySet())
		{
//			System.out.println("[" + i + "] " + time);
			if(time.compare(lastTime) < 0 && time.compare(knowPrice) > 0) map.put(time, prices.getValues().get(time));
			
			i++;
		}
		
//		System.out.println("map: " + map.toString());
		
		return result;
	}
}
