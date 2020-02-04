package edu.iris.seed.timeseries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import edu.iris.seed.io.SeedFileUtils;
import edu.iris.seed.io.SeedIOUtils;
import edu.iris.timeseries.Timeseries;
import edu.iris.seed.io.DataRecordIterator;

public class TimeseriesTest {

	@Test
	void read() throws Exception {

		try (InputStream is = new FileInputStream(
				new File(getClass().getClassLoader().getResource("mseed/ADK.IU.2020.001").getFile()))) {
			DataRecordIterator it = SeedIOUtils.toDataRecordIterator(is);
			

		}

		Timeseries t1 = SeedFileUtils.toTimeseries(
				new File(getClass().getClassLoader().getResource("mseed/ADK.IU.2020.001").getFile()), false).get(0);
		assertEquals(2, t1.getSegments().size());
	}
}
