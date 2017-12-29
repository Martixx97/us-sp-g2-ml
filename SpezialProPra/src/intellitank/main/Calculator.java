package intellitank.main;

import java.util.ArrayList;
import java.util.List;

import intellitank.Logger;
import intellitank.utils.PriceList;
import intellitank.utils.Timestamp;

public class Calculator
{
	// 1.	YYYY-MM-DD HH:MM:SS+HH bis zu dem Sie die Benzinpreise als bekannt annehmen und f�r Ihre Berechnungen verwenden d�rfen
	// 2.	geforderten	Zeitpunkt f�r die Vorhersage
	// 3.	Tankstellen-ID f�r die Vorhersage
	
	// 2015-02-10 12:18:01+01	;	2015-02-15 21:18:01+01	;	24
	
	// tats�chlich: 2015-02-15 17:50:01+01;1309
	public static int forecastPrice(int id, Timestamp knowPrice, Timestamp forecastTime)
	{
		int result = 0;		
		int daysback = 7;
		
		Timestamp lastTime = knowPrice.clone();
		lastTime.setDay(lastTime.getDay() - daysback);
		lastTime.setTimezone("+02");
		lastTime.clean();
		
		knowPrice.clean();
		
		PriceList prices = PriceList.fromString("https://raw.githubusercontent.com/InformatiCup/InformatiCup2018/master/Eingabedaten/Benzinpreise/" + id +".csv");
		prices.clean(lastTime.clone(), knowPrice.clone());
		
		List<Integer> durchschnitte = new ArrayList<>();
		
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

					durchschnitte.add(temp / 3);
				}
			}
			
			currentTime.setDay(currentTime.getDay() + 1);
		}
		
		Logger.log("durchschnitte > " + durchschnitte);
		
		if(!durchschnitte.isEmpty())
		{
			for(Integer value : durchschnitte)
			{
				result += value;
			}
			
			result /= durchschnitte.size();
		}
		
		Logger.log("result > " + result);
		
		return result;
	}
}
