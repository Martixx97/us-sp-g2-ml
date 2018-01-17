package intellitank;

import java.util.ArrayList;

import intellitank.main.DataStorage;
import intellitank.main.Reader;
import intellitank.utils.Gasstation;
import intellitank.utils.Route;

public class Intellitank
{
	public static Logger logger;
	
	public static ArrayList<Gasstation> gasStationList = new ArrayList<>();
	
	public static void main(String[] args)
	{
		System.out.println("#############################");
		System.out.println(System.currentTimeMillis());
		System.out.println("#############################");
		
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
//		1516202905337
//		
		preloadGasstations();
		
		/***********************************************************************************************************************/
		
		logger.log("starting test ...");

		Route route = Route.fromString(Reader.readURL("https://raw.githubusercontent.com/InformatiCup/InformatiCup2018/master/Eingabedaten/Fahrzeugrouten/Bertha%20Benz%20Memorial%20Route.csv"));
		System.out.println(route.getTankResults());		
		
		logger.log("... test finished");
		System.out.println("#############################");
		System.out.println(System.currentTimeMillis());
		System.out.println("#############################");
	}
	
	private static void preloadGasstations()
	{
		logger.log("starting preload...");
		
		gasStationList.add(null);
		
		String data = Reader.readURL(DataStorage.getStationList());
		
		String[] split = data.split("\\|");
		
		for(int i=0; i<split.length; i++)
		{
			gasStationList.add(Gasstation.fromString(split[i]));
		}
		
		logger.log("... finished preload");
	}
}
