package intellitank.utils;

import java.util.HashMap;

import intellitank.main.Reader;

public class History
{

	public static String getPrice(int id, Timestamp timestamp)
	{
		String result = "";

		String[] bTime = Reader.readURL("https://raw.githubusercontent.com/InformatiCup/InformatiCup2018/master/Eingabedaten/Benzinpreise/" + id +".csv").split("|");
		
		HashMap<Timestamp, Integer> data = new HashMap<>();
		for (int i = 0; i < bTime.length; i++)
		{
			data.put(Timestamp.fromString(bTime[i].split(";")[0]), Integer.valueOf((bTime[i].split(";")[0])));
		}	
		
		if(data.containsKey(timestamp))
			result = String.valueOf(data.get(timestamp));
		
			
		return result;
	}
}
