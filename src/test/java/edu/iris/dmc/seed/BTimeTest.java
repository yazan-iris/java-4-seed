package edu.iris.dmc.seed;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;


public class BTimeTest {

	@Test
	public void constructor() throws Exception {
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

	@Test
	public void valueOf() throws Exception {
		BTime time = BTime.valueOf(1990, 29, 8, 36, 36, 2222);
		assertEquals("1990,029,08:36:36.2222", time.toSeedString());
	}

	@Test
	public void zonedDateTime() throws Exception {
		BTime time = BTime.valueOf(ZonedDateTime.parse("1990-01-29T08:36:36.2222Z[UTC]"));
		assertEquals("1990,029,08:36:36.222200000", time.toSeedString());
		
		ZonedDateTime.parse("2012-06-30T12:30:40+01:00[+01:00]");
	}
}
