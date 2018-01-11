package intellitank.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import intellitank.Intellitank;
import intellitank.Logger;
import intellitank.utils.Timestamp;

public class Reader
{
	public static String readURL(String url)
	{
		String result = "";
		
		try
		{
			URL web = new URL(url);
			BufferedReader breader = new BufferedReader(new InputStreamReader(web.openStream()));

			String inputLine;
			
			while((inputLine = breader.readLine()) != null)
			{
				if(result.isEmpty()) result = inputLine;
				else result += "|" + inputLine;
			}
			
			breader.close();
		} catch (IOException exception)
		{
			Intellitank.logger.throwIO(exception);
			return null;
		}
		
		return result;
	}
}
