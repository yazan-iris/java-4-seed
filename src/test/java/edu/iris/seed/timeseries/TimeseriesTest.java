package edu.iris.seed.timeseries;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import edu.iris.seed.io.SeedFileUtils;
import edu.iris.timeseries.Timeseries;

public class TimeseriesTest {

	@Test
	void read(@TempDir Path tempDir) throws Exception {

		List<Timeseries> l = SeedFileUtils.toTimeseries(
				new File(getClass().getClassLoader().getResource("mseed/ADK.IU.2020.001").getFile()), false);

		for(Timeseries ts:l) {
			System.out.println(ts.getTotalNumberOfSamples());
			System.out.println(ts.getActualNumberOfSamples()+"   "+ts.getTotalNumberOfSamples());
		}
	}
}
