package intellitank.main;

import java.util.ArrayList;
import java.util.List;

import intellitank.Logger;
import intellitank.utils.PriceList;
import intellitank.utils.Timestamp;

public class Calculator
{
	// 1.	YYYY-MM-DD HH:MM:SS+HH bis zu dem Sie die Benzinpreise als bekannt annehmen und für Ihre Berechnungen verwenden dürfen
	// 2.	geforderten	Zeitpunkt für die Vorhersage
	// 3.	Tankstellen-ID für die Vorhersage
	
	// 2015-02-10 12:18:01+01	;	2015-02-15 21:18:01+01	;	24
	
	// tatsächlich: 2015-02-15 17:50:01+01;1309
	public static int forecastPrice(int id, Timestamp knowPrice, Timestamp forecastTime)
	{
		int result = 0; 	
		int daysback = 7;
		
		Timestamp lastTime = knowPrice.clone();
		lastTime.setDay(lastTime.getDay() - daysback);
		lastTime.clean();
		
		knowPrice.clean();
		
		PriceList prices = PriceList.fromString("https://raw.githubusercontent.com/InformatiCup/InformatiCup2018/master/Eingabedaten/Benzinpreise/" + id +".csv");
		prices.clean(lastTime.clone(), knowPrice.clone());
		
		List<Integer> averages = new ArrayList<>();
		
		Timestamp currentTime = lastTime.clone();
		
		while(currentTime.compare(knowPrice) != 0)
		{
			for(int h=0; h<24; h++)
			{
				if(h == forecastTime.getHour())
				{
					Timestamp time = currentTime.clone();

					int temp = 0;
					
					time.setHour(time.getHour() - 1);
					temp += prices.getPriceAt(time);

					time.setHour(time.getHour());
					temp += prices.getPriceAt(time);

					time.setHour(time.getHour() + 1);
					temp += prices.getPriceAt(time);

					averages.add(temp / 3);
				}
			}
			
			currentTime.setDay(currentTime.getDay() + 1);
		}
		
		Logger.log("averages > " + averages);
		
		if(!averages.isEmpty())
		{
			for(Integer value : averages)
			{
				result += value;
			}
			
			result /= averages.size();
		}
		
		Logger.log("result > " + result);
		
		return result;
	}
}
