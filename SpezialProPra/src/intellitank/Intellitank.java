package intellitank;

import intellitank.main.Calculator;
import intellitank.utils.PriceList;
import intellitank.utils.Timestamp;

public class Intellitank
{
	public static Logger logger;
	
	public static void main(String[] args)
	{
		logger = new Logger(null);
		
		for(String arg : args)
		{
			if(arg.equalsIgnoreCase("debug"))
			{
				logger.setDebug(true);
			} else
			{
				System.err.println("ERROR 104 | unknown argument > " + arg);
				System.err.println("... exiting program");				
				System.exit(1);
			}
		}
		
		/***********************************************************************************************************************/
		
		logger.log("start");

		Calculator.forecastPrice(24, Timestamp.fromString("2015-02-10 12:18:01+01"), Timestamp.fromString("2015-02-15 21:18:01+01"));
		
		// 24, Timestamp.fromString("2015-02-10 12:18:01+01"), Timestamp.fromString("2015-02-15 21:18:01+01")
		// 46, Timestamp.fromString("2016-03-22 10:42:01+01"), Timestamp.fromString("2016-03-22 10:43:01+01")
		// 14038, Timestamp.fromString("2016-01-27 03:06:01+01"), Timestamp.fromString("2016-02-26 18:06:01+01")
		
		logger.log("end");
	}
}
