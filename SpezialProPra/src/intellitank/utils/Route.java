package intellitank.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import intellitank.Intellitank;
import intellitank.main.Calculator;

public class Route
{	
	private int capacity;
	
	private double currentTank;
	private double maxDistance;
	
	private ArrayList<Timestamp> times;
	private ArrayList<RouteGasstation> stations;
	
	private int index;
	private RouteGasstation current;
	
	public Route(int capacity, LinkedHashMap<Timestamp, Integer> stops)
	{
		this.capacity = capacity;
		
		this.times = new ArrayList<>();
		this.stations = new ArrayList<>();
		
		for(Timestamp time : stops.keySet())
		{
			times.add(time);		
			stations.add(new RouteGasstation(Intellitank.gasStationList.get(stops.get(time))));
		}
		
		for(int i=0; i<stations.size(); i++)
		{
			RouteGasstation gasStation = stations.get(i);
			
			if(i > 0) gasStation.setPrevious(stations.get(i - 1));
			if(i < (stations.size() - 1)) gasStation.setNext(stations.get(i + 1));
		}
		
		this.index = 0;
		this.current = stations.get(index);
		
		this.currentTank = 0;
		this.maxDistance = capacity / 5.6 * 100;
		
		if(times.size() != stations.size()) Intellitank.logger.error("ERROR 105 | different list size in Route");
	}
	
	public LinkedHashMap<RouteGasstation, Double> getTankResults()
	{
		LinkedHashMap<RouteGasstation, Double> results = new LinkedHashMap<>();
		
		RouteGasstation curr = stations.get(0);
		
		while(curr != stations.get(stations.size() - 1))
		{
			if(distanceToEnd() <= maxDistance)
			{
				results.put(curr, getGasConsume(distanceToEnd()) - currentTank);

				currentTank -= getGasConsume(distanceToNext());
				curr = stations.get(stations.size() - 1);
			} else
			{
				results.put(curr, capacity - currentTank);
				currentTank = capacity - currentTank;

				currentTank -= getGasConsume(distanceToNext());
				curr = nextCheapest(curr);
			}
		}
		
		return results;
	}
	
	private RouteGasstation nextCheapest(RouteGasstation gasstation)
	{
		RouteGasstation cheapest = gasstation.getNext();
		
		while(gasstation.hasNext())
		{
			if(Calculator.forecastPrice(gasstation.getNext().getID(), times.get(index + 1), times.get(index + 1)) <= Calculator.forecastPrice(gasstation.getID(), times.get(index), times.get(index))) cheapest = gasstation;
			
			gasstation = gasstation.getNext();
		}
		
		return cheapest;
	}
	
	private double getGasConsume(double distance)
	{
		return distance * (5.6 / 100);
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
	
	public double distanceToEnd()
	{
		float latA = current.getAddress().getLatitude();
		float lonA = current.getAddress().getLongitude();
			
		float latB = stations.get(stations.size() - 1).getAddress().getLatitude();
		float lonB = stations.get(stations.size() - 1).getAddress().getLongitude();
			
		return 6378.388 * Math.acos(Math.sin(latA) * Math.sin(latB) + Math.cos(latA) * Math.cos(latB) * Math.cos(lonB - lonA));
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
			
			String[] line = data.split("\\|");
			
			try
			{
				capacity = Integer.valueOf(line[0]);
			} catch (NumberFormatException exception)
			{
				Intellitank.logger.throwNumberFormat(exception);
			}
			
			for(int i=1; i<line.length; i++)
			{
				try
				{
					stops.put(Timestamp.fromString(line[i].split(";")[0]), Integer.valueOf(line[i].split(";")[1]));
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
