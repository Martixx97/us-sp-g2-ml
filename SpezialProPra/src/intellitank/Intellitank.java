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
				try
				{
					Logger.debug = Boolean.valueOf(arg.split("=")[1]);
				} catch (ArrayIndexOutOfBoundsException exception)
				{
					System.err.println("ERROR 101 | " + exception.toString() + " > " + arg);
					System.err.println("... exiting program");
					System.exit(1);
				}
				
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
		
		// 24, Timestamp.fromString("2015-02-10 12:18:01+01"), Timestamp.fromString("2015-02-15 21:18:01+01")
		// 46, Timestamp.fromString("2016-03-22 10:42:01+01"), Timestamp.fromString("2016-03-22 10:43:01+01")
		// 14038, Timestamp.fromString("2016-01-27 03:06:01+01"), Timestamp.fromString("2016-02-26 18:06:01+01")
		
		Logger.log("end");
	}
}
