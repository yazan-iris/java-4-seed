package edu.iris.seed.station;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.seed.Blockette;
import edu.iris.seed.index.B005;
import edu.iris.seed.index.B008;
import edu.iris.seed.index.B010;
import edu.iris.seed.index.B011;
import edu.iris.seed.index.B012;

public class StationBlocketteToSeedStringTest {

	@Test
	public void b005() throws Exception {
		String oldString = "0050036 2.1121992,001,00:00:00.0000~";
		Blockette b = B005.Builder.newInstance().build(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}

	@Test
	public void b008() throws Exception {
		String oldString = "008011702.112 ANMO  BHZ1992,001,00:00:00.0000~1992,002,00:00:00.0000~1992,002,00:00:00.0000~1993,029,00:00:00.0000~IU";
		// 008011702.112 ANMO
		// BHZ1992,001,00:00:00.0000~1992,002,00:00:00.0000~1992,002,00:00:00.0000~1993,029,00:00:00.0000~IU
		Blockette b = B008.Builder.newInstance().build(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}

	@Test
	public void b010() throws Exception {
		String oldString = "010012502.1121992,001,00:00:00.0000~1992,002,00:00:00.0000~1993,029,00:00:00.0000~IRIS _ DMC~Data for 1992,001,00:00:00.0000~";
		Blockette b = B010.Builder.newInstance().build(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}

	@Test
	public void b011() throws Exception {
		String oldString = "0110054004AAK  000003ANMO 000007ANTO 000010BJI  000012";
		Blockette b = B011.Builder.newInstance().build(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}

	@Test
	public void b012() throws Exception {
		String oldString = "012006300011992,001,00:00:00.0000~1992,002,00:00:00.0000~000014";
		Blockette b = B012.Builder.newInstance().build(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}

	@Test
	public void b050() throws Exception {
		String oldString = "0500133RR01 +20.000067+055.425030-4385.00000000RHUM-RUM site 01, OBS DEPAS~0013210102012,279,06:52:37.0000~2013,310,22:08:29.0000~NYV";
		Blockette b = B050.Builder.newInstance().build(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}

	@Test
	public void b051() throws Exception {
		String oldString = "05100632012,275,06:52:37.0000~2014,317,01:06:40.0000~0001000000";
		Blockette b = B051.Builder.newInstance().build(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);

		oldString = "05100632012,275,06:52:37.0000~2014,317,00:00:00.0000~0001000000";
		b = B051.Builder.newInstance().build(oldString.getBytes());
		newString = b.toSeedString();
		assertEquals(oldString, newString);
	}

	@Test
	public void b052() throws Exception {
		String oldString = "052014900BH20000002~001000+20.000067+055.425030-4385.0000.0090.0+00.00000125.0000E+012.0000E-040000CG~2012,284,20:00:00.0000~2013,317,01:06:40.0000~N";
		Blockette b = B052.Builder.newInstance().build(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}

	@Test
	public void b053() throws Exception {
		String oldString = "0530334A01001002+1.54100E+04+1.00000E+00002+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00004-1.17800E-02+1.16932E-02+0.00000E+00+0.00000E+00-1.17800E-02-1.16932E-02+0.00000E+00+0.00000E+00-8.00000E+01+9.50000E+01+0.00000E+00+0.00000E+00-8.00000E+01-9.50000E+01+0.00000E+00+0.00000E+00";
		Blockette b = B053.Builder.newInstance().build(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}

	@Test
	public void b059() throws Exception {
		String oldString = "05900632012,284,20:00:00.0000~2013,317,01:06:40.0000~0003000000";
		Blockette b = B059.Builder.newInstance().build(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);

		oldString = "05900632012,284,20:00:00.0000~1987,023,00:00:00.0000~0003000000";
		b = B059.Builder.newInstance().build(oldString.getBytes());
		newString = b.toSeedString();
		assertEquals(oldString, newString);
	}

}
