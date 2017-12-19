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
	
	public void clean(Timestamp currentTime, Timestamp lastTime)
	{
		for(Timestamp timestamp : values.keySet())
		{
			timestamp.clean();
		}
		
		Integer currentPrice = getPriceAt(currentTime);
		
		currentTime.clean();
		lastTime.clean();
		
		currentTime.setDay(currentTime.getDay() - 1);
		
		LinkedHashMap<Timestamp, Integer> newValues = new LinkedHashMap<>();
		
//		System.out.println("old > " + values);
//		System.out.println("---------------------------------------------------------------------------------");
		
		while(currentTime.compare(lastTime) > 0)
		{
			currentTime = new Timestamp(currentTime.getYear(), currentTime.getMonth(), currentTime.getDay(), currentTime.getHour() + 1, currentTime.getMinute(), currentTime.getSecond(), "+02");
			
			if(currentPrice != null) newValues.put(currentTime, currentPrice);
//			System.out.println("new put > " + currentTime + ", " + currentPrice);
			
			if(containsKey(currentTime))
			{
				currentPrice = getPriceAt(currentTime);
//				System.out.println("new price > " + currentPrice);
			}
		}
		
		values = newValues;

//		System.out.println("---------------------------------------------------------------------------------");
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
			if(time.compare(timestamp)  == 0) return true;
		}
		
		return false;
	}
	
	public Integer getPriceAt(Timestamp timestamp)
	{
		if(containsKey(timestamp))
		{
			for(Timestamp time : values.keySet())
			{
				if(time.equals(timestamp)) return values.get(time);
			}
		}
		
		return null;
	}
	
	public HashMap<Timestamp, Integer> getValues()
	{
		return values;
	}
}
