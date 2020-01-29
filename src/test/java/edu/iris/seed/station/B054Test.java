package edu.iris.seed.station;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class B054Test {

	@Test
	public void shortBlockette() throws Exception {
		String text = "0540024D0200200300000000";

		B054 b054 = B054.Builder.newInstance().fromBytes(text.getBytes()).build();
		assertEquals(text, b054.toSeedString());
	}

	@Test
	public void longBlockette() throws Exception {
		String text = "0540888D030030030036+3.05176E-05+0.00000E+00+1.52588E-04+0.00000E+00+4.57764E-04+0.00000E+00+1.06812E-03+0.00000E+00+2.13623E-03+0.00000E+00+3.84521E-03+0.00000E+00+6.40869E-03+0.00000E+00+1.00708E-02+0.00000E+00+1.49536E-02+0.00000E+00+2.10571E-02+0.00000E+00+2.82593E-02+0.00000E+00+3.63159E-02+0.00000E+00+4.48608E-02+0.00000E+00+5.34058E-02+0.00000E+00+6.13403E-02+0.00000E+00+6.79321E-02+0.00000E+00+7.26318E-02+0.00000E+00+7.50732E-02+0.00000E+00+7.50732E-02+0.00000E+00+7.26318E-02+0.00000E+00+6.79321E-02+0.00000E+00+6.13403E-02+0.00000E+00+5.34058E-02+0.00000E+00+4.48608E-02+0.00000E+00+3.63159E-02+0.00000E+00+2.82593E-02+0.00000E+00+2.10571E-02+0.00000E+00+1.49536E-02+0.00000E+00+1.00708E-02+0.00000E+00+6.40869E-03+0.00000E+00+3.84521E-03+0.00000E+00+2.13623E-03+0.00000E+00+1.06812E-03+0.00000E+00+4.57764E-04+0.00000E+00+1.52588E-04+0.00000E+00+3.05176E-05+0.00000E+000000";
		B054 b054 = B054.Builder.newInstance().fromBytes(text.getBytes()).build();
		assertEquals(text, b054.toSeedString());
	}

	@Test
	public void oneSplit() throws Exception {
		B054 b = new B054();
		b.setResponseType('T');
		b.setSignalInputUnit(1);
		b.setSignalOutputUnit(1);
		b.setStageNumber(1);
		List<B054> l = b.split();
		assertEquals(1, l.size());

		for (int i = 0; i < 415; i++) {
			b.getNumerators().add(new Number(1D, 1D));
		}
		l = b.split();
		assertEquals(1, l.size());

		for (int i = 0; i < 415; i++) {
			b.getNumerators().add(new Number(1D, 1D));
		}
		l = b.split();
		assertEquals(2, l.size());

		for (int i = 0; i < 415; i++) {
			b.getNumerators().add(new Number(1D, 1D));
		}
		l = b.split();
		assertEquals(3, l.size());

	}
}
