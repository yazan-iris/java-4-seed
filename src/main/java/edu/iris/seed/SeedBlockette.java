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

	protected static final Map<Integer, Class<? extends BlocketteBuilder<? extends ControlBlockette>>> controlBlocketteBuilders = new HashMap<Integer, Class<? extends BlocketteBuilder<? extends ControlBlockette>>>();
	static {
		controlBlocketteBuilders.put(5, B005.Builder.class);
		controlBlocketteBuilders.put(8, B008.Builder.class);
		controlBlocketteBuilders.put(10, B010.Builder.class);
		controlBlocketteBuilders.put(11, B011.Builder.class);
		controlBlocketteBuilders.put(12, B012.Builder.class);
		controlBlocketteBuilders.put(30, B030.Builder.class);
		controlBlocketteBuilders.put(31, B031.Builder.class);
		controlBlocketteBuilders.put(32, B032.Builder.class);
		controlBlocketteBuilders.put(33, B033.Builder.class);
		controlBlocketteBuilders.put(34, B034.Builder.class);
		controlBlocketteBuilders.put(35, B035.Builder.class);
		controlBlocketteBuilders.put(41, B041.Builder.class);
		controlBlocketteBuilders.put(42, B042.Builder.class);
		controlBlocketteBuilders.put(43, B043.Builder.class);
		controlBlocketteBuilders.put(44, B044.Builder.class);
		controlBlocketteBuilders.put(45, B045.Builder.class);
		controlBlocketteBuilders.put(46, B046.Builder.class);
		controlBlocketteBuilders.put(47, B047.Builder.class);
		controlBlocketteBuilders.put(48, B048.Builder.class);
		controlBlocketteBuilders.put(49, B049.Builder.class);
		controlBlocketteBuilders.put(50, B050.Builder.class);
		controlBlocketteBuilders.put(51, B051.Builder.class);
		controlBlocketteBuilders.put(52, B052.Builder.class);
		controlBlocketteBuilders.put(53, B053.Builder.class);
		controlBlocketteBuilders.put(54, B054.Builder.class);
		controlBlocketteBuilders.put(55, B055.Builder.class);
		controlBlocketteBuilders.put(57, B057.Builder.class);
		controlBlocketteBuilders.put(58, B058.Builder.class);
		controlBlocketteBuilders.put(59, B059.Builder.class);
		controlBlocketteBuilders.put(60, B060.Builder.class);
		controlBlocketteBuilders.put(61, B061.Builder.class);
		controlBlocketteBuilders.put(62, B062.Builder.class);
	}

	protected static final Map<Integer, Class<? extends BlocketteBuilder<? extends DataBlockette>>> dataBlocketteBuilders = new HashMap<>();
	static {
		dataBlocketteBuilders.put(100, B100.Builder.class);
		dataBlocketteBuilders.put(200, B200.Builder.class);
		dataBlocketteBuilders.put(201, B201.Builder.class);
		dataBlocketteBuilders.put(202, B202.Builder.class);
		dataBlocketteBuilders.put(300, B300.Builder.class);
		dataBlocketteBuilders.put(310, B310.Builder.class);
		dataBlocketteBuilders.put(320, B320.Builder.class);
		dataBlocketteBuilders.put(390, B390.Builder.class);
		dataBlocketteBuilders.put(395, B395.Builder.class);
		dataBlocketteBuilders.put(400, B400.Builder.class);
		dataBlocketteBuilders.put(405, B405.Builder.class);
		dataBlocketteBuilders.put(500, B500.Builder.class);

		dataBlocketteBuilders.put(1000, B1000.Builder.class);
		dataBlocketteBuilders.put(1001, B1001.Builder.class);
		dataBlocketteBuilders.put(2000, B2000.Builder.class);
	}
	protected static final Map<Integer, Class<? extends Blockette>> controlMap = new HashMap<>();
	static {
		controlMap.put(5, B005.class);
		controlMap.put(8, B008.class);
		controlMap.put(10, B010.class);
		controlMap.put(11, B011.class);
		controlMap.put(12, B012.class);

		controlMap.put(30, B030.class);
		controlMap.put(31, B031.class);
		controlMap.put(32, B032.class);
		controlMap.put(33, B033.class);
		controlMap.put(34, B034.class);
		controlMap.put(35, B035.class);

		controlMap.put(41, B041.class);
		controlMap.put(42, B042.class);
		controlMap.put(43, B043.class);
		controlMap.put(44, B044.class);
		controlMap.put(45, B045.class);
		controlMap.put(46, B046.class);

		controlMap.put(47, B047.class);
		controlMap.put(48, B048.class);
		controlMap.put(49, B049.class);

		controlMap.put(50, B050.class);
		controlMap.put(51, B051.class);
		controlMap.put(52, B052.class);
		controlMap.put(53, B053.class);
		controlMap.put(54, B054.class);
		controlMap.put(55, B055.class);
		controlMap.put(57, B057.class);
		controlMap.put(58, B058.class);
		controlMap.put(59, B059.class);
		controlMap.put(60, B060.class);

		controlMap.put(61, B061.class);
		controlMap.put(62, B062.class);

		controlMap.put(100, B100.class);
		controlMap.put(200, B200.class);
		controlMap.put(201, B201.class);
		controlMap.put(202, B202.class);
		controlMap.put(300, B300.class);
		controlMap.put(310, B310.class);
		controlMap.put(320, B320.class);
		controlMap.put(390, B390.class);
		controlMap.put(395, B395.class);
		controlMap.put(400, B400.class);
		controlMap.put(405, B405.class);
		controlMap.put(500, B500.class);

		controlMap.put(1000, B1000.class);
		controlMap.put(1001, B1001.class);
		controlMap.put(2000, B2000.class);

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

	public static <T> BlocketteBuilder<? extends ControlBlockette> controlBlocketteBuilder(int type)
			throws SeedException {
		try {
			Class<? extends BlocketteBuilder<? extends ControlBlockette>> clazz = controlBlocketteBuilders.get(type);
			if (clazz == null) {
				throw new SeedException("Unkown blockette type {}", type);
			}
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new SeedException(e);
		}
	}

	public static BlocketteBuilder<? extends DataBlockette> datBlocketteBuilder(int type) throws SeedException {
		try {
			return dataBlocketteBuilders.get(type).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new SeedException(e);
		}
	}

	@Override
	public String toString() {
		return "SeedBlockette [type=" + type + ", title=" + title + ", getType()=" + getType() + ", getTitle()="
				+ getTitle() + "]";
	}

}
