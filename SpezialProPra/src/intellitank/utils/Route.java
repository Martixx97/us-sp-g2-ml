package intellitank.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.function.LongConsumer;

import intellitank.Logger;
import intellitank.main.DataStorage;

public class Route
{
	int current;
	
	int capacity;
	
	ArrayList<Timestamp> times;
	ArrayList<Integer> stations;
	
	public Route(int capacity, LinkedHashMap<Timestamp, Integer> stops)
	{
		this.capacity = capacity;
		
		for(Timestamp time : stops.keySet())
		{
			times.add(time);
			stations.add(stops.get(time));
		}
		
		current = 0;
		
		if(times.size() != stations.size()) Logger.error("ERROR 105 | different list size in Route");
	}
	
	public double distanceToNext()
	{
		if(hasNext())
		{
			Gasstation currentStation = Gasstation.fromID(getCurrentStationID());
			Gasstation nextStation = Gasstation.fromID(getNextStationID());
			
			float latCurrent = currentStation.getAddress().getLatitude();
			float lonCurrent = currentStation.getAddress().getLongitude();
			
			float latNext = nextStation.getAddress().getLatitude();
			float lonNext = nextStation.getAddress().getLongitude();
			
			return 6378.388 * Math.acos(Math.sin(latCurrent) * Math.sin(latNext) + Math.cos(latCurrent) * Math.cos(latNext) * Math.cos(lonNext - lonCurrent));
		}
		
		return 0.0d;
	}

	public int getCapacity()
	{
		return capacity;
	}

	public int getCurrent()
	{
		return current;
	}
	
	public boolean hasNext()
	{
		return times.size() >= (current + 1);
	}
	
	public void next()
	{
		if(hasNext()) current++;
	}
	
	public Timestamp getCurrentTime()
	{
		return times.get(current);
	}
	
	public Timestamp getNextTime()
	{
		return hasNext() ? times.get(current + 1) : null;
	}
	
	public int getCurrentStationID()
	{
		return stations.get(current);
	}
	
	public int getNextStationID()
	{
		return hasNext() ? stations.get(current + 1) : 0;
	}
	
	@Override
	public String toString()
	{
		String stopString = "";
		
		for(int i=0; i<times.size(); i++)
		{
			if(stopString.isEmpty()) stopString = times.get(i) + ";" + stations.get(i);
			else stopString += "|" + times.get(i) + ";" + stations.get(i);
		}
		
		return capacity + "|" + stopString;
	}
	
	public static Route fromString(String data)
	{
		int capacity = 0;
		LinkedHashMap<Timestamp, Integer> stops = new LinkedHashMap<>();
		
		String[] split = data.split("\\|");
		
		try
		{
			capacity = Integer.valueOf(split[0]);
		} catch (NumberFormatException exception)
		{
			Logger.error("ERROR 102 | " + exception.toString());
		}
		
		for(int i=1; i<split.length; i++)
		{
			try
			{
				stops.put(Timestamp.fromString(split[i].split(";")[0]), Integer.valueOf(split[i].split(";")[1]));
			} catch (NumberFormatException exception)
			{
				Logger.error("ERROR 102 | " + exception.toString());
			}
		}
		
		return new Route(capacity, stops);
	}
}
