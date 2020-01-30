package edu.iris.seed;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import edu.iris.seed.Identifier.B005;
import edu.iris.seed.Identifier.B008;
import edu.iris.seed.Identifier.B010;
import edu.iris.seed.Identifier.B011;
import edu.iris.seed.Identifier.B012;
import edu.iris.seed.abbreviation.B030;
import edu.iris.seed.abbreviation.B031;
import edu.iris.seed.abbreviation.B032;
import edu.iris.seed.abbreviation.B033;
import edu.iris.seed.abbreviation.B034;
import edu.iris.seed.abbreviation.B035;
import edu.iris.seed.abbreviation.B041;
import edu.iris.seed.abbreviation.B042;
import edu.iris.seed.abbreviation.B043;
import edu.iris.seed.abbreviation.B044;
import edu.iris.seed.abbreviation.B045;
import edu.iris.seed.abbreviation.B046;
import edu.iris.seed.abbreviation.B047;
import edu.iris.seed.abbreviation.B048;
import edu.iris.seed.abbreviation.B049;
import edu.iris.seed.data.B100;
import edu.iris.seed.data.B1000;
import edu.iris.seed.data.B1001;
import edu.iris.seed.data.B200;
import edu.iris.seed.data.B2000;
import edu.iris.seed.data.B201;
import edu.iris.seed.data.B202;
import edu.iris.seed.data.B300;
import edu.iris.seed.data.B310;
import edu.iris.seed.data.B320;
import edu.iris.seed.data.B390;
import edu.iris.seed.data.B395;
import edu.iris.seed.data.B400;
import edu.iris.seed.data.B405;
import edu.iris.seed.data.B500;
import edu.iris.seed.lang.SeedStrings;
import edu.iris.seed.station.B050;
import edu.iris.seed.station.B051;
import edu.iris.seed.station.B052;
import edu.iris.seed.station.B053;
import edu.iris.seed.station.B054;
import edu.iris.seed.station.B055;
import edu.iris.seed.station.B057;
import edu.iris.seed.station.B058;
import edu.iris.seed.station.B059;
import edu.iris.seed.station.B060;
import edu.iris.seed.station.B061;
import edu.iris.seed.station.B062;

public abstract class SeedBlockette<T extends Blockette> {

	private final int type;
	private final String title;

	public SeedBlockette(int type, String title) {
		this.type = type;
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}

	public static final Map<Integer, Class<? extends Blockette>> controlMap = new HashMap<Integer, Class<? extends Blockette>>();
	static {
		controlMap.put(new Integer(5), B005.class);
		controlMap.put(new Integer(8), B008.class);
		controlMap.put(new Integer(10), B010.class);
		controlMap.put(new Integer(11), B011.class);
		controlMap.put(new Integer(12), B012.class);

		controlMap.put(new Integer(30), B030.class);
		controlMap.put(new Integer(31), B031.class);
		controlMap.put(new Integer(32), B032.class);
		controlMap.put(new Integer(33), B033.class);
		controlMap.put(new Integer(34), B034.class);
		controlMap.put(new Integer(35), B035.class);

		controlMap.put(new Integer(41), B041.class);
		controlMap.put(new Integer(42), B042.class);
		controlMap.put(new Integer(43), B043.class);
		controlMap.put(new Integer(44), B044.class);
		controlMap.put(new Integer(45), B045.class);
		controlMap.put(new Integer(46), B046.class);

		controlMap.put(new Integer(47), B047.class);
		controlMap.put(new Integer(48), B048.class);
		controlMap.put(new Integer(49), B049.class);

		controlMap.put(new Integer(50), B050.class);
		controlMap.put(new Integer(51), B051.class);
		controlMap.put(new Integer(52), B052.class);
		controlMap.put(new Integer(53), B053.class);
		controlMap.put(new Integer(54), B054.class);
		controlMap.put(new Integer(55), B055.class);
		controlMap.put(new Integer(57), B057.class);
		controlMap.put(new Integer(58), B058.class);
		controlMap.put(new Integer(59), B059.class);
		controlMap.put(new Integer(60), B060.class);

		controlMap.put(new Integer(61), B061.class);
		controlMap.put(new Integer(62), B062.class);

		controlMap.put(new Integer(100), B100.class);
		controlMap.put(new Integer(200), B200.class);
		controlMap.put(new Integer(201), B201.class);
		controlMap.put(new Integer(202), B202.class);
		controlMap.put(new Integer(300), B300.class);
		controlMap.put(new Integer(310), B310.class);
		controlMap.put(new Integer(320), B320.class);
		controlMap.put(new Integer(390), B390.class);
		controlMap.put(new Integer(395), B395.class);
		controlMap.put(new Integer(400), B400.class);
		controlMap.put(new Integer(405), B405.class);
		controlMap.put(new Integer(500), B500.class);

		controlMap.put(new Integer(1000), B1000.class);
		controlMap.put(new Integer(1001), B1001.class);
		controlMap.put(new Integer(2000), B2000.class);

	}

	public static void validate(int type, int minumumLength, byte[] bytes) throws SeedException {

		try {
			if (bytes.length < 7) {
				throw new SeedException("Expected at least 7 bytes but was {}", bytes.length);
			}
			int bType = SeedStrings.parseInt(bytes, 0, 3);
			if (type != bType) {
				throw new SeedException("Expected type {} but was {}", type, bType);
			}
			int length = SeedStrings.parseInt(bytes, 3, 4);
			if (length < minumumLength) {
				throw new SeedException(bytes, type, "Expected minumum of {} bytes but was {}", minumumLength,
						bytes.length);
			}
			if (bytes.length < length) {
				throw new SeedException("Expected {} bytes but was {}", length, bytes.length);
			}
		} catch (NumberFormatException e) {
			throw new SeedException(e, bytes, type);
		}
	}

	public abstract BlocketteBuilder<T> builder();

	public static <T extends Blockette> BlocketteBuilder<T> builder(int type) throws SeedException {
		Class<? extends Blockette> clazz = controlMap.get(type);

		if (clazz == null) {
			throw new SeedException("Invalid blockette type [{}] ", type);
		}
		try {
			Method method = clazz.getMethod("builder");
			BlocketteBuilder<T> b = (BlocketteBuilder<T>) method.invoke(clazz.newInstance());

			return b;//method.invoke(clazz.newInstance());
		} catch (Exception e) {
			throw new SeedException(e);
		}
	}
}
