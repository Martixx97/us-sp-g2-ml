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
		
		for(Timestamp timestamp : map.keySet())
		{
			values.put(timestamp, map.get(timestamp));
		}
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
		
		int i = 0;
		
		while(currentTime.compare(lastTime) > 0);															// TODO //
		{
			System.out.println("[" + i + "] curr: " + currentTime + " | last: " + lastTime);
			currentTime.setHour(currentTime.getHour() + 1);
			
			if(!values.containsKey(currentTime))
			{
				values.put(currentTime, currentPrice);
			} else
			{
				currentPrice = values.get(currentTime);
			}
			
			i++;
		}
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
