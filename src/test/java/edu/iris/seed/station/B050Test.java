package edu.iris.seed.station;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.seed.ControlBlockette;

public class B050Test {

	@Test
	public void b050() throws Exception {
		String oldString = "0500107MSIA +54.099602-116.864700+1086.60003000Z~0013210102014,001,00:00:00.0000~2024,366,23:59:59.0000~N2K";
		String expected = "0500107MSIA +54.099602-116.864700+1086.60000000Z~0013210102014,001,00:00:00.0000~2024,366,23:59:59.0000~N2K";
		ControlBlockette b = B050.Builder.newInstance().fromString(oldString).build();

		long old=System.currentTimeMillis();
		String newString = b.toSeedString();
		System.out.println(System.currentTimeMillis()-old);
		assertEquals(expected, newString);
		
		String testString = "050####MSIA +54.099602-116.864700+1086.60003000Z~0013210102014,001,00:00:00.0000~2024,366,23:59:59.0000~N2K";
		
		
		//testString.replace(3, 7, testString.length(), "####");

	}
	
	@Test
	public void b0501() throws Exception {
		String oldString = "0500107MSIA +54.099602-116.864700+1086.60003000Z~0013210102014,001,00:00:00.0000~2024,366,23:59:59.0000~N2K";
		String expected = "0500107MSIA +54.099602-116.864700+1086.60000000Z~0013210102014,001,00:00:00.0000~2024,366,23:59:59.0000~N2K";
		
		ControlBlockette b = B050.Builder.newInstance().fromString(oldString).build();
		long old=System.currentTimeMillis();
		String newString = b.toSeedString();
		System.out.println(System.currentTimeMillis()-old);
		assertEquals(expected, newString);

	}
}
