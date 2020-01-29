package edu.iris.timeseries;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.iris.seed.BTime;

public class Util {

	private static Logger logger = Logger.getLogger("edu.iris.dmc.ws.seed.Util");

	public static long toLongTime(BTime bTime, int activityFlags, int timeCorrection, int microseconds) {
		Timestamp ts = toTime(bTime, activityFlags, timeCorrection, microseconds);
		return ts.getTime();
	}

	public static Timestamp toTime(BTime bTime, int activityFlags, int timeCorrection, int microseconds) {

		long totalNanoSeconds = (microseconds * 1000) + (bTime.getTenthMilliSecond() * 100000);
		Timestamp timeStamp = null;

		// If time correction is not applied, apply time correction
		if (activityFlags == 0) {
			long timeCorrectionInNano = timeCorrection * 100000l;
			totalNanoSeconds = totalNanoSeconds + timeCorrectionInNano;
		}

		int overflow = (int) (totalNanoSeconds / 1000000000);
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.set(Calendar.YEAR, bTime.getYear());
		cal.set(Calendar.DAY_OF_YEAR, bTime.getDayOfYear());
		cal.set(Calendar.HOUR_OF_DAY, bTime.getHour());
		cal.set(Calendar.MINUTE, bTime.getMinute());

		if (overflow == 0) {
			cal.set(Calendar.SECOND, bTime.getSecond());
			timeStamp = new Timestamp(cal.getTime().getTime());
		} else {
			cal.set(Calendar.SECOND, bTime.getSecond() + overflow);
			timeStamp = new Timestamp(cal.getTime().getTime());
		}
		timeStamp.setNanos(0);

		int remainderNanos = (int) (totalNanoSeconds % 1000000000);

		if (remainderNanos == 0) {

		} else if (remainderNanos > 0) {
			timeStamp.setNanos(remainderNanos);
		} else {
			cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) - 1);
			timeStamp = new Timestamp(cal.getTime().getTime());
			timeStamp.setNanos(1000000000 + remainderNanos);
		}
		return roundTime(timeStamp);
	}

	public static Timestamp roundTime(Timestamp timestamp) {

		int nanos = timestamp.getNanos();

		nanos = (Math.round(nanos / 1000)) * 1000;

		if (nanos == 0) {
			// do nothing
		} else if (nanos > 0 && nanos < 1000000000) {
			timestamp.setNanos(nanos);
		} else {
			timestamp.setNanos(0);
			timestamp = new Timestamp(timestamp.getTime() + 1000);
		}
		return timestamp;
	}

	public static long addSecondsToLong(long ts, double seconds) {
		return addSeconds(new Timestamp(ts), seconds).getTime();
	}

	public static Timestamp addSeconds(Timestamp ts, double seconds) {

		// clone the timestamp so that we don't change it.
		Timestamp tstampClone = (Timestamp) ts.clone();
		if (seconds == 0.0) {
			// short circuit the trivial case...
			return tstampClone;
		}
		double dtmp = Math.abs(seconds);

		// the integer part of seconds
		long isecs = (long) dtmp;

		// the fractional part of seconds
		double dfrac = dtmp - isecs;

		// the number of nano seconds
		int inano = (int) Math.round(dfrac * 1000000000);

		// the nano seconds in the tstamp
		int tnano = tstampClone.getNanos();

		// the seconds in the tstamp
		long tsecs = tstampClone.getTime() / 1000;

		long newsecs;
		int newnano;

		if (seconds > 0) {
			newnano = inano + tnano;
			newsecs = isecs + tsecs;
			if (newnano > 999999999) {
				// carry over
				newnano -= 1000000000;
				newsecs += 1;
			}
		} else {
			if (inano > tnano) {
				// barrow from
				tsecs -= 1;
				tnano += 1000000000;
			}
			newsecs = tsecs - isecs;
			newnano = tnano - inano;
		}
		tstampClone.setTime(newsecs * 1000);
		tstampClone.setNanos(newnano);
		return roundTime(tstampClone);
	}

	public static boolean isSampleRateTolerable(float a, float b) {
		return (Math.abs(1.0 - (a / b)) < 0.0001);
	}

	public static boolean isRightTimeTolerable(Timestamp end, Timestamp expected, double period) {
		long temp = end.getTime() - expected.getTime();
		if (logger.isLoggable(Level.FINER)) {
			logger.finer("Comparing: " + end + " " + expected + "  " + period + "  " + temp);
		}
		return Math.abs(temp) <= period;
	}

	public static boolean isLeftTimeTolerable(Timestamp start, Timestamp expected, double period) {
		return Math.abs(start.getTime() - expected.getTime()) <= period;
	}
}
