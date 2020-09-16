package edu.iris.dmc.seed.control.station;

import edu.iris.dmc.seed.BTime;
import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.BlocketteFactory;
import edu.iris.dmc.seed.SeedException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



public class B052Test {

	@Test
	public void b052() throws Exception {
		             //0520149  BDF0000000~000000+28.209718-177.381430+1,00000.0000.0000.0+00.00000000.0000E+000.0000E+000000~2013,315,00:00:00.0000~2017,045,00:00:00.0000~N
		String text = "0520149  BDF0000004~001002+28.209718-177.381430+0004.6000.0000.0+00.00001122.0000E+010.0000E+000000CG~2013,315,00:00:00.0000~2017,045,00:00:00.0000~N";
		Blockette b052 = BlocketteFactory.create(text.getBytes());
		assertEquals("0520149  BDF0000004~001002+28.209718-177.381430+0004.600000000.0+00.00001122.0000E+010.0000E+000000CG~2013,315,00:00:00.0000~2017,045,00:00:00.0000~N", b052.toSeedString());
	}
	
	@Test
	public void fromObject() throws Exception {
		B052 b = new B052();
		b.setChannelCode("BDF");
		b.setLocationCode("  ");

		b.setStartTime(BTime.valueOf(2013, 315, 0, 0, 0, 0));
		b.setEndTime(BTime.valueOf(217, 45, 0, 0, 0, 0));
		b.setLatitude(+28.209718);
		b.setLongitude(-177.381430);
		b.setElevation(-99999);

		b.setUpdateFlag('N');
		System.out.println(b.toSeedString());

		//assertEquals(text, b.toSeedString());
	}
	
	@Test
	public void handleException() throws Exception {
		Assertions.assertThrows(SeedException.class, () -> {
		B052 b = new B052();
		b.setChannelCode("BDF");
		b.setLocationCode("  ");

		b.setStartTime(BTime.valueOf(2013, 315, 0, 0, 0, 0));
		b.setEndTime(BTime.valueOf(217, 45, 0, 0, 0, 0));
		b.setLatitude(+28.209718);
		b.setLongitude(-177.381430);
		b.setElevation(-99999999);

		b.setUpdateFlag('N');
		System.out.println(b.toSeedString());

		//assertEquals(text, b.toSeedString());
		  });
	}
	

	@Test
	public void negativeDepth() throws Exception {
		// 0520149
		// BDF0000000~000000+28.209718-177.381430+1,00000.0000.0000.0+00.00000000.0000E+000.0000E+000000~2013,315,00:00:00.0000~2017,045,00:00:00.0000~N
		String text = "0520149  BDF0000004~001002+28.209718-177.381430+0004.6000.0000.0+00.00001122.0000E+010.0000E+000000CG~2013,315,00:00:00.0000~2017,045,00:00:00.0000~N";
		Blockette b = BlocketteFactory.create(text.getBytes());

		B052 b052 = (B052) b;
		b052.setLocalDepth(-100);
		assertEquals(
				"0520149  BDF0000004~001002+28.209718-177.381430+0004.6-0100000.0+00.00001122.0000E+010.0000E+000000CG~2013,315,00:00:00.0000~2017,045,00:00:00.0000~N",
				b052.toSeedString());
		b052.setLocalDepth(-9999);
		assertEquals(
				"0520149  BDF0000004~001002+28.209718-177.381430+0004.6-9999000.0+00.00001122.0000E+010.0000E+000000CG~2013,315,00:00:00.0000~2017,045,00:00:00.0000~N",
				b052.toSeedString());
		
		b052.setLocalDepth(-99.9);
		assertEquals(
				"0520149  BDF0000004~001002+28.209718-177.381430+0004.6-99.9000.0+00.00001122.0000E+010.0000E+000000CG~2013,315,00:00:00.0000~2017,045,00:00:00.0000~N",
				b052.toSeedString());
		
		b052.setLocalDepth(-150.0);
		assertEquals(
				"0520149  BDF0000004~001002+28.209718-177.381430+0004.6-0150000.0+00.00001122.0000E+010.0000E+000000CG~2013,315,00:00:00.0000~2017,045,00:00:00.0000~N",
				b052.toSeedString());
		
		b052.setLocalDepth(-150.9);
		assertEquals(
				"0520149  BDF0000004~001002+28.209718-177.381430+0004.6-0151000.0+00.00001122.0000E+010.0000E+000000CG~2013,315,00:00:00.0000~2017,045,00:00:00.0000~N",
				b052.toSeedString());
	}
	
	@Test
	public void positiveDepth() throws Exception {
		// 0520149
		// BDF0000000~000000+28.209718-177.381430+1,00000.0000.0000.0+00.00000000.0000E+000.0000E+000000~2013,315,00:00:00.0000~2017,045,00:00:00.0000~N
		String text = "0520149  BDF0000004~001002+28.209718-177.381430+0004.6000.0000.0+00.00001122.0000E+010.0000E+000000CG~2013,315,00:00:00.0000~2017,045,00:00:00.0000~N";
		Blockette b = BlocketteFactory.create(text.getBytes());

		B052 b052 = (B052) b;
		b052.setLocalDepth(99999);
		assertEquals(
				"0520149  BDF0000004~001002+28.209718-177.381430+0004.699999000.0+00.00001122.0000E+010.0000E+000000CG~2013,315,00:00:00.0000~2017,045,00:00:00.0000~N",
				b.toSeedString());
	}
	
}
