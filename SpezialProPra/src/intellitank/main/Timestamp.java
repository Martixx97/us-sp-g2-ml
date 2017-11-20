package intellitank.main;

public class Timestamp
{
	int year;
	int month;
	int day;
	
	int hour;
	int minute;
	int second;
	
	String timezone;

	public Timestamp(int year, int month, int day, int hour, int minute, int second, String timezone)
	{
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.timezone = timezone;
	}
	
	@Override
	public String toString()
	{
		return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second + timezone;
	}

	public static Timestamp read(String date)
	{
		int year = 0;
		int month = 0;
		int day = 0;
		
		int hour = 0;
		int minute = 0;
		int second = 0;
		
		String timezone = "";
		
		if(!date.isEmpty())
		{
			String ymd = date.split(" ")[0];
			String hmst = date.split(" ")[1];

			try
			{
				year = Integer.valueOf(ymd.split("-")[0]);
				month = Integer.valueOf(ymd.split("-")[1]);
				day = Integer.valueOf(ymd.split("-")[2]);
				
				hour = Integer.valueOf(hmst.split(":")[0]);
				minute = Integer.valueOf(hmst.split(":")[1]);
				
				String st = hmst.split(":")[2];
				System.out.println(st);
				
				if(st.split("+").length > 0)
				{
					second = Integer.valueOf(st.split("+")[0]);
					timezone = "+" + st.split("+")[1];
				} else if(st.split("-").length > 0)
				{
					second = Integer.valueOf(st.split("-")[0]);
					timezone = "-" + st.split("-")[1];
				}
			} catch (NumberFormatException exception)
			{
				exception.printStackTrace();
			}
		}
		
		return new Timestamp(year, month, day, hour, minute, second, timezone);
	}
	
	public boolean compare(Timestamp time)
	{
		return time.year == this.year && time.month == this.month && time.day == this.day && time.hour == this.hour && time.minute == this.minute && time.second == this.second && time.timezone == this.timezone;
	}
	
	public int getYear()
	{
		return year;
	}

	public void setYear(int year)
	{
		this.year = year;
	}

	public int getMonth()
	{
		return month;
	}

	public void setMonth(int month)
	{
		this.month = month;
	}

	public int getDay()
	{
		return day;
	}

	public void setDay(int day)
	{
		this.day = day;
	}

	public int getHour()
	{
		return hour;
	}

	public void setHour(int hour)
	{
		this.hour = hour;
	}

	public int getMinute()
	{
		return minute;
	}

	public void setMinute(int minute)
	{
		this.minute = minute;
	}

	public int getSecond()
	{
		return second;
	}

	public void setSecond(int second)
	{
		this.second = second;
	}

	public String getTimezone()
	{
		return timezone;
	}

	public void setTimezone(String timezone)
	{
		this.timezone = timezone;
	}
}
