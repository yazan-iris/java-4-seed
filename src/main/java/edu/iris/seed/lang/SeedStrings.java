package edu.iris.seed.lang;

import java.io.UnsupportedEncodingException;

import edu.iris.seed.SeedException;

public class SeedStrings {

	public static int parseInt(byte[] bytes, int start, int length) throws SeedException {
		
		if (bytes == null || length + start > bytes.length) {
			throw new SeedException("Error reading buffer: " + new String(bytes) + " at:" + start + ", length:" + length);
		}
		String s = null;
		try {
			s = new String(bytes, start, length, "us-ascii").trim();
		} catch (UnsupportedEncodingException e) {
			throw new SeedException("Couldn't parse:" + new String(bytes));
		}
		s = s.trim();
		if (s.isEmpty()) {
			return 0;
		}
		int i = 0;
		try {
			i = Integer.parseInt(s.trim());
		} catch (NumberFormatException nfe) {
			throw new SeedException("Couldn't parse: [" + s + "]  " + new String(bytes));
		}
		return i;
	}

	public static float parseFloat(byte[] buf, int begin, int length) throws SeedException {
		if (buf == null || length + begin > buf.length) {
			throw new SeedException("Error reading buffer: " + new String(buf) + " at:" + begin + ", length:" + length);
		}

		try {
			String s = new String(buf, begin, length, "us-ascii");
			return Float.parseFloat(s);
		} catch (NumberFormatException | UnsupportedEncodingException e) {
			throw new SeedException("Error reading buffer: " + new String(buf) + " at:" + begin + ", length:" + length);
		}
	}

	public static double parseDouble(byte[] buf, int begin, int length) throws SeedException {
		if (buf == null || length + begin > buf.length) {
			throw new SeedException("Error reading buffer: " + new String(buf) + " at:" + begin + ", length:" + length);
		}
		try {
			String s = new String(buf, begin, length, "us-ascii");
			return Double.parseDouble(s);
		} catch (NumberFormatException | UnsupportedEncodingException e) {
			throw new SeedException("",e);
		}

	}

	public static String readString(byte[] buf, int begin, int length) throws SeedException {
		if (buf == null || length + begin > buf.length) {
			throw new SeedException("Error reading buffer: " + new String(buf) + " at:" + begin + ", length:" + length);
		}
		try {
			String text = new String(buf, begin, length, "us-ascii");

			return text.trim();
		} catch (UnsupportedEncodingException e) {
			throw new SeedException("Error reading buffer: " + new String(buf) + " at:" + begin + ", length:" + length);
		}
	}

	public static int advance(byte[] bytes, int offset, int cnt) throws SeedException {
		for (int i = 0; i < cnt; i++) {
			offset++;
		}
		if (offset >= bytes.length) {
			throw new SeedException("Unable to advance offset");
		}
		return offset;
	}

	public static String cleanTextContent(String text) {
		// strips off all non-ASCII characters
		text = text.replaceAll("[^\\x00-\\x7F]", "");

		// erases all the ASCII control characters
		text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");

		// removes non-printable characters from Unicode
		text = text.replaceAll("\\p{C}", "");

		return text.trim();
	}
}
