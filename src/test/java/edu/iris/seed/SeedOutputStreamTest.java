package edu.iris.seed;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.seed.io.output.StringBuilderOutputStream;
import edu.iris.seed.record.Header.Type;
import edu.iris.seed.station.B052;

public class SeedOutputStreamTest {

	@Test
	public void b053() throws Exception {
		try (StringBuilderOutputStream o = new StringBuilderOutputStream();) {
			SeedOutputStream out = new SeedOutputStream(o, 4096, 1, Type.S);
			
			String text = "0520149  BDF0000004~001002+28.209718-177.381430+0004.6000.0000.0+00.00001122.0000E+010.0000E+000000CG~2013,315,00:00:00.0000~2017,045,00:00:00.0000~N";
			Blockette b052 = B052.Builder.newInstance().build(text.getBytes());
			
			out.write(b052);
			out.write(b052);
			System.out.println(o.toString());
		}

	}
}
