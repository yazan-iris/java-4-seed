package edu.iris.dmc.seed.control.dictionary;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import edu.iris.dmc.seed.builder.BlocketteBuilder;
import edu.iris.dmc.seed.control.index.B012;

public class B030Test {
	@Test
	public void b030() throws Exception {
		
		String oldString = "0300233Steim1 Integer Compression Format~000105006F1 P4 W4 D0-31 C2 R1 P8 W4 D0-31 C2~P0 W4 N15 S2,0,1~T0 X N0 W4 D0-31 C2~T1 N0 W1 D0-7 C2 N1 W1 D0-7 C2 N2 W1 D0-7 C2 N3 W1 D0-7 C2~T2 N0 W2 D0-15 C2 N1 W2 D0-15 C2~T3 N0 W4 D0-31 C2~";
	    String b30test2 =  "0300237Steim2 Integer Compression Format~000205014F1 P4 W4 D C2 R1 P8 W4 D C2~P0 W4 N15 S2,0,1~T0 X W4~T1 Y4 W1 D C2~T2 W4 I D2~K0 X D30~K1 N0 D30 C2~K2 Y2 D15 C2~K3 Y3 D10 C2~T3 W4 I D2~K0 Y5 D6 C2~K1 Y6 D5 C2~K2 X D2 Y7 D4 C2~K3 X D30~";
		String b30test3 = "0300237Steim2 Integer Compression Format~000105014F1 P4 W4 D C2 R1 P8 W4 D C2~P0 W4 N15 S2,0,1~T0 X W4~T1 Y4 W1 D C2~T2 W4 I D2~K0 X D30~K1 N0 D30 C2~K2 Y2 D15 C2~K3 Y3 D10 C2~T3 W4 I D2~K0 Y5 D6 C2~K1 Y6 D5 C2~K2 X D2 Y7 D4 C2~K3 X D30~";
		B030 b = BlocketteBuilder.build030(oldString.getBytes());
		B030 b2 = BlocketteBuilder.build030(b30test2.getBytes());
		B030 b3 = BlocketteBuilder.build030(b30test3.getBytes());

		String newString = b.toSeedString();
		String rebuildb30test2 = b2.toSeedString();
		String rebuildb30test3 = b3.toSeedString();

		assertEquals(oldString, newString);
		assertEquals(b30test2, rebuildb30test2);
		assertEquals(b30test3, rebuildb30test3);

	}
	
	@Test
	public void b030overflow() throws Exception {
		
		String oldString = "0300233Steim1 Integer Compression Formatsdhjfgsdghkjfsdhgfghjksdfgksdhgfhsdkfhsdghkghfds~000105006F1 P4 W4 D0-31 C2 R1 P8 W4 D0-31 C2~P0 W4 N15 S2,0,1~T0 X N0 W4 D0-31 C2~T1 N0 W1 D0-7 C2 N1 W1 D0-7 C2 N2 W1 D0-7 C2 N3 W1 D0-7 C2~T2 N0 W2 D0-15 C2 N1 W2 D0-15 C2~T3 N0 W4 D0-31 C2~";
		B030 b = BlocketteBuilder.build030(oldString.getBytes());


		String newString = b.toSeedString();
		assertNotEquals(oldString, newString);

	}
}
