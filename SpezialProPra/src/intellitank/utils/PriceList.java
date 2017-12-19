package intellitank.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;

import intellitank.main.Reader;

public class PriceList
{
	private LinkedHashMap<Timestamp, Integer> values;
	
	public PriceList(LinkedHashMap<Timestamp, Integer> map)
	{
		values = new LinkedHashMap<>();
		values.putAll(map);
	}
	
	public void clean()
	{
		for(Timestamp timestamp : values.keySet())
		{
			timestamp.setMinute(0);
			timestamp.setSecond(0);
		}
		
		Timestamp currentTime = (Timestamp) values.keySet().toArray()[0];
		Integer currentPrice = (Integer) values.values().toArray()[0];
		
		Timestamp lastTime = (Timestamp) values.keySet().toArray()[values.keySet().toArray().length-1];
		
		LinkedHashMap<Timestamp, Integer> newValues = new LinkedHashMap<>();
		System.out.println("old > " + values);
		while(currentTime.compare(lastTime) > 0)
		{
			currentTime.setHour(currentTime.getHour() + 1);
			
			Timestamp test = Timestamp.fromString("2017-9-21 22:0:0+02");
			
			if(test.compare(currentTime) == 0)
			{
				System.out.println(test + " ??? " + values.containsKey(test));
				System.out.println("[" + values.get(currentTime) + "] " + currentTime + " ??? " + values.containsKey(currentTime));
			}
			
			if(!values.containsKey(currentTime))
			{
				System.out.println("new put > " + currentTime);
				newValues.put(currentTime, currentPrice);
			} else
			{
				currentPrice = values.get(currentTime);
			}
		}
		
//		values = newValues;
		
		System.out.println("new > " + newValues);
	}
	
	public static PriceList fromString(String url)
	{
		LinkedHashMap<Timestamp, Integer> result = new LinkedHashMap<>();
		
		String[] rawData = Reader.readURL(url).split("\\|");
		
		for(int i = 0; i < rawData.length; i++)
		{
			result.put(Timestamp.fromString(rawData[i].split(";")[0]), Integer.valueOf((rawData[i].split(";")[1])));
		}
		
		return new PriceList(result);
	}
	
	public String getPriceAt(Timestamp timestamp)
	{
		return values.containsKey(timestamp) ? values.get(timestamp).toString() : null;
	}
	
	public HashMap<Timestamp, Integer> getValues()
	{
		return values;
	}
}
