package edu.iris.dmc.seed;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

import edu.iris.dmc.io.SeedStringBuilder;

public class SeedStringBuilderTest {

	@Test
	public void t() {
		BTime b=new BTime();
		b.setYear(1995);
		b.setDayOfYear(195);
		b.setHour(0);
		b.setMinute(0);
		b.setSecond(0);
		b.setTenthMilliSecond(0);

		SeedStringBuilder s = new SeedStringBuilder();
		s.append(b);
		
	}
}
