package edu.iris.seed;

import java.io.InputStream;

import org.junit.jupiter.api.Test;

import edu.iris.seed.io.SeedIOUtils;

public class SeedFileTest {

	@Test
	public void run() throws Exception {
		try (InputStream stream = SeedFileTest.class.getClassLoader().getResourceAsStream("BK.dataless.seed");) {
			SeedVolume v=SeedIOUtils.toSeedVolume(stream);
			for(Blockette b:v.blockettes()){
				if(b.getType()<50&&b.getType()>39) {
					System.out.println(((ControlBlockette)b).toSeedString());
				}
			}
		}
	}
}
