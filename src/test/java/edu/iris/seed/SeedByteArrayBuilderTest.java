package edu.iris.seed;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.junit.jupiter.api.Test;

public class SeedByteArrayBuilderTest {

	@Test
	public void run() throws Exception {
		SeedByteArrayBuilder b = new SeedByteArrayBuilder(10);

		BTime t = new BTime(2020, 1, 20, 19, 0, 695);

		b.append(t);
		byte[] bytes = b.toBytes();
		
		ByteBuffer buffer = ByteBuffer.wrap(bytes, 2, 2).order(ByteOrder.BIG_ENDIAN);
		
		System.out.println(buffer.getShort()& 0xFFFF);
	}
}
