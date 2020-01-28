package edu.iris.seed;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteOrder;

import org.junit.jupiter.api.Test;

import edu.iris.seed.SeedHeader.Type;

public class SeedDataHeaderTest {

	@Test
	public void run() throws Exception {
		SeedDataHeader.Builder builder = SeedDataHeader.Builder.newInstance(3450, Type.M, ' ').network("IU")
				.station("ADK").location("60").channel("VHZ");

		/*
		 * 3450, quality=M, reserved=32, network=IU, station=ADK , location=60,
		 * channel=VHZ, byteOrder=BIG_ENDIAN, start=null, end=null, numberOfSamples=691,
		 * sampleRateMultiplier=1, activityFlag=0, ioClockFlag=32, dataQualityFlag=0,
		 * numberOfFollowingBlockettes=2, sampleRateFactor=65526, timeCorrection=0,
		 * beginingOfData=64, firstDataBlockette=48
		 */

		builder.start(new BTime(2020, 1, 20, 19, 0, 695));
		builder.activityFlag((byte) 0);
		builder.beginingOfData((short) 64);
		builder.byteOrder(ByteOrder.BIG_ENDIAN);
		builder.dataQualityFlag((byte) 0);
		builder.firstDataBlockette((short) 48);
		builder.ioClockFlag((byte) 32);
		builder.numberOfFollowingBlockettes((byte) 2);
		builder.numberOfSamples((short) 691);
		builder.sampleRateFactor((short) 65526);
		builder.sampleRateMultiplier((short) 1);
		// builder.start(start);
		builder.timeCorrection(0);

		SeedDataHeader header = builder.build();
		String before = header.toString();
		byte[] bytes = header.toSeedBytes();
		header = SeedDataHeader.Builder.newInstance().bytes(bytes).build();

		assertEquals(before, header.toString());
		System.out.println(header.toString());

		// header.toSeedBytes();
		// header.toSeedString();
	}
}
