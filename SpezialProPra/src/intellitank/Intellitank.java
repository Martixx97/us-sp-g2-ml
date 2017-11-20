package intellitank;

import java.sql.Date;

import intellitank.main.Timestamp;

public class Intellitank
{
	public static void main(String[] args)
	{
		Timestamp t1 = new Timestamp(2017, 11, 20, 15, 6, 48, "+2");
		
		Timestamp t2 = new Timestamp(2013, 9, 20, 15, 2, 48, "+drölf");
		Timestamp t3 = Timestamp.read("2017-11-20 15:06:48+2");
		
		System.out.println(t1.compare(t2));
		System.out.println(t1.compare(t3));
	}
}
