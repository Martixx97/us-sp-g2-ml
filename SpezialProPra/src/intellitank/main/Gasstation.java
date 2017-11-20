package intellitank.main;

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
	
	public void fromString(String data)
	{
		if(!data.isEmpty())
		{
			// TODO
		}
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
