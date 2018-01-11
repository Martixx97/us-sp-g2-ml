package intellitank.utils;

import intellitank.Intellitank;
import intellitank.Logger;
import intellitank.main.Reader;

public class Address
{
	private String street, city;
	private int housenumber, zip;
	
	private float latitude, longitude;
	
	public Address(String street, int housenumber, int zip, String city, float latitude, float longitude)
	{
		this.street = street;
		this.housenumber = housenumber;
		this.zip = zip;
		this.city = city;
		
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	@Override
	public String toString()
	{
		return street + ";" + housenumber + ";" + zip + ";" + city + ";" + latitude + ";" + longitude;
	}
	
	public static Address fromString(String data)
	{
		if(!data.isEmpty())
		{
			String street = "", city = "";
			int housenumber = 0, zip = 0;
			
			float latitude = 0f, longitude = 0f;
			try
			{
				street = data.split(";")[0];
				housenumber = Integer.valueOf(data.split(";")[1]);
				zip = Integer.valueOf(data.split(";")[2]);
				city = data.split(";")[3];
				
				latitude = Float.valueOf(data.split(";")[4]);
				longitude = Float.valueOf(data.split(";")[5]);
			} catch (NumberFormatException exception)
			{
				Intellitank.logger.throwNumberFormat(exception);
			}
			
			return new Address(street, housenumber, zip, city, latitude, longitude);
		} else
		{
			Intellitank.logger.throwInvalidDataInput("Address.fromString(...)");
			return null;
		}
	}

	public String getStreet()
	{
		return street;
	}

	public void setStreet(String street)
	{
		this.street = street;
	}

	public int getHousenumber()
	{
		return housenumber;
	}

	public void setHousenumber(int housenumber)
	{
		this.housenumber = housenumber;
	}

	public int getZip()
	{
		return zip;
	}

	public void setZip(int zip)
	{
		this.zip = zip;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public float getLatitude()
	{
		return latitude;
	}

	public void setLatitude(float latitude)
	{
		this.latitude = latitude;
	}

	public float getLongitude()
	{
		return longitude;
	}

	public void setLongitude(float longitude)
	{
		this.longitude = longitude;
	}	
}
