package edu.iris.seed;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class BTime implements Comparable<BTime> {

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
		return b;
	}

	public static BTime valueOf(int year, int dayOfYear) {
		return new BTime(year, dayOfYear, 0, 0, 0, 0);
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

	public static BTime valueOf(String source) throws SeedException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date;
		try {
			date = simpleDateFormat.parse(source);
			return valueOf(date);
		} catch (ParseException e) {
			throw new SeedException(e);
		}

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

	public static XMLGregorianCalendar toCalendar(BTime bTime) throws DatatypeConfigurationException {
		GregorianCalendar c = new GregorianCalendar();
		c.setTimeZone(TimeZone.getTimeZone("GMT"));
		c.set(Calendar.YEAR, bTime.getYear());

		c.set(Calendar.DAY_OF_YEAR, bTime.getDayOfYear());
		c.set(Calendar.HOUR_OF_DAY, bTime.getHour());
		c.set(Calendar.MINUTE, bTime.getMinute());
		c.set(Calendar.SECOND, bTime.getSecond());
		c.set(Calendar.MILLISECOND, bTime.getTenthMilliSecond());

		return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
	}

	public static BTime toBTime(XMLGregorianCalendar xmlCal) throws DatatypeConfigurationException {
		if (xmlCal == null) {
			return null;
		}
		if (xmlCal.getTimezone() == DatatypeConstants.FIELD_UNDEFINED) {
			xmlCal.setTimezone(0);
		}
		// xmlCal.normalize();
		GregorianCalendar original = xmlCal.toGregorianCalendar();

		ZonedDateTime zdt = original.toZonedDateTime();
		ZonedDateTime converted = zdt.withZoneSameInstant(ZoneId.of("GMT"));

		return new BTime(converted.getYear(), converted.getDayOfYear(), converted.getHour(), converted.getMinute(),
				converted.getSecond(), converted.get(ChronoField.MILLI_OF_SECOND));
	}

	public ZonedDateTime toZonedDateTime() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy,DDD,HH:mm:ss.nZ");
		String date = this.toSeedString() + "00000+0000";

		return ZonedDateTime.parse(date, dateTimeFormatter);
	}

	public String toString(String pattern) {
		ZonedDateTime z = toZonedDateTime();
		return toString(z, pattern);
	}

	public static String toString(ZonedDateTime dateTime, String pattern) {
		if (dateTime == null) {
			return null;
		}

		DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.of("UTC"));
		return format.format(dateTime);

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

	@Override
	public int compareTo(BTime o) {
		return Comparator.comparing(BTime::getYear).thenComparing(BTime::getDayOfYear).thenComparing(BTime::getHour)
				.thenComparing(BTime::getMinute).thenComparing(BTime::getSecond)
				.thenComparing(BTime::getTenthMilliSecond).compare(this, o);
	}

}
