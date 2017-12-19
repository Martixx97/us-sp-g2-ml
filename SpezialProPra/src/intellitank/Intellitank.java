package intellitank;

import intellitank.main.Calculator;
import intellitank.utils.PriceList;
import intellitank.utils.Timestamp;

public class Intellitank
{
	public static void main(String[] args)
	{
//		System.out.println(Calculator.getPriceAt("2015-02-10 12:18:01+01;2015-02-15 21:18:01+01;24"));
		
		System.out.println("start");
		
		Calculator.forecastPrice(24, Timestamp.fromString("2015-02-10 12:18:01+01"), Timestamp.fromString("2015-02-15 21:18:01+01"));
		
		System.out.println("end");
	}
}
