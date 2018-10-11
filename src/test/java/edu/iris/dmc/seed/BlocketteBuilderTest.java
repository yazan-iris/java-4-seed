package edu.iris.dmc.seed;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import edu.iris.dmc.seed.builder.BlocketteBuilder;
import edu.iris.dmc.seed.control.station.B050;
import edu.iris.dmc.seed.control.station.B053;
import edu.iris.dmc.seed.control.station.B054;

public class BlocketteBuilderTest {

	@Test
	public void b053() throws Exception {
		String seedString = "0530382A01001002+3.14096E+02+1.00000E+00003+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00-1.70000E-01+0.00000E+00+0.00000E+00+0.00000E+00004+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00-3.14000E+02+0.00000E+00+0.00000E+00+0.00000E+00-1.88000E-01+0.00000E+00+0.00000E+00+0.00000E+00-4.40000E-02+0.00000E+00+0.00000E+00+0.00000E+00";
		B053 b = BlocketteBuilder.build053(seedString.getBytes());

		assertEquals(seedString, b.toSeedString());
	}
	
	@Test
	public void b054() throws Exception {
		String seedString = "0540024D0200200300000000";
		B054 b = BlocketteBuilder.build054(seedString.getBytes());

		assertEquals(seedString, b.toSeedString());
	}

	@Test
	public void relaxNonAscii() throws Exception {
		String seedString = new String(
				"0500144ESCA +43.831000+007.374000+0550.00000000Chapelle Saint Pancrace à l'Escarène~0013210102003,302,12:30:00.0000~2500,365,12:00:00.0000~NFR");
		B050 b = BlocketteBuilder.build050(seedString.getBytes());
		assertEquals("Chapelle Saint Pancrace  l'Escarne", b.getSiteName());

		assertEquals(
				"0500140ESCA +43.831000+007.374000+0550.00000000Chapelle Saint Pancrace  l'Escarne~0013210102003,302,12:30:00.0000~2500,365,12:00:00.0000~NFR",
				b.toSeedString());
	}

	@Test(expected = SeedException.class)
	public void bytesTooShort() throws Exception {
		String seedString = new String(
				"0500142ESCA +43.831000+007.374000+0550.00000000Chapelle Saint Pancrace à l'Escarène~0013210102003,302,12:30:00.0000~2500,365,12:00:00.0000~N");
		BlocketteBuilder.build050(seedString.getBytes());
	}

	@Test(expected = SeedException.class)
	public void bytesIncomplete() throws Exception {
		String seedString = new String(
				"0500144ESCA +43.831000+007.374000+0550.00000000Chapelle Saint Pancrace à l'Escarène~0013210102003,302,12:30:00.0000~2500,365,12:00:00.0");
		BlocketteBuilder.build050(seedString.getBytes());
	}
}
