package intellitank.main;

import java.util.HashMap;

public class Route
{
	int capacity;
	HashMap<Timestamp, Integer> stops;
	
	public Route(int capacity, HashMap<Timestamp, Integer> stops)
	{
		this.capacity = capacity;
		this.stops = stops;
	}

	public int getCapacity()
	{
		return capacity;
	}

	public HashMap<Timestamp, Integer> getStops()
	{
		return stops;
	}
	
	@Override
	public String toString()
	{
		String stopString = "";
		
		for(Timestamp time : stops.keySet())
		{
			if(stopString.isEmpty()) stopString = time + ";" + stops.get(time);
			else stopString += "|" + time + ";" + stops.get(time);
		}
		
		return capacity + "|" + stopString;
	}
	
	public static Route fromString(String data)
	{
		int capacity = 0;
		HashMap<Timestamp, Integer> stops = new HashMap<>();
		
		String[] split = data.split("\\|");
		
		try
		{
			capacity = Integer.valueOf(split[0]);
		} catch (NumberFormatException exception)
		{
			exception.printStackTrace();
		}
		
		for(int i=1; i<split.length; i++)
		{
			try
			{
				stops.put(Timestamp.fromString(split[i].split(";")[0]), Integer.valueOf(split[i].split(";")[1]));
			} catch (NumberFormatException exception)
			{
				exception.printStackTrace();
			}
		}
		
		return new Route(capacity, stops);
	}
}
