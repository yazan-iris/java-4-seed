package edu.iris.seed.station;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import edu.iris.seed.SeedBlockette;

public class B053Test {

	@Test
	public void shouldPass() throws Exception {
		String text = "0530382A01001002+3.14096E+02+1.00000E+00003+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00-1.70000E-01+0.00000E+00+0.00000E+00+0.00000E+00004+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00-3.14000E+02+0.00000E+00+0.00000E+00+0.00000E+00-1.88000E-01+0.00000E+00+0.00000E+00+0.00000E+00-4.40000E-02+0.00000E+00+2.00000E-04+2.00000E-04";
		B053 b053 = (B053) SeedBlockette.Builder.newInstance().build(text.getBytes());

		assertNotNull(b053.getPoles());
		assertEquals(4, b053.getPoles().size());
		Pole pole = b053.getPoles().get(3);
		assertNotNull(pole.getImaginary());
		assertEquals(0, pole.getImaginary().getValue(), 0.0000000001);
		assertEquals(0.00020E+00, pole.getImaginary().getError(), 0.0000000001);
		assertEquals(text, b053.toSeedString());
	}

	@Test
	public void oneSplit() throws Exception {
		String text = "0530382A01001002+3.14096E+02+1.00000E+00003+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00-1.70000E-01+0.00000E+00+0.00000E+00+0.00000E+00004+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00-3.14000E+02+0.00000E+00+0.00000E+00+0.00000E+00-1.88000E-01+0.00000E+00+0.00000E+00+0.00000E+00-4.40000E-02+0.00000E+00+2.00000E-04+2.00000E-04";
		B053 b053 = (B053) SeedBlockette.Builder.newInstance().build(text.getBytes());

		assertNotNull(b053.getPoles());
		assertEquals(4, b053.getPoles().size());
		Pole pole = b053.getPoles().get(3);
		assertNotNull(pole.getImaginary());
		assertEquals(0, pole.getImaginary().getValue(), 0.0000000001);
		assertEquals(0.00020E+00, pole.getImaginary().getError(), 0.0000000001);
		assertEquals(text, b053.toSeedString());

		assertEquals(3, b053.getZeros().size());
		for (int i = 0; i < 200; i++) {
			b053.getZeros().add(new Zero(1, 0, 0, 0));
		}
		assertEquals(203, b053.getZeros().size());
		assertEquals(1, b053.split().size());
		assertEquals(4, b053.getPoles().size());

		b053.getZeros().add(new Zero(1, 0, 0, 0));
		assertEquals(204, b053.getZeros().size());

		List<B053> list = b053.split();
		assertEquals(2, list.size());
		B053 b1 = list.get(0);

		assertEquals(204, b1.getZeros().size());
		assertEquals(3, b1.getPoles().size());

		B053 b2 = list.get(1);
		assertEquals(0, b2.getZeros().size());
		assertEquals(1, b2.getPoles().size());
	}

	@Test
	public void twoSplits() throws Exception {

		B053 b053 = new B053();
		b053.setNormalizationFactor(1);
		b053.setNormalizationFrequency(1);
		b053.setSignalInputUnit(1);
		b053.setSignalOutputUnit(1);
		b053.setStageNumber(1);
		b053.setTransferFunctionType('T');

		assertNotNull(b053.getPoles());
		assertEquals(0, b053.getPoles().size());

		assertNotNull(b053.getZeros());
		assertEquals(0, b053.getZeros().size());

		for (int i = 0; i < 207; i++) {
			b053.getZeros().add(new Zero(1, 0, 0, 0));
		}

		List<B053> l = b053.split();
		assertEquals(1, l.size());
		
		b053=l.get(0);
		b053.getZeros().add(new Zero(1, 0, 0, 0));
		l = b053.split();
		assertEquals(2, l.size());
		
		for (int i = 0; i < 206; i++) {
			b053.getZeros().add(new Zero(1, 0, 0, 0));
		}
		l = b053.split();
		assertEquals(2, l.size());
		
		b053.getZeros().add(new Zero(1, 0, 0, 0));
		l = b053.split();
		assertEquals(3, l.size());

	}
}
