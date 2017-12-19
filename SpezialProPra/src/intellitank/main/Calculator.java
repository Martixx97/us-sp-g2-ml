package intellitank.main;

import java.util.ArrayList;
import java.util.List;

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
		lastTime.setTimezone("+02");
		lastTime.clean();
		
		knowPrice.clean();
		
		PriceList prices = PriceList.fromString("https://raw.githubusercontent.com/InformatiCup/InformatiCup2018/master/Eingabedaten/Benzinpreise/" + id +".csv");
		prices.clean(lastTime.clone(), knowPrice.clone());
		
		int hour = forecastTime.getHour();

		List<Integer> durchschnitte = new ArrayList<>();
		
		for(int i=lastTime.getDay(); i<knowPrice.getDay(); i++)
		{
			for(int h=0; h<24; h++)
			{
				if(h == hour)
				{
					Timestamp time = new Timestamp(forecastTime.getYear(), forecastTime.getMonth(), i, h, 0, 0, "+02");

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
		}
		
		System.out.println("durchschnitte > " + durchschnitte);
		
		if(!durchschnitte.isEmpty())
		{
			for(Integer value : durchschnitte)
			{
				result += value;
			}
			
			result /= durchschnitte.size();
		}
		
		System.out.println("result > " + result);
		
		return result;
	}
}
