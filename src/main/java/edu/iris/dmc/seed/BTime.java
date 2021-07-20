package edu.iris.dmc.seed;

import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

import edu.iris.dmc.io.SeedStringBuilder;

public class BTime {

	private static Logger LOGGER = Logger.getLogger(BTime.class.getName());
	private int dayOfYear;
	private int year;
	private int hour;
	private int minute;
	private int second;
	private int tenthMilliSecond;

	public BTime() {
	}

	public BTime(int year, int dayOfYear, int hour, int minute, int second, int tenthMilliSecond) {
		super();
		this.dayOfYear = dayOfYear;
		this.year = year;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.tenthMilliSecond = tenthMilliSecond;
	}

	public static BTime now() {
		ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

		BTime b = new BTime();
		b.setYear(now.getYear());
		b.setDayOfYear(now.getDayOfYear());
		b.setHour(now.getHour());
		b.setMinute(now.getMinute());
		b.setSecond(now.getSecond());
		b.setTenthMilliSecond(now.getNano());
		return b;
	}

	public static BTime valueOf(int year, int dayOfYear, int hour, int minute, int second, int tenthMilliSecond) {
		return new BTime(year, dayOfYear, hour, minute, second, tenthMilliSecond);
	}

	public static BTime valueOf(ZonedDateTime date) {
		if (date == null) {
			return null;
		}
		ZonedDateTime utc = date.withZoneSameInstant(ZoneOffset.UTC);
		BTime bTime = new BTime();

		bTime.setYear(utc.getYear());
		bTime.setDayOfYear(utc.getDayOfYear());
		bTime.setHour(utc.getHour());
		bTime.setMinute(utc.getMinute());
		bTime.setSecond(utc.getSecond());
		bTime.setTenthMilliSecond(utc.getNano());
		return bTime;
	}

	public static BTime valueOf(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("UTC"));
		cal.setTime(date);

		BTime time = new BTime();
		time.setYear(cal.get(Calendar.YEAR));
		time.setDayOfYear(cal.get(Calendar.DAY_OF_YEAR));
		time.setHour(cal.get(Calendar.HOUR_OF_DAY));
		time.setMinute(cal.get(Calendar.MINUTE));
		time.setSecond(cal.get(Calendar.SECOND));
		time.setTenthMilliSecond(cal.get(Calendar.MILLISECOND));
		return time;
	}

	public static BTime valueOf(byte[] bytes) throws SeedException {

		if (bytes == null) {
			throw new IllegalArgumentException("Cannot build BTime object from NULL.");
		}

		try {
			String btime = new String(bytes, StandardCharsets.US_ASCII).trim();
			String[] array = btime.split(",[ ]*");
			if (bytes.length == 0) {
				return null;
			}
			if (array[0].length() < 4) {
				throw new SeedException("Invalid time format: [" + btime + "]");
			}
			BTime time = new BTime();
			time.setYear(Integer.parseInt(array[0]));

			if (array.length < 2) {
				return time;
			}

			time.setDayOfYear(Integer.parseInt(array[1]));
			if (array.length < 3) {
				return time;
			}
			time.setHour(Integer.parseInt(new String(bytes, 9, 2, StandardCharsets.US_ASCII)));
			if (bytes.length < 14) {
				return time;
			}
			time.setMinute(Integer.parseInt(new String(bytes, 12, 2, StandardCharsets.US_ASCII)));
			if (bytes.length < 17) {
				return time;
			}
			time.setSecond(Integer.parseInt(new String(bytes, 15, 2, StandardCharsets.US_ASCII)));
			if (bytes.length < 22) {
				return time;
			}
			time.setTenthMilliSecond(Integer.parseInt(new String(bytes, 18, 4, StandardCharsets.US_ASCII)));
			return time;
		} catch (NumberFormatException e) {
			throw new SeedException("invalid time " + new String(bytes), e);
		}

	}

	public int getDayOfYear() {
		return dayOfYear;
	}

	public void setDayOfYear(int dayOfYear) {
		this.dayOfYear = dayOfYear;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public int getTenthMilliSecond() {
		return tenthMilliSecond;
	}

	public void setTenthMilliSecond(int tenthMilliSecond) {
		this.tenthMilliSecond = tenthMilliSecond;
	}

	@Override
	public String toString() {
		return this.year + "," + this.dayOfYear + "," + this.hour + ":" + this.minute + ":" + this.second + "."
				+ this.tenthMilliSecond;
	}

	public String toSeedString() {
		SeedStringBuilder stringBuilder = new SeedStringBuilder();
		stringBuilder.append(this);
		return stringBuilder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dayOfYear;
		result = prime * result + hour;
		result = prime * result + minute;
		result = prime * result + second;
		result = prime * result + tenthMilliSecond;
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BTime other = (BTime) obj;
		if (dayOfYear != other.dayOfYear)
			return false;
		if (hour != other.hour)
			return false;
		if (minute != other.minute)
			return false;
		if (second != other.second)
			return false;
		if (tenthMilliSecond != other.tenthMilliSecond)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

}
