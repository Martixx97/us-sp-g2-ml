package intellitank.utils;

public class Address
{
	String street;
	int housenumber;
	int zip;
	String city;
	
	float latitude;
	float longitude;
	
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
		String street = "";
		int housenumber = 0;
		int zip = 0;
		String city = "";
		
		float latitude = 0f;
		float longitude = 0f;
		
		if(!data.isEmpty())
		{
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
				exception.printStackTrace();
			}
		}
		
		return new Address(street, housenumber, zip, city, latitude, longitude);
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