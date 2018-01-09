package intellitank;

public class Logger
{
	// TODO //
	// > make not static
	// > add jTextArea object for console
	// > add console output
	
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
