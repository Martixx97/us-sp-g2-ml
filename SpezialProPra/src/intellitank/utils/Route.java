package intellitank.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import intellitank.Intellitank;
import intellitank.main.Calculator;

public class Route
{	
	private double currentTank;
	private double maxTank;
	
	private double maxDistance;
	
	private LinkedHashMap<RouteGasstation, Timestamp> times;

	private ArrayList<RouteGasstation> stations;
	
	public Route(int maxTank, LinkedHashMap<Timestamp, Integer> stops)
	{
		this.maxTank = maxTank;		
		this.currentTank = 0;
		
		this.maxDistance = maxTank / 5.6 * 100;
		
		this.times = new LinkedHashMap<RouteGasstation, Timestamp>();
		this.stations = new ArrayList<>();
		
		for(Timestamp time : stops.keySet())
		{
			RouteGasstation gasStation = new RouteGasstation(Intellitank.gasStationList.get(stops.get(time)));
			
			times.put(gasStation, time);		
			stations.add(gasStation);
		}
		
		for(int i=0; i<stations.size(); i++)
		{
			RouteGasstation gasStation = stations.get(i);
			
			if(i > 0) gasStation.setPrevious(stations.get(i - 1));
			if(i < (stations.size() - 1)) gasStation.setNext(stations.get(i + 1));
		}
		
		if(times.size() != stations.size()) Intellitank.logger.error("ERROR 105 | different list size in Route");
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
		
		return maxTank + "|" + stopString;
	}
	
	public static Route fromString(String data)
	{
		if(!data.isEmpty())
		{
			int maxTank = 0;
			LinkedHashMap<Timestamp, Integer> stops = new LinkedHashMap<>();
			
			String[] line = data.split("\\|");
			
			try
			{
				maxTank = Integer.valueOf(line[0]);
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
			
			return new Route(maxTank, stops);
		} else
		{
			Intellitank.logger.throwInvalidDataInput("Route.fromString(...)");
			return null;
		}
	}
	
	public LinkedHashMap<RouteGasstation, Double> getTankResults()
	{
		LinkedHashMap<RouteGasstation, Double> results = new LinkedHashMap<>();
		
		RouteGasstation curr = stations.get(0);
		
		while(curr != stations.get(stations.size() - 1))
		{
			if(distanceToEnd(curr) <= maxDistance)
			{System.err.println("if");
				results.put(curr, getGasConsume(distanceToEnd(curr)) - currentTank);

				currentTank -= getGasConsume(distanceToEnd(curr));
				curr = stations.get(stations.size() - 1);
			} else
			{System.err.println("else");
				results.put(curr, maxTank - currentTank);
				currentTank = maxTank - currentTank;

				RouteGasstation cheapest = nextCheapest(curr);
				System.err.println(cheapest.getID());
				currentTank -= getGasConsume(distanceBetween(curr, cheapest));
				curr = cheapest;
			}
		}
		
		return results;
	}
	
	private RouteGasstation nextCheapest(RouteGasstation gasstation)
	{
		RouteGasstation currentStation = gasstation;
		RouteGasstation cheapest = currentStation.getNext();
		
		while(currentStation.hasNext())
		{
			if(currentStation.hasPrevious()) System.out.println("prev: " + currentStation.getPrevious().getID());
			System.out.println("curr: " + currentStation.getID());
			if(currentStation.hasNext()) System.out.println("next: " + currentStation.getNext().getID());
			System.out.println("-------------------------------------");
			
			int currPrice = Calculator.forecastPrice(currentStation.getID(), times.get(currentStation), times.get(currentStation));
			int nextPrice = Calculator.forecastPrice(currentStation.getNext().getID(), times.get(currentStation.getNext()), times.get(currentStation.getNext()));
			
			if(nextPrice <= currPrice) cheapest = currentStation.getNext();
			
			currentStation = currentStation.getNext();
		}
		
		return cheapest;
	}
	
	private double getGasConsume(double distance)
	{
		return distance * (5.6 / 100);
	}
	
	private double distanceBetween(RouteGasstation stationA, RouteGasstation stationB)
	{
		float latA = stationA.getAddress().getLatitude();
		float lonA = stationA.getAddress().getLongitude();
			
		float latB = stationB.getAddress().getLatitude();
		float lonB = stationB.getAddress().getLongitude();
			
		return 6378.388 * Math.acos(Math.sin(latA) * Math.sin(latB) + Math.cos(latA) * Math.cos(latB) * Math.cos(lonB - lonA));
	}
	
	private double distanceToEnd(RouteGasstation station)
	{
		return distanceBetween(station, stations.get(stations.size() - 1));
	}
}
