package edu.iris.seed;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.iris.seed.station.B050;

public class SeedBlocketteTest {

	@Test
	public void b053() throws Exception {
		String seedString = "0530574B01004008+1.42792E+01+5.00000E-02005+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00-1.25000E+01+0.00000E+00+0.00000E+00+0.00000E+00-2.42718E-02+0.00000E+00+0.00000E+00+0.00000E+00-2.42718E-02+0.00000E+00+0.00000E+00+0.00000E+00006-1.96012E-03+1.88382E-03+0.00000E+00+0.00000E+00-1.96012E-03-1.88382E-03+0.00000E+00+0.00000E+00-2.43226E-02+0.00000E+00+0.00000E+00+0.00000E+00-2.43226E-02+0.00000E+00+0.00000E+00+0.00000E+00-5.99595E+00+1.19322E+01+0.00000E+00+0.00000E+00-5.99595E+00-1.19322E+01+0.00000E+00+0.00000E+00";
		Blockette b = SeedBlockette.builder(53).fromString(seedString).build();
		assertEquals(seedString, b.toSeedString());
	}

	@Test
	public void b054() throws Exception {
		String seedString = "0540024D0200200300000000";
		Blockette b = SeedBlockette.builder(54).fromString(seedString).build();

		assertEquals(seedString, b.toSeedString());
	}

	@Test
	public void relaxNonAscii() throws Exception {
		String seedString = new String(
				"0500144ESCA +43.831000+007.374000+0550.00000000Chapelle Saint Pancrace à l'Escarène~0013210102003,302,12:30:00.0000~2500,365,12:00:00.0000~NFR");
		Blockette b = SeedBlockette.builder(50).fromString(seedString).build();
		assertEquals("Chapelle Saint Pancrace  l'Escarne", ((B050) b).getSiteName());

		assertEquals(
				"0500140ESCA +43.831000+007.374000+0550.00000000Chapelle Saint Pancrace  l'Escarne~0013210102003,302,12:30:00.0000~2500,365,12:00:00.0000~NFR",
				b.toSeedString());
	}

	@Test
	public void bytesTooShort() throws Exception {
		String seedString = new String(
				"0500142ESCA +43.831000+007.374000+0550.00000000Chapelle Saint Pancrace à l'Escarène~0013210102003,302,12:30:00.0000~2500,365,12:00:00.0000~N");
		Assertions.assertThrows(SeedException.class, () -> {
			Blockette b = SeedBlockette.builder(50).fromString(seedString).build();
		});

	}

	@Test
	public void bytesIncomplete() throws Exception {
		String seedString = new String(
				"0500144ESCA +43.831000+007.374000+0550.00000000Chapelle Saint Pancrace à l'Escarène~0013210102003,302,12:30:00.0000~2500,365,12:00:00.0");

		Assertions.assertThrows(SeedException.class, () -> {
			Blockette b = SeedBlockette.builder(50).fromString(seedString).build();
		});
	}
}
