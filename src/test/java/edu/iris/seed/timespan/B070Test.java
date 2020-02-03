package edu.iris.seed.timespan;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

public class B070Test {

	@Test
	public void b070() throws Exception {
		
		B070 b=B070.builder().startTime(ZonedDateTime.now()).endTime(ZonedDateTime.now()).build();
		System.out.println(b.toSeedString());

	}
}
