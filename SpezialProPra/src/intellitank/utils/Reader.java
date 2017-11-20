package intellitank.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Reader
{
	public static String readFile(String path)
	{
		String result = "";

		try
		{
			File file = new File(path);
			BufferedReader breader = new BufferedReader(new FileReader(file));
			
			String inputLine;

			while((inputLine = breader.readLine()) != null)
			{
				if(result.isEmpty()) result = inputLine;
				else result += "|" + inputLine;
			}

			breader.close();
		} catch (IOException exception)
		{
			exception.printStackTrace();
			return null;
		}

		return result;
	}

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
			exception.printStackTrace();
			return null;
		}
		
		return result;
	}
}
