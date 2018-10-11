package edu.iris.dmc.seed;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BTimeTest {

	@Test
	public void t1() throws Exception{
		BTime time = new BTime();
		time.setYear(1990);
		time.setDayOfYear(29);
		time.setHour(8);
		time.setMinute(36);
		time.setSecond(36);
		time.setTenthMilliSecond(2222);
		assertEquals("1990,029,08:36:36.2222", time.toSeedString());
		time.setTenthMilliSecond(2);
		assertEquals("1990,029,08:36:36.0002", time.toSeedString());

	}
}
