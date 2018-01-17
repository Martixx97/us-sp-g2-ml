package intellitank.utils;

import java.util.ArrayList;
import java.util.Comparator;
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
	private ArrayList<RouteGasstation> sortedCheapList;
	
	public Route(int maxTank, LinkedHashMap<Timestamp, Integer> stops)
	{
		this.maxTank = maxTank;		
		this.currentTank = 0;
		
		this.maxDistance = maxTank / 5.6 * 100;
		
		this.times = new LinkedHashMap<RouteGasstation, Timestamp>();
		this.stations = new ArrayList<>();
		
		int i = 0;
		
		for(Timestamp time : stops.keySet())
		{
			RouteGasstation gasStation = new RouteGasstation(i++, Intellitank.gasStationList.get(stops.get(time)));
			
			times.put(gasStation, time);		
			stations.add(gasStation);
		}
		
		for(i=0; i<stations.size(); i++)
		{
			RouteGasstation gasStation = stations.get(i);
			
			if(i > 0) gasStation.setPrevious(stations.get(i - 1));
			if(i < (stations.size() - 1)) gasStation.setNext(stations.get(i + 1));
		}
		
		this.sortedCheapList = getSortedCheapList();
		
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
		sortedCheapList = getSortedCheapList();
		
		RouteGasstation curr = stations.get(0);
		
		sortedCheapList.remove(curr);
		
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
				
				currentTank -= getGasConsume(distanceBetween(curr, cheapest));
				curr = cheapest;
			}
		}
		
		return results;
	}
	
	private RouteGasstation nextCheapest(RouteGasstation gasstation)
	{
		RouteGasstation cheapest = null;
		
		for(RouteGasstation cheapStation : sortedCheapList)
		{
			if(cheapStation.getRouteID() < gasstation.getRouteID())
			{
				sortedCheapList.remove(cheapStation);
				continue;
			}
			System.out.println(distanceBetween(gasstation, cheapStation) + " > " + maxDistance);
			if(distanceBetween(gasstation, cheapStation) > maxDistance) continue;
				
			cheapest = cheapStation; System.out.println("cheapest: " + cheapest.getID());
			sortedCheapList.remove(cheapest);
			break;
		}
		
		return cheapest;
	}
	
	private ArrayList<RouteGasstation> getSortedCheapList()
	{
		LinkedHashMap<RouteGasstation, Integer> stationPrices = new LinkedHashMap<>();
		
		for(RouteGasstation gasStation : stations)
		{
			stationPrices.put(gasStation, Calculator.forecastPrice(gasStation.getID(), times.get(gasStation), times.get(gasStation)));
		}
		
		ArrayList<RouteGasstation> results = new ArrayList<>();
		
		for(RouteGasstation gasStation : stationPrices.keySet())
		{
			results.add(gasStation);
		}
		
		results.sort(new Comparator<RouteGasstation>()
		{
			@Override
			public int compare(RouteGasstation o1, RouteGasstation o2)
			{
				return stationPrices.get(o1) - stationPrices.get(o2);
			}
		});
		
		return results;
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
