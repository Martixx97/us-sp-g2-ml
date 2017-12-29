package intellitank;

public class Logger
{
	public static boolean debug = false;
	
	public static void log(String msg)
	{
		if(debug) System.out.println(msg);
	}
	
	public static void error(String error)
	{
		if(debug) System.err.println(error);
	}
}
