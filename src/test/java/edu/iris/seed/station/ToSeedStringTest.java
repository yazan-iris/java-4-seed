package edu.iris.seed.station;

public class ToSeedStringTest {

	public static void main(String[] args) throws Exception {
		String oldString = "0500107MSIA +54.099602-116.864700+1086.60003000Z~0013210102014,001,00:00:00.0000~2024,366,23:59:59.0000~N2K";
		B050 b = B050.Builder.newInstance().fromBytes(oldString.getBytes()).build();
		long old = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
			b.toSeedString();
			// System.out.println(b.toSeedString());
		}

		System.out.println(System.currentTimeMillis() - old);
	}
}
//7269