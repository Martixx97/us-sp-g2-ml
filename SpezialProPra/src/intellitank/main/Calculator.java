package intellitank.main;

import java.util.ArrayList;

import intellitank.utils.Timestamp;

public class Calculator
{
	// 1.	YYYY-MM-DD HH:MM:SS+HH bis zu dem Sie die Benzinpreise als bekannt annehmen und für Ihre Berechnungen verwenden dürfen
	// 2.	geforderten	Zeitpunkt für die Vorhersage
	// 2.	Tankstellen-ID für die Vorhersage
	
	// 2015-02-10 12:18:01+01	;	2015-02-15 21:18:01+01	;	24
	
	// tatsächlich: 2015-02-15 17:50:01+01;1309
	public static String getPriceAt(String data)
	{
		String result = "";
		 
		for(String line : data.split("\\|"))
		{
			String price = "";
			
			// TODO start
			
			String prices = Reader.readURL("https://raw.githubusercontent.com/InformatiCup/InformatiCup2018/master/Eingabedaten/Benzinpreise/" + line.split(";")[2] + ".csv");
			
			ArrayList<Timestamp> starts = new ArrayList<>();
			ArrayList<Timestamp> ends = new ArrayList<>();
			
			boolean bigger;

			starts.add(Timestamp.fromString(prices.split("\\|")[0].split(";")[0]));
			
			if(Integer.valueOf(prices.split("\\|")[1].split(";")[1]) > Integer.valueOf(prices.split("\\|")[0].split(";")[1])) bigger = true; 	// Tiefpunkt
			else bigger = false;							
			
			for(int i=2; i<prices.split("\\|").length-1; i++)
			{
				// außerhalb des Betrachtungsbereich?
				if(Timestamp.fromString(prices.split("\\|")[i].split(";")[0]).compare(Timestamp.fromString(line.split(";")[0])) < 0) break;
				
				if(bigger)
				{
					if(Integer.valueOf(prices.split("\\|")[i+1].split(";")[1]) < Integer.valueOf(prices.split("\\|")[i].split(";")[1]))
					{
						starts.add(Timestamp.fromString(prices.split("\\|")[i].split(";")[0]));
						bigger = false;
					}
				} else
				{
					if(Integer.valueOf(prices.split("\\|")[i+1].split(";")[1]) > Integer.valueOf(prices.split("\\|")[i].split(";")[1]))
					{
						ends.add(Timestamp.fromString(prices.split("\\|")[i].split(";")[0]));
						bigger = true;
					}
				}
			}
			
			// TODO end
			
			if(result.isEmpty()) result = price;
			else result += "\\|" + price;
		}
		
		return result;
	}
}
