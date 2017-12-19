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
		
//		System.out.println("old > " + values);
		System.out.println("---------------------------------------------------------------------------------");
		
		while(currentTime.compare(lastTime) > 0)
		{
			currentTime = new Timestamp(currentTime.getYear(), currentTime.getMonth(), currentTime.getDay(), currentTime.getHour() + 1, currentTime.getMinute(), currentTime.getSecond(), currentTime.getTimezone());
			
			Timestamp test = Timestamp.fromString("2014-6-9 3:0:0+02");
			
			if(test.equals(currentTime))
			{
				System.out.println("[test] " + test + " ??? " + values.containsKey(test));
				System.out.println("[curr] " + currentTime + " ??? " + values.containsKey(currentTime));
			}
			
			if(!values.containsKey(currentTime))
			{
				newValues.put(currentTime, currentPrice);
//				System.out.println("new put > " + currentTime);
			} else
			{
				currentPrice = values.get(currentTime);
				System.out.println("new price > " + currentPrice);
			}
		}
		
		values = newValues;

		System.out.println("---------------------------------------------------------------------------------");
//		System.out.println("new > " + values);
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
	
	public boolean containsKey(Timestamp timestamp)
	{
		for(Timestamp time : values.keySet())
		{
			if(time.compare(timestamp) == 0) return true;
		}
		
		return false;
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
