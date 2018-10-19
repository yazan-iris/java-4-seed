package edu.iris.dmc.seed;

import edu.iris.dmc.seed.builder.BlocketteBuilder;

public class BlocketteFactory {

	public static Blockette create(byte[] bytes) throws SeedException {
		String s = new String(bytes, 0, 3);
		int type = Integer.parseInt(s);
		return createBlockette(type, bytes);
	}

	public static Blockette createBlockette(int type, byte[] bytes, int start, int end) throws SeedException {
		return null;
	}

	public static Blockette createBlockette(int type, byte[] bytes) throws SeedException {
		switch (type) {
		case 5:
			return BlocketteBuilder.build005(bytes);
		case 8:
			return BlocketteBuilder.build008(bytes);
		case 10:
			return BlocketteBuilder.build010(bytes);
		case 11:
			return BlocketteBuilder.build011(bytes);
		case 12:
			return BlocketteBuilder.build012(bytes);
		case 30:
			return BlocketteBuilder.build030(bytes);
		case 31:
			return BlocketteBuilder.build031(bytes);
		case 32:
			return BlocketteBuilder.build032(bytes);
		case 33:
			return BlocketteBuilder.build033(bytes);
		case 34:
			return BlocketteBuilder.build034(bytes);
		case 35:
			return BlocketteBuilder.build035(bytes);
		case 41:
			return BlocketteBuilder.build041(bytes);
		case 42:
			return BlocketteBuilder.build042(bytes);
		case 43:
			return BlocketteBuilder.build043(bytes);
		case 44:
			return BlocketteBuilder.build044(bytes);
		case 45:
			return BlocketteBuilder.build045(bytes);
		case 46:
			return BlocketteBuilder.build046(bytes);
		case 47:
			return BlocketteBuilder.build047(bytes);
		case 48:
			return BlocketteBuilder.build048(bytes);
		case 49:
			return BlocketteBuilder.build049(bytes);
		case 50:
			return BlocketteBuilder.build050(bytes);
		case 51:
			return BlocketteBuilder.build051(bytes);
		case 52:
			return BlocketteBuilder.build052(bytes);
		case 53:
			return BlocketteBuilder.build053(bytes);
		case 54:
			return BlocketteBuilder.build054(bytes);
		case 55:
			return BlocketteBuilder.build055(bytes);
		case 56:
			return BlocketteBuilder.build056(bytes);
		case 57:
			return BlocketteBuilder.build057(bytes);
		case 58:
			return BlocketteBuilder.build058(bytes);
		case 59:
			return BlocketteBuilder.build059(bytes);
		case 60:
			return BlocketteBuilder.build060(bytes);
		case 61:
			return BlocketteBuilder.build061(bytes);
		case 62:
			return BlocketteBuilder.build062(bytes);
		default:
			break;
		}
		throw new SeedException("Unkown type: [" + type + "]");
	}
}
