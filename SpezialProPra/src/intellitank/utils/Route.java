package intellitank.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.function.LongConsumer;

import intellitank.Intellitank;
import intellitank.Logger;
import intellitank.main.DataStorage;

public class Route
{	
	private int capacity;
	
	private ArrayList<Timestamp> times;
	private ArrayList<RouteGasstation> stations;
	
	private int index;
	private RouteGasstation current;
	
	public Route(int capacity, LinkedHashMap<Timestamp, Integer> stops)
	{
		this.capacity = capacity;
		
		for(Timestamp time : stops.keySet())
		{
			times.add(time);
			stations.add(new RouteGasstation(Gasstation.fromID(stops.get(time))));
		}
		
		this.index = 0;
		this.current = stations.get(index);
		
		if(times.size() != stations.size()) Intellitank.logger.error("ERROR 105 | different list size in Route");
	}
	
	public double distanceToPrevious()
	{
		if(current.hasPrevious())
		{
			float latA = current.getPrevious().getAddress().getLatitude();
			float lonA = current.getPrevious().getAddress().getLongitude();
			
			float latB = current.getAddress().getLatitude();
			float lonB = current.getAddress().getLongitude();
			
			return 6378.388 * Math.acos(Math.sin(latA) * Math.sin(latB) + Math.cos(latA) * Math.cos(latB) * Math.cos(lonB - lonA));
		}
		
		return 0.0d;
	}
	
	public double distanceToNext()
	{
		if(current.hasNext())
		{
			float latA = current.getAddress().getLatitude();
			float lonA = current.getAddress().getLongitude();
			
			float latB = current.getNext().getAddress().getLatitude();
			float lonB = current.getNext().getAddress().getLongitude();
			
			return 6378.388 * Math.acos(Math.sin(latA) * Math.sin(latB) + Math.cos(latA) * Math.cos(latB) * Math.cos(lonB - lonA));
		}
		
		return 0.0d;
	}

	public int getCapacity()
	{
		return capacity;
	}

	public RouteGasstation getCurrent()
	{
		return current;
	}
	
	public void next()
	{
		if(current.hasNext())
		{
			current = current.getNext();
			index++;
		}
	}
	
	public Timestamp getCurrentTime()
	{
		return times.get(index);
	}
	
	public Timestamp getPreviousTime()
	{
		return current.hasPrevious() ? times.get(index - 1) : null;
	}
	
	public Timestamp getNextTime()
	{
		return current.hasNext() ? times.get(index + 1) : null;
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
		if(!data.isEmpty())
		{
			int capacity = 0;
			LinkedHashMap<Timestamp, Integer> stops = new LinkedHashMap<>();
			
			String[] split = data.split("\\|");
			
			try
			{
				capacity = Integer.valueOf(split[0]);
			} catch (NumberFormatException exception)
			{
				Intellitank.logger.throwNumberFormat(exception);
			}
			
			for(int i=1; i<split.length; i++)
			{
				try
				{
					stops.put(Timestamp.fromString(split[i].split(";")[0]), Integer.valueOf(split[i].split(";")[1]));
				} catch (NumberFormatException exception)
				{
					Intellitank.logger.throwNumberFormat(exception);
				}
			}
			
			return new Route(capacity, stops);
		} else
		{
			Intellitank.logger.throwInvalidDataInput("Route.fromString(...)");
			return null;
		}
	}
}
