package intellitank.utils;

import java.util.Date;

import intellitank.Intellitank;
import intellitank.Logger;

public class Timestamp
{
	private int year, month, day;	
	private int hour, minute, second;
	
	private String timezone;

	public Timestamp(int year, int month, int day, int hour, int minute, int second, String timezone)
	{
		setYear(year);
		setMonth(month);
		setDay(day);
		
		setHour(hour);
		setMinute(minute);
		setSecond(second);
		
		setTimezone(timezone);
	}
	
	@Override
	public String toString()
	{
		return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second + timezone;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Timestamp)
		{
			Timestamp time = (Timestamp) obj;
			
			return this.year == time.year && this.month == time.month && this.day == time.day && this.hour == time.hour && this.minute == time.minute && this.second == time.second && this.timezone.equalsIgnoreCase(time.timezone);
		}
		
		return false;
	}

	public static Timestamp fromString(String date)
	{
		if(!date.isEmpty())
		{
			int year = 0, month = 0, day = 0;		
			int hour = 0, minute = 0, second = 0;
			
			String timezone = "";
			
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
				
				if(!st.split("\\+")[0].equalsIgnoreCase(st))
				{
					second = Integer.valueOf(st.split("\\+")[0]);
					timezone = "+" + st.split("\\+")[1];
				} else if(!st.split("-")[0].equalsIgnoreCase(st))
				{
					second = Integer.valueOf(st.split("-")[0]);
					timezone = "-" + st.split("-")[1];
				}
			} catch (NumberFormatException exception)
			{
			}
			
			return new Timestamp(year, month, day, hour, minute, second, timezone);
		} else
		{
			Intellitank.logger.throwInvalidDataInput("Timestamp.fromString(...)");
			return null;
		}
	}
	
	public Date toDate()
	{
		return new Date(year, month, day, hour, minute, second);
	}
	
	public static Timestamp fromDate(Date date)
	{
		int year = date.getYear(), month = date.getMonth(), day = date.getDate();		
		int hour = date.getHours(), minute = date.getMinutes(), second = date.getSeconds();
		
		String timezone = String.valueOf(date.getTimezoneOffset());
		
		return new Timestamp(year, month, day, hour, minute, second, timezone);
	}
	
	public Timestamp clone()
	{
		return new Timestamp(this.year, this.month, this.day, this.hour, this.minute, this.second, this.timezone);
	}
	
	public void clean()
	{
		setMinute(0);
		setSecond(0);
		setTimezone("+02");
	}
	
	/**
	 * @return -1, wenn time früher (time vor this). 0, wenn gleich. 1, wenn time später (time nach this).
	 */
	public int compare(Timestamp time)
	{
		Date date_time = new Date(time.year, time.month - 1, time.day, time.hour, time.minute, time.second);
		Date date_this = new Date(this.year, this.month - 1, this.day, this.hour, this.minute, this.second);
		
		return date_time.compareTo(date_this);
	}
	
	public int getYear()
	{
		return year;
	}

	public void setYear(int year)
	{
		this.year = year;
		
		if(year < 1990 || year > 2030) Intellitank.logger.warning("unrealistic year was set in a timestamp");
	}

	public int getMonth()
	{
		return month;
	}

	public void setMonth(int month)
	{
		this.month = month;
		
		if(this.month > 12)
		{
			this.month = 1;
			this.year += 1;
		} else if(this.month < 1)
		{
			this.month += 12;
			this.year -= 1;
		}
	}

	public int getDay()
	{
		return day;
	}

	public void setDay(int day)
	{
		this.day = day;
		
		if(this.day > 30)
		{
			this.day = 1;
			setMonth(getMonth() + 1);
		} else if(this.day < 1)
		{
			this.day += 30;
			setMonth(getMonth() - 1);
		}
	}

	public int getHour()
	{
		return hour;
	}

	public void setHour(int hour)
	{
		this.hour = hour;
		
		if(this.hour > 23)
		{
			this.hour = 0;
			setDay(getDay() + 1);
		} else if(this.hour < 1)
		{
			this.hour += 23;
			setDay(getDay() - 1);
		}
	}

	public int getMinute()
	{
		return minute;
	}

	public void setMinute(int minute)
	{
		this.minute = minute;
		
		if(this.minute > 60)
		{
			this.minute = 0;
			setHour(getHour() + 1);
		}
	}

	public int getSecond()
	{
		return second;
	}

	public void setSecond(int second)
	{
		this.second = second;
		
		if(this.second > 60)
		{
			this.second = 0;
			setMinute(getMinute() + 1);
		}
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
