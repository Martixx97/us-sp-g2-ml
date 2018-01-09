package intellitank.utils;

import java.sql.Date;

import intellitank.Logger;
import intellitank.main.DataStorage;
import intellitank.main.Reader;

public class Gasstation
{
	int id;
	
	String name;
	String brand;
	
	Address address;
	
	public Gasstation(int id, String name, String brand, Address address)
	{
		this.id = id;
		
		this.name = name;
		this.brand = brand;
		
		this.address = address;
	}
	
	@Override
	public String toString()
	{
		return id + ";" + name + ";" + brand + ";" + address.toString();
	}
	
	public static Gasstation fromString(String data)
	{
		int id = 0;
		
		String name = "";
		String brand = "";
		
		Address address = null;
		
		if(!data.isEmpty())
		{
			try
			{
				id = Integer.valueOf(data.split(";")[0]);
			} catch (NumberFormatException exception)
			{
				Logger.error("ERROR 102 | " + exception.toString());
			}
			
			name = data.split(";")[1];
			brand = data.split(";")[2];
			
			String addressData = "";
			
			for(int i=3; i<data.split(";").length; i++)
			{
				if(addressData.isEmpty()) addressData = data.split(";")[i];
				else addressData += ";" + data.split(";")[i];
			}
			
			address = Address.fromString(addressData);
		}
		
		return new Gasstation(id, name, brand, address);
	}
	
	public static Gasstation fromID(int id)
	{
		if(id != 0)
		{
			String data = Reader.readURL(DataStorage.getStationList());
			
			String[] split = data.split("\\|");
			
			for(int i=0; i<split.length; i++)
			{
				Gasstation station = Gasstation.fromString(split[i]);
				
				if(station.getID() == id) return station;
			}
		} else
		{
			Logger.error("ERROR 106 | gas station id cannot be 0");
		}
		
		return null;
	}
	
	public int getID()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getBrand()
	{
		return brand;
	}
	
	public void setBrand(String brand)
	{
		this.brand = brand;
	}
	
	public Address getAddress()
	{
		return address;
	}
	
	public void setAddress(Address address)
	{
		this.address = address;
	}
}
