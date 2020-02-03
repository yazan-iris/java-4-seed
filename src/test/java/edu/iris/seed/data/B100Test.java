package edu.iris.seed.data;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.junit.jupiter.api.Test;

public class B100Test {

	@Test
	public void b100() throws Exception {

		ByteBuffer bb = ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN);
		bb.putShort((short) (100 & 0xffff));
		int type = ByteUtil.readUnsignedShort(bb.array(), 0, 2);
		System.out.println(type);

		float f = 33.333f;

		B100 b = B100.Builder.newInstance(33.333f).build();
	
		b = B100.Builder.newInstance().fromBytes(b.toSeedBytes()).build();

		bb = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);
		byte[] array=bb.putFloat(f).array();
	

	}
}
