package edu.iris.seed.timeseries;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.iris.seed.io.SeedFileUtils;
import edu.iris.timeseries.Timeseries;

public class TimeseriesTest {

	@Test
	void read() throws Exception {
		Timeseries t1 = SeedFileUtils.toTimeseries(
				new File(getClass().getClassLoader().getResource("mseed/ADK.IU.2020.001").getFile()), false).get(0);

		Timeseries t2 = SeedFileUtils.toTimeseries(
				new File(getClass().getClassLoader().getResource("mseed/ADK.IU.2020.001").getFile()), true).get(0);

	}
}
