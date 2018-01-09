package intellitank.main;

public class DataStorage
{
	private static final String GITHUB_REPO = "https://raw.githubusercontent.com/InformatiCup/InformatiCup2018/master";
	
	private static final String STATION_LIST = GITHUB_REPO + "/Eingabedaten/Tankstellen.csv";
	private static final String STATION_PRICE_LIST = GITHUB_REPO + "/Eingabedaten/Benzinpreise/~ID~.csv";
	
	public static String getStationList()
	{
		return STATION_LIST;
	}
	
	public static String getStationPriceList(int id)
	{
		return STATION_PRICE_LIST.replace("~ID~", String.valueOf(id));
	}
}
