package intellitank.main;

import java.util.HashMap;

import intellitank.utils.Timestamp;

public class TimeSeries
{
	private HashMap<Timestamp, Integer> values = new HashMap<>();
	
	public TimeSeries()
	{
		
	}
	
	public int forecast(Timestamp time)
	{
		int result = 0;
		
		// TODO //
		
		return result;
	}
	
	public boolean addValue(Timestamp time, Integer value)
	{
		if(!values.containsKey(time))
		{
			values.put(time, value);
			return true;
		}
		
		return false;
	}
	
	public Integer getValue(Timestamp time)
	{
		if(values.containsKey(time))
		{
			return values.get(time);
		} else return 0;
	}
}
