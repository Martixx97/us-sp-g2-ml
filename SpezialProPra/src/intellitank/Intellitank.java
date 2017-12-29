package intellitank;

import intellitank.main.Calculator;
import intellitank.utils.PriceList;
import intellitank.utils.Timestamp;

public class Intellitank
{
	public static void main(String[] args)
	{
		for(String arg : args)
		{
			if(arg.startsWith("debug"))
			{
				Logger.debug = Boolean.valueOf(arg.split("=")[1]);
			} else
			{
				System.err.println("ERROR 104 | unknown argument > " + arg);
				System.err.println("... exiting program");				
				System.exit(1);
			}
		}
		
		/***********************************************************************************************************************/
		
		Logger.log("start");

		Calculator.forecastPrice(24, Timestamp.fromString("2015-02-10 12:18:01+01"), Timestamp.fromString("2015-02-15 21:18:01+01"));
		
		Logger.log("end");
	}
}
