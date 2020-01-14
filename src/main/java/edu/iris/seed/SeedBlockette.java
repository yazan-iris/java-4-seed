package edu.iris.seed;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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
import edu.iris.seed.index.B005;
import edu.iris.seed.index.B008;
import edu.iris.seed.index.B010;
import edu.iris.seed.index.B011;
import edu.iris.seed.index.B012;
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

public abstract class SeedBlockette {

	private final int type;
	private final String title;
	// private int length;

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

	/*
	 * public int getLength() { return length; }
	 */

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

	public static class Builder {

		private Builder() {

		}

		public static Builder newInstance() {
			return new Builder();
		}

		public Blockette build(byte[] bytes) throws SeedException {
			String s = new String(bytes, 0, 3);
			int type = Integer.parseInt(s);
			return build(type, bytes);
		}

		public Blockette build(int type, byte[] bytes) throws SeedException {
			Class<? extends Blockette> clazz = controlMap.get(type);
			if (clazz == null) {
				throw new SeedException("Invalid blockette type [{}] ", type);
			}
			try {
				Method method = clazz.getMethod("build", new Class[] { byte[].class });
				return (Blockette) method.invoke(null, bytes);
			} catch (Exception e) {
				e.printStackTrace();
				throw new SeedException(e);
			}
		}

		public Blockette build(int type, byte[] bytes, int index) throws SeedException {
			Class<? extends Blockette> clazz = controlMap.get(type);
			if (clazz == null) {
				throw new SeedException("Invalid blockette type [{}] ", type);
			}
			try {
				Method method = clazz.getMethod("build", new Class[] { byte[].class });
				return (Blockette) method.invoke(null, bytes, index);
			} catch (Exception e) {
				e.printStackTrace();
				throw new SeedException(e);
			}
		}
	}
}
