package edu.iris.dmc.seed.control.station;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.BlocketteFactory;

public class B061Test {

	@Test
	public void b061() throws Exception {
		String text = "061058404FIR_FLBELOW100_40~A0030030039+4.1895179E-13+3.3031761E-04+1.0292126E-03-3.1412280E-03+2.0570927E-04+1.5252131E-03-6.2319267E-03+1.0480133E-02-1.3120247E-02+1.0782143E-02-1.4445500E-03-1.5872946E-02+3.9507404E-02-6.5103630E-02+8.5371559E-02-8.9191342E-02+5.0061889E-02+8.3723276E-01+2.6672305E-01-1.6669311E-01+9.5283986E-02-5.0921772E-02+1.6145837E-02+7.0636240E-03-1.8387713E-02+1.9941410E-02-1.5489507E-02+8.5273541E-03-2.5578868E-03-1.8110264E-03+2.4264926E-03-3.7576946E-03+4.6729273E-04+6.3307212E-04-1.5687414E-06-1.2547978E-05+3.2104054E-07-2.6332410E-08-5.0999748E-08";
		Blockette b061 = BlocketteFactory.create(text.getBytes());
		assertEquals(
				text,
				b061.toSeedString());
	}

}
