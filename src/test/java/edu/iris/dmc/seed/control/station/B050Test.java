package edu.iris.dmc.seed.control.station;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.iris.dmc.seed.BTime;
import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.BlocketteFactory;
import edu.iris.dmc.seed.SeedException;

public class B050Test {

	String text = "0500154I58H1+28.209718-177.381430+0004.60000000Midway Islands Infrasonic Array, Site I58H1, USA~0003210102013,315,00:00:00.0000~2999,365,23:59:59.0000~NIM";
	             //05001555I58H1+28.209718-177.381430+0004.60000000Midway Islands Infrasonic Array, Site I58H1, USA~0003210102013,315,00:00:00.0000~2999,365,23:59:59.0000~NIM
	@Test
	public void fromText() throws Exception {
		Blockette b050 = BlocketteFactory.create(text.getBytes());
		assertEquals(text, b050.toSeedString());
	}

	@Test
	public void fromObject1() throws Exception {
		B050 b = new B050();
		b.setNetworkCode("IM");
		b.setStationCode("I58H1");

		b.setSiteName("Midway Islands Infrasonic Array, Site I58H1, USA");
		b.setStartTime(BTime.valueOf(2013, 315, 0, 0, 0, 0));
		b.setEndTime(BTime.valueOf(2999, 365, 23, 59, 59, 0));
		b.setLatitude(28.209718);
		b.setLongitude(-177.381430);
		b.setElevation(4.6);
		b.setBit32BitOrder(3210);
		b.setBit16BitOrder(10);
		b.setUpdateFlag('N');
		assertEquals(text, b.toSeedString());
	}

	@Test
	public void fromObject2() throws Exception {
		B050 b = new B050();
		b.setNetworkCode("IM");
		b.setStationCode("I58H");

		b.setSiteName("Midway Islands Infrasonic Array, Site I58H1, USA");
		b.setStartTime(BTime.valueOf(2013, 315, 0, 0, 0, 0));
		b.setEndTime(BTime.valueOf(2999, 365, 23, 59, 59, 0));
		b.setLatitude(28.209718);
		b.setLongitude(-177.381430);
		b.setElevation(4.6);
		b.setBit32BitOrder(3210);
		b.setBit16BitOrder(10);
		b.setUpdateFlag('N');
		System.out.println(b.toSeedString());
		assertEquals("0500154I58H +28.209718-177.381430+0004.60000000Midway Islands Infrasonic Array, Site I58H1, USA~0003210102013,315,00:00:00.0000~2999,365,23:59:59.0000~NIM", b.toSeedString());
	}
	
	@Test(expected = SeedException.class)
	public void exceptionHandling() throws Exception {
		B050 b = new B050();
		b.setNetworkCode("IM");
		b.setStationCode("I58H111");

		b.setSiteName("Midway Islands Infrasonic Array, Site I58H1, USA");
		b.setStartTime(BTime.valueOf(2013, 315, 0, 0, 0, 0));
		b.setEndTime(BTime.valueOf(2999, 365, 23, 59, 59, 0));
		b.setLatitude(28.209718);
		b.setLongitude(-177.381430);
		b.setElevation(4.6);
		b.setBit32BitOrder(3210);
		b.setBit16BitOrder(10);
		b.setUpdateFlag('N');
		b.toSeedString();
	}
	
	
}
