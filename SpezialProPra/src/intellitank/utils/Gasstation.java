package intellitank.utils;

import java.sql.Date;

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
				exception.printStackTrace();
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
	
	public int getId()
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
