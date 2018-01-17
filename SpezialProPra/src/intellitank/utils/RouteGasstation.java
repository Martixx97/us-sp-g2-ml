package intellitank.utils;

public class RouteGasstation extends Gasstation
{
	private int routeID;
	
	private RouteGasstation previous;
	private RouteGasstation next;
	
	public RouteGasstation(int routeID, int id, String name, String brand, Address address)
	{
		super(id, name, brand, address);
		
		this.routeID = routeID;
		
		this.previous = null;
		this.next = null;
	}
	
	public RouteGasstation(int routeID, Gasstation station)
	{
		super(station.getID(), station.getName(), station.getBrand(), station.getAddress());
		
		this.routeID = routeID;
		
		this.previous = null;
		this.next = null;
	}
	
	public int getRouteID()
	{
		return routeID;
	}

	public RouteGasstation getPrevious()
	{
		return previous;
	}

	public void setPrevious(RouteGasstation previous)
	{
		this.previous = previous;
	}
	
	public boolean hasPrevious()
	{
		return previous != null;
	}

	public RouteGasstation getNext()
	{
		return next;
	}

	public void setNext(RouteGasstation next)
	{
		this.next = next;
	}
	
	public boolean hasNext()
	{
		return next != null;
	}
}
