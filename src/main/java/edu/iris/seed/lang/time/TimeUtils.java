package edu.iris.seed.lang.time;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import edu.iris.seed.BTime;

public class TimeUtils {

	public static ZonedDateTime toZonedDateTime(String source) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[z]").withZone(ZoneId.of("UTC"));
		return ZonedDateTime.parse(source, format);
	}
	public static XMLGregorianCalendar toCalendar(Date date) throws DatatypeConfigurationException {
		GregorianCalendar c = new GregorianCalendar();
		c.setTimeZone(TimeZone.getTimeZone("GMT"));
		c.setTime(date);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
	}

	

	public static BTime toBTime(ZonedDateTime time) {
		if (time == null) {
			return null;
		}

		return new BTime(time.getYear(), time.getDayOfYear(), time.getHour(), time.getMinute(), time.getSecond(),
				time.get(ChronoField.MILLI_OF_SECOND));
	}

	public static String toString(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		return format.format(date);
	}

	public static String toString(Date date) {
		if (date == null) {
			return "";
		}
		return toString(date, "yyyy-MM-dd'T'HH:mm:ss");
	}

	



	public static String toString(ZonedDateTime time) {
		return toString(time, "yyyy-MM-dd'T'HH:mm:ss");
	}

	public static String toString(ZonedDateTime dateTime, String pattern) {
		if (dateTime == null) {
			return null;
		}

		DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.of("UTC"));
		return format.format(dateTime);

	}

	public static ZonedDateTime now() {
		return ZonedDateTime.now(ZoneId.of("UTC"));
	}

	public static boolean isAfter(ZonedDateTime one, ZonedDateTime two) {
		if (one == null || two == null) {
			return true;
		}

		return one.isAfter(two);
	}

	public static boolean isBefore(ZonedDateTime one, ZonedDateTime two) {
		if (one == null || two == null) {
			return true;
		}
		return one.isBefore(two);
	}

	public static int compare(ZonedDateTime one, ZonedDateTime two) {
		if (two == null) {
			return 1;
		}
		int result = one.compareTo(two);
		return result;

	}
}
