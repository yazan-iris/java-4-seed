package edu.iris.seed.data;

import java.nio.ByteOrder;

import org.junit.jupiter.api.Test;

public class B1000Test {

	@Test
	public void b1000() throws Exception {

		B1000 b = B1000.Builder.newInstance().encodingFormat(ByteOrder.LITTLE_ENDIAN)
				.encodingFormat(EncodingFormat.STEIM_1).recordLengthExponent(12).build();
		b = B1000.Builder.newInstance().fromBytes(b.toSeedBytes()).build();

	}
}
