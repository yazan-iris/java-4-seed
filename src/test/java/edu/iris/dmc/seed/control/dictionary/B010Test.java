package edu.iris.dmc.seed.control.dictionary;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import edu.iris.dmc.seed.builder.BlocketteBuilder;
import edu.iris.dmc.seed.control.index.B010;

public class B010Test {

	@Test
	public void b010() throws Exception {
		String oldString = "010012502.1121992,001,00:00:00.0000~1992,002,00:00:00.0000~1993,029,00:00:00.0000~IRIS _ DMC~Data for 1992,001,00:00:00.0000~";
		B010 b = BlocketteBuilder.build010(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}
	@Test
	public void b010Overflow() throws Exception {
		String oldString = "010012502.1121992,001,00:00:00.0000~1992,002,00:00:00.0000"
				+ "~1993,029,00:00:00.0000~IRIS _ DMCsdfhsdjfhdshjfhgskdfgjhsdfgk"
				+ "sdgjfhgjksdfghkdshgjkfsdgfsghdjkfgsdjkhfghjsdghjkfsdghkfghjksdfghjksdfgjk"
				+ "~Data for 1992,001,00:00:00.0000dsfsdfsdfdsfsfsdfsdfsdkjfsdjgfsdfhj"
				+ "gdjghjdfhgfdhgdjflgjdsfjsd.fsd,fnjsd~";
		B010 b = BlocketteBuilder.build010(oldString.getBytes());
		String newString = b.toSeedString();
		assertNotEquals(oldString, newString);
	}
}
