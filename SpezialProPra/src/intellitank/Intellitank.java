package intellitank;

import intellitank.main.Calculator;
import intellitank.utils.PriceList;

public class Intellitank
{
	public static void main(String[] args)
	{
//		System.out.println(Calculator.getPriceAt("2015-02-10 12:18:01+01;2015-02-15 21:18:01+01;24"));
		
		System.out.println("start");
		PriceList prices = PriceList.fromString("https://raw.githubusercontent.com/InformatiCup/InformatiCup2018/master/Eingabedaten/Benzinpreise/24.csv");
		System.out.println("clean");
		prices.clean();
		System.out.println("end");
	}
}
