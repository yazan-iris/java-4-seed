package edu.iris.seed.record;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import edu.iris.seed.BTime;
import edu.iris.seed.DataBlockette;
import edu.iris.seed.SeedDataHeader;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedRecord;
import edu.iris.seed.data.B100;
import edu.iris.seed.data.B1000;
import edu.iris.seed.data.B1001;
import edu.iris.seed.data.DataSection;
import edu.iris.seed.data.EncodingFormat;
import edu.iris.seedcodec.CodecException;
import edu.iris.seedcodec.DecompressedData;
import edu.iris.timeseries.Util;

public class DecompressedDataRecord extends SeedRecord<DataBlockette> {

	private Map<Integer, DataBlockette> map = new TreeMap<>();

	private DataSection dataSection;

	public DecompressedDataRecord(SeedDataHeader header) {
		super(header);
	}

	public DecompressedData decompress(boolean reduce) throws CodecException {
		return DecompressedData.of(getEncodingFormat(), dataSection.getData(), getNumberOfSamples(), reduce, false);
	}

	public EncodingFormat getEncodingFormat() {
		B1000 b1000 = (B1000) map.get(1000);
		if (b1000 == null) {

		}
		return b1000.getEncodingFormat();
	}

	public int getNumberOfSamples() {
		SeedDataHeader header = (SeedDataHeader) this.getHeader();
		return header.getNumberOfSamples();
	}

	public long expectedNextSampleTime() {
		double d = (getNumberOfSamples() / this.getSampleRate()) * 1000;
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("UTC"));
		long durationInMiliSecond = (long) ((getNumberOfSamples() - 1) / this.getSampleRate()) * 1000;
		long startTime = this.getStartTime();
		cal.setTimeInMillis(startTime + durationInMiliSecond);
		cal.setTimeInMillis(startTime + (long) d);
		Timestamp ts = new Timestamp(cal.getTimeInMillis());
		return ts.getTime();
	}

	public long getStartTime() {
		int microseconds = 0;
		B1001 b1001 = (B1001) map.get(1001);
		if (b1001 != null) {
			microseconds = b1001.getMicroSeconds();
		}
		SeedDataHeader header = (SeedDataHeader) this.getHeader();
		return computeStartTime(header.getStart(), header.getActivityFlag(), header.getTimeCorrection(), microseconds);
	}

	private long computeStartTime(BTime bTime, int activityFlags, int timeCorrection, int microseconds) {
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
		return roundTime(timeStamp).getTime();

	}

	public long getEndTime() {
		return computeEndTime();
	}

	private long computeEndTime() {
		double durationinInSeconds = ((getNumberOfSamples() - 1) / (double) this.getSampleRate());

		return Util.addSecondsToLong(this.getStartTime(), durationinInSeconds);
	}

	private Timestamp roundTime(Timestamp timestamp) {

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

	public float getSampleRate() {
		B100 b100 = (B100) map.get(100);
		if (b100 != null) {
			return b100.getActualSampleRate();
		}
		SeedDataHeader header = (SeedDataHeader) this.getHeader();
		if (header != null) {
			return header.getSampleRateFactor();
		}
		return 0;
	}

	@Override
	public List<DataBlockette> blockettes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DataBlockette add(DataBlockette t) throws SeedException {
		if (t == null) {

		}
		if (t instanceof DataSection) {
			this.dataSection = (DataSection) t;
		}
		int type = t.getType();
		DataBlockette db = map.get(type);
		if (db == null) {
			map.put(type, t);
		} else {

		}
		return t;
	}

	@Override
	public DataBlockette get(int... type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int writeTo(OutputStream outputStream, int recordLength, int sequence) throws SeedException, IOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
