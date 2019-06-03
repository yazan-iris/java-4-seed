package edu.iris.dmc.seed;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.ZonedDateTime;

import edu.iris.dmc.seed.blockette.util.BlocketteItrator;
import edu.iris.dmc.seed.builder.BlocketteBuilder;
import edu.iris.dmc.seed.control.dictionary.B031;
import edu.iris.dmc.seed.control.station.B050;
import edu.iris.dmc.seed.control.station.B051;
import edu.iris.dmc.seed.control.station.B052;
import edu.iris.dmc.seed.director.BlocketteDirector;

public class TestUtil {

	public static Volume load(File file) throws Exception {
		try (final FileInputStream inputStream = new FileInputStream(file)) {
			return load(inputStream);
		}
	}

	public static Volume load(InputStream inputStream) throws Exception {
		BlocketteDirector director = new BlocketteDirector();
		BlocketteItrator iterator = director.process(inputStream);

		Volume volume = new Volume();
		while (iterator.hasNext()) {
			Blockette blockette = iterator.next();
			volume.add(blockette);
		}
		return volume;
	}

	public static B050 createB050(String networkCode, String stationCode, ZonedDateTime start, ZonedDateTime end,
			double latitude, double longitude, double elevation) {
		B050 b = new B050();
		b.setNetworkCode(networkCode);
		b.setStationCode(stationCode);
		b.setStartTime(BTime.valueOf(start));
		b.setEndTime(BTime.valueOf(end));
		b.setLatitude(latitude);
		b.setLongitude(longitude);
		b.setElevation(elevation);
		return b;
	}

	public static B051 createB051(ZonedDateTime start, ZonedDateTime end) {
		B051 b = new B051();
		b.setStartTime(BTime.valueOf(start));
		b.setEndTime(BTime.valueOf(end));
		return b;
	}

	public static B052 createB052(ZonedDateTime start, ZonedDateTime end) {
		B052 b = new B052();
		b.setStartTime(BTime.valueOf(start));
		b.setEndTime(BTime.valueOf(end));
		return b;
	}

	public static B031 createB031(char classCode, String description, int unitsOfCommentLevel) {
		B031 b = new B031();
		b.setClassCode(classCode);
		b.setDescription(description);
		b.setUnitsOfCommentLevel(unitsOfCommentLevel);
		return b;
	}
	
	public static Blockette create(String text) throws SeedException {
		return BlocketteFactory.create(text.getBytes());
	}
}
