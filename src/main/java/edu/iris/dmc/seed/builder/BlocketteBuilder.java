package edu.iris.dmc.seed.builder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import edu.iris.dmc.seed.BTime;
import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.SeedException;
import edu.iris.dmc.seed.control.dictionary.B030;
import edu.iris.dmc.seed.control.dictionary.B031;
import edu.iris.dmc.seed.control.dictionary.B032;
import edu.iris.dmc.seed.control.dictionary.B033;
import edu.iris.dmc.seed.control.dictionary.B034;
import edu.iris.dmc.seed.control.dictionary.B035;
import edu.iris.dmc.seed.control.dictionary.B041;
import edu.iris.dmc.seed.control.dictionary.B042;
import edu.iris.dmc.seed.control.dictionary.B043;
import edu.iris.dmc.seed.control.dictionary.B044;
import edu.iris.dmc.seed.control.dictionary.B045;
import edu.iris.dmc.seed.control.dictionary.B046;
import edu.iris.dmc.seed.control.dictionary.B047;
import edu.iris.dmc.seed.control.dictionary.B048;
import edu.iris.dmc.seed.control.dictionary.B049;
import edu.iris.dmc.seed.control.dictionary.Component;
import edu.iris.dmc.seed.control.index.B005;
import edu.iris.dmc.seed.control.index.B008;
import edu.iris.dmc.seed.control.index.B010;
import edu.iris.dmc.seed.control.index.B011;
import edu.iris.dmc.seed.control.index.B012;
import edu.iris.dmc.seed.control.index.Span;
import edu.iris.dmc.seed.control.station.B050;
import edu.iris.dmc.seed.control.station.B051;
import edu.iris.dmc.seed.control.station.B052;
import edu.iris.dmc.seed.control.station.B053;
import edu.iris.dmc.seed.control.station.B054;
import edu.iris.dmc.seed.control.station.B055;
import edu.iris.dmc.seed.control.station.B056;
import edu.iris.dmc.seed.control.station.B057;
import edu.iris.dmc.seed.control.station.B058;
import edu.iris.dmc.seed.control.station.B059;
import edu.iris.dmc.seed.control.station.B060;
import edu.iris.dmc.seed.control.station.B061;
import edu.iris.dmc.seed.control.station.B062;
import edu.iris.dmc.seed.control.station.Calibration;
import edu.iris.dmc.seed.control.station.Number;
import edu.iris.dmc.seed.control.station.Pole;
import edu.iris.dmc.seed.control.station.Stage;
import edu.iris.dmc.seed.control.station.Zero;

public class BlocketteBuilder implements Builder<Blockette> {
	private static Logger LOG = Logger.getLogger(BlocketteBuilder.class.getName());
	private static Builder<Blockette> defaultInstance = new BlocketteBuilder();

	public List<Blockette> createAll(byte[] bytes) throws SeedException, IOException {
		List<Blockette> list = new ArrayList<>();
		int index = 0;
		while (true) {
			if ((index + 7) >= bytes.length) {
				break;
			}

			String typeString = new String(bytes, index, 3).trim();
			if (typeString.trim().isEmpty()) {
				break;
			}
			int type = Integer.parseInt(typeString);
			String lengthString = new String(bytes, index + 3, 4);
			int length = Integer.parseInt(lengthString);
			if (index + length > bytes.length) {
				// error or continuation needed

			}

			Blockette blockette = getBlockette(type, Arrays.copyOfRange(bytes, index, index + length));
			list.add(blockette);
			index += length;
		}

		return list;
	}

	@Override
	public Blockette create(byte[] bytes) throws SeedException, IOException {
		String s = new String(bytes, 0, 3);
		int type = Integer.parseInt(s);
		return getBlockette(type, bytes);
	}

	public Blockette getBlockette(int type, byte[] bytes) throws SeedException, IOException {
		switch (type) {
		case 5:
			return build005(bytes);
		case 8:
			return build008(bytes);
		case 10:
			return build010(bytes);
		case 11:
			return build011(bytes);
		case 12:
			return build012(bytes);
		case 30:
			return build030(bytes);
		case 31:
			return build031(bytes);
		case 32:
			return build032(bytes);
		case 33:
			return build033(bytes);
		case 34:
			return build034(bytes);
		case 35:
			return build035(bytes);
		case 41:
			return build041(bytes);
		case 42:
			return build042(bytes);
		case 43:
			return build043(bytes);
		case 44:
			return build044(bytes);
		case 45:
			return build045(bytes);
		case 46:
			return build046(bytes);
		case 47:
			return build047(bytes);
		case 48:
			return build048(bytes);
		case 49:
			return build049(bytes);
		case 50:
			return build050(bytes);
		case 51:
			return build051(bytes);
		case 52:
			return build052(bytes);
		case 53:
			return build053(bytes);
		case 54:
			return build054(bytes);
		case 55:
			return build055(bytes);
		case 56:
			return build056(bytes);
		case 57:
			return build057(bytes);
		case 58:
			return build058(bytes);
		case 59:
			return build059(bytes);
		case 60:
			return build060(bytes);
		case 61:
			return build061(bytes);
		case 62:
			return build062(bytes);
		default:
			break;
		}
		throw new SeedException("Unkown type: [" + type + "]");
	}

	public static byte[] trim(byte[] bytes) {
		int i = bytes.length;
		while (i-- > 0 && bytes[i] == 32) {
		}

		byte[] output = new byte[i + 1];
		System.arraycopy(bytes, 0, output, 0, i + 1);
		return output;
	}

	public static synchronized Builder<Blockette> getInstance() {
		return defaultInstance;
	}

	public static B005 build005(byte[] bytes) throws SeedException {
		B005 b = new B005();

		int offset = 7;
		b.setVersion(new String(bytes, offset, 4));
		offset = offset + 4;
		int recordLength = BlocketteBuilder.parseInt(bytes, offset, 2);
		b.setLogicalRecordLength(recordLength);
		offset = offset + 2;
		int from = offset;
		for (;; offset++) {
			if (bytes[offset] == '~') {
				break;
			}
		}
		b.setStartTime(BTime.valueOf(Arrays.copyOfRange(bytes, from, offset)));
		return b;
	}

	public static B008 build008(byte[] bytes) throws SeedException {
		B008 b = new B008();

		int offset = 7;

		b.setVersion(new String(bytes, offset, 4));
		offset = offset + 4;
		int recordLength = BlocketteBuilder.parseInt(bytes, offset, 2);
		b.setLogicalRecordLength(recordLength);
		offset = offset + 2;

		b.setStationCode(new String(bytes, offset, 5));
		offset = offset + 5;
		b.setLocationCode(new String(bytes, offset, 2));
		offset = offset + 2;
		b.setChannelCode(new String(bytes, offset, 3));
		offset = offset + 3;
		int from = offset;
		for (;; offset++) {
			if (bytes[offset] == '~') {
				break;
			}
		}
		b.setVolumeStartTime(BTime.valueOf(Arrays.copyOfRange(bytes, from, offset)));
		offset++;
		from = offset;
		for (;; offset++) {
			if (bytes[offset] == '~') {
				break;
			}
		}
		b.setVolumeEndTime(BTime.valueOf(Arrays.copyOfRange(bytes, from, offset)));
		offset++;
		from = offset;
		for (;; offset++) {
			if (bytes[offset] == '~') {
				break;
			}
		}
		b.setStationInformationEffectiveDate(BTime.valueOf(Arrays.copyOfRange(bytes, from, offset)));
		offset++;
		from = offset;
		for (;; offset++) {
			if (bytes[offset] == '~') {
				break;
			}
		}
		b.setChannelInformationEffectiveDate(BTime.valueOf(Arrays.copyOfRange(bytes, from, offset)));
		offset++;
		b.setNetworkCode(new String(bytes, offset, 2));
		return b;
	}

	public static B010 build010(byte[] bytes) throws SeedException {
		B010 b = new B010();

		int offset = 7;
		b.setVersion(new String(bytes, offset, 4));
		offset = offset + 4;
		int nthPower = BlocketteBuilder.parseInt(bytes, offset, 2);
		b.setNthPower(nthPower);
		offset = offset + 2;
		int from = offset;
		for (;; offset++) {
			if (bytes[offset] == '~') {
				break;
			}
		}
		b.setStartTime(BTime.valueOf(Arrays.copyOfRange(bytes, from, offset)));
		offset++;

		from = offset;
		for (;; offset++) {
			if (bytes[offset] == '~') {
				break;
			}
		}
		b.setEndTime(BTime.valueOf(Arrays.copyOfRange(bytes, from, offset)));
		offset++;
		from = offset;
		for (;; offset++) {
			if (bytes[offset] == '~') {
				break;
			}
		}
		b.setVolumeTime(BTime.valueOf(Arrays.copyOfRange(bytes, from, offset)));
		offset++;

		from = offset;
		for (;; offset++) {
			if (bytes[offset] == '~') {
				break;
			}
		}
		b.setOrganization((new String(Arrays.copyOfRange(bytes, from, offset))));
		offset++;

		from = offset;
		for (;; offset++) {
			if (bytes[offset] == '~') {
				break;
			}
		}
		b.setLabel((new String(Arrays.copyOfRange(bytes, from, offset))));
		return b;
	}

	public static B011 build011(byte[] bytes) throws SeedException {

		B011 b = new B011();

		int offset = 7;
		int numberOfStations = -1;
		numberOfStations = BlocketteBuilder.parseInt(bytes, 7, 3);
		b.setNumberOfStations(numberOfStations);
		offset = 10;
		if (numberOfStations > 0) {
			for (int i = 0; i < numberOfStations; i++) {
				String code = new String(bytes, offset, 5);
				offset += 5;
				String sequenceString = new String(bytes, offset, 6).trim();
				offset += 6;
				sequenceString = sequenceString.replaceFirst("^0+(?!$)", "");
				try {
					int sequence = Integer.parseInt(sequenceString);
					b.add(code, sequence);
				} catch (NumberFormatException e) {
					throw new SeedException(e);
				}
			}
		}
		return b;
	}

	public static B012 build012(byte[] bytes) throws SeedException {
		B012 b = new B012();

		int offset = 7;
		int numberOfSpans = -1;
		numberOfSpans = BlocketteBuilder.parseInt(bytes, 7, 4);
		offset = 11;
		for (int i = 0; i < numberOfSpans; i++) {
			int from = offset;
			for (;; offset++) {
				if (bytes[offset] == '~') {
					break;
				}
			}
			BTime start = BTime.valueOf(Arrays.copyOfRange(bytes, from, offset));
			offset++;

			from = offset;
			for (;; offset++) {
				if (bytes[offset] == '~') {
					break;
				}
			}
			BTime end = BTime.valueOf(Arrays.copyOfRange(bytes, from, offset));
			offset++;
			String sequence = new String(bytes, offset, 6);
			offset += 6;

			b.add(sequence, new Span(start, end));
		}

		return b;
	}

	public static B030 build030(byte[] bytes) throws SeedException {
		B030 b = new B030(new String(bytes));

		int offset = 7;

		int from = offset;
		for (;; offset++) {
			if (bytes[offset] == '~') {
				break;
			}
		}

		b.setName(new String(bytes, from, offset - from));
		offset++;
		b.setLookupKey(BlocketteBuilder.parseInt(bytes, offset, 4));
		offset = offset + 4;
		b.setDataFamilyType(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;
		int numberOfKeys = BlocketteBuilder.parseInt(bytes, offset, 2);
		offset = offset + 2;
		int i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == '~') {
				b.addKey(new String(bytes, i, offset - i));
				i = offset + 1;
			}
		}

		return b;
	}

	public static B031 build031(byte[] bytes) throws SeedException {

		B031 b = new B031(new String(bytes));

		int offset = 7;

		b.setLookupKey(BlocketteBuilder.parseInt(bytes, offset, 4));
		offset = offset + 4;
		b.setClassCode((char) bytes[offset]);
		offset++;

		int i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == '~') {
				break;
			}
		}
		b.setDescription(new String(bytes, i, offset - i));
		offset++;
		b.setUnitsOfCommentLevel(BlocketteBuilder.parseInt(bytes, offset, 3));
		return b;
	}

	public static B032 build032(byte[] bytes) throws SeedException {

		B032 b = new B032(new String(bytes));

		int offset = 7;

		b.setLookupKey(BlocketteBuilder.parseInt(bytes, offset, 2));
		offset = offset + 2;

		int i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == '~') {
				break;
			}
		}
		b.setTitleAuthor(new String(bytes, i, offset - i));
		offset++;

		i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == '~') {
				break;
			}
		}
		b.setPublishedCatalog(new String(bytes, i, offset - i));
		offset++;

		i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == '~') {
				break;
			}
		}
		b.setPublisherName(new String(bytes, i, offset - i));
		return b;
	}

	public static B033 build033(byte[] bytes) throws SeedException {
		int offset = 7;
		B033 b = new B033(new String(bytes));

		Integer lookupCode = BlocketteBuilder.parseInt(bytes, offset, 3);
		b.setLookupKey(lookupCode);
		offset += 3;
		int i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == (byte) '~') {
				break;
			}
		}
		b.setDescription(new String(bytes, i, offset - i));
		return b;
	}

	public static B034 build034(byte[] bytes) throws SeedException {
		int offset = 7;
		B034 b = new B034(new String(bytes));

		b.setLookupKey(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset += 3;
		int i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == (byte) '~') {
				break;
			}
		}

		b.setName(new String(bytes, i, offset - i));
		offset++;

		i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == (byte) '~') {
				break;
			}
		}

		b.setDescription(new String(bytes, i, offset - i));
		return b;
	}

	public static B035 build035(byte[] bytes) throws SeedException {
		int offset = 7;
		B035 b = new B035();

		b.setLookupKey(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset += 3;

		int numberOfComponents = BlocketteBuilder.parseInt(bytes, offset, 4);
		offset += 4;

		for (int i = 0; i < numberOfComponents; i++) {
			String stationCode = new String(bytes, offset, 5);
			offset = offset + 5;
			String locationCode = new String(bytes, offset, 2);
			offset = offset + 2;
			String channelCode = new String(bytes, offset, 3);
			offset = offset + 3;

			int subChannelId = BlocketteBuilder.parseInt(bytes, offset, 4);
			offset = offset + 4;
			double weight = BlocketteBuilder.parseDouble(bytes, offset, 5);
			offset = offset + 5;
			b.add(new Component(stationCode, locationCode, channelCode, subChannelId, weight));
		}

		return b;
	}

	public static B041 build041(byte[] bytes) throws SeedException {
		int offset = 7;
		B041 b = new B041(new String(bytes));

		b.setLookupKey(BlocketteBuilder.parseInt(bytes, offset, 4));
		offset = offset + 4;

		int i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == (byte) '~') {
				break;
			}
		}
		b.setName(new String(bytes, i, offset - i));
		offset++;

		b.setSymetryCode((char) bytes[offset]);
		offset++;
		b.setSignalInputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;
		b.setSignalOutputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;

		int numberOfCoefficients = BlocketteBuilder.parseInt(bytes, offset, 4);
		offset = offset + 4;

		for (i = 0; i < numberOfCoefficients; i++) {
			b.addCoefficient(BlocketteBuilder.parseDouble(bytes, offset, 14));
			offset = offset + 14;
		}
		return b;
	}

	public static B042 build042(byte[] bytes) throws SeedException {
		if (bytes == null || bytes.length == 0) {
			throw new IllegalArgumentException("object null|empty");
		}
		int offset = 7;
		B042 b = new B042();

		b.setTransferFunctionType((char) bytes[offset]);
		offset++;

		b.setLookupKey(BlocketteBuilder.parseInt(bytes, offset, 4));
		offset = offset + 4;

		b.setSignalInputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;

		b.setSignalOutputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;

		b.setApproximationType((char) bytes[offset]);
		offset++;

		b.setFrequencyUnit((char) bytes[offset]);
		offset++;

		b.setLowerValidFrequencyBound(BlocketteBuilder.parseDouble(bytes, offset, 12));
		offset = offset + 12;
		b.setUpperValidFrequencyBound(BlocketteBuilder.parseDouble(bytes, offset, 12));
		offset = offset + 12;

		b.setLowerBoundOfApproximation(BlocketteBuilder.parseDouble(bytes, offset, 12));
		offset = offset + 12;
		b.setUpperBoundOfApproximation(BlocketteBuilder.parseDouble(bytes, offset, 12));
		offset = offset + 12;
		b.setMaximumAbsoluteError(BlocketteBuilder.parseDouble(bytes, offset, 12));
		offset = offset + 12;

		int numberOfCoefficients = BlocketteBuilder.parseInt(bytes, offset, 3);
		offset = offset + 3;

		for (int i = 0; i < numberOfCoefficients; i++) {
			Float value = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			Float error = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			b.add(new Number(value, error));
		}
		return b;
	}

	public static B043 build043(byte[] bytes) throws SeedException {
		int offset = 7;
		B043 b = new B043();

		b.setLookupKey(BlocketteBuilder.parseInt(bytes, offset, 4));
		offset = offset + 4;

		int i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == (byte) '~') {
				break;
			}
		}
		String responseName = new String(bytes, i, offset);
		b.setResponseName(responseName);
		// skip ~
		offset++;

		b.setTransferFunctionType((char) bytes[offset]);
		offset++;

		b.setSignalInputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;
		b.setSignalOutputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;

		b.setNormalizationFactor(BlocketteBuilder.parseFloat(bytes, offset, 12));
		offset = offset + 12;

		b.setNormalizationFrequency(BlocketteBuilder.parseFloat(bytes, offset, 12));
		offset = offset + 12;

		int numberOfZeros = BlocketteBuilder.parseInt(bytes, offset, 3);
		offset = offset + 3;

		for (i = 0; i < numberOfZeros; i++) {
			float real = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			float imaginary = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			float realError = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			float imaginaryError = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			b.add(new Zero(real, realError, imaginary, imaginaryError));
		}

		int numberOfPoles = BlocketteBuilder.parseInt(bytes, offset, 3);
		offset = offset + 3;
		for (i = 0; i < numberOfPoles; i++) {
			float real = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			float imaginary = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			float realError = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			float imaginaryError = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			b.add(new Pole(real, realError, imaginary, imaginaryError));
		}

		return b;
	}

	public static B044 build044(byte[] bytes) throws SeedException {
		int offset = 7;
		B044 b = new B044();

		b.setLookupKey(BlocketteBuilder.parseInt(bytes, offset, 4));
		offset = offset + 4;

		int i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == (byte) '~') {
				break;
			}
		}
		String responseName = new String(bytes, i, offset);
		b.setResponseName(responseName);
		// skip ~
		offset++;

		b.setResponseType((char) bytes[offset]);
		offset++;

		b.setSignalInputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;
		b.setSignalOutputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;

		int numberOfNumerators = BlocketteBuilder.parseInt(bytes, offset, 4);
		offset = offset + 4;

		for (i = 0; i < numberOfNumerators; i++) {
			float numerator = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			float error = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			b.addNumerator(new Number(numerator, error));
		}

		int numberOfDenominators = BlocketteBuilder.parseInt(bytes, offset, 4);
		offset = offset + 4;
		for (i = 0; i < numberOfDenominators; i++) {
			float denominator = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			float error = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			b.addDenominator(new Number(denominator, error));
		}

		return b;
	}

	public static B045 build045(byte[] bytes) throws SeedException {
		int offset = 7;
		B045 b = new B045();

		b.setLookupKey(BlocketteBuilder.parseInt(bytes, offset, 4));
		offset = offset + 4;
		b.setSignalInputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;
		b.setSignalOutputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;

		int numberOfNumerators = BlocketteBuilder.parseInt(bytes, offset, 4);
		offset = offset + 4;

		for (int i = 0; i < numberOfNumerators; i++) {
			double frequency = BlocketteBuilder.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			double amplitude = BlocketteBuilder.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			double amplitudeError = BlocketteBuilder.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			double phaseAngle = BlocketteBuilder.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			double phaseAngleError = BlocketteBuilder.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			b.add(b.new Response(frequency, amplitude, amplitudeError, phaseAngle, phaseAngleError));
		}

		return b;
	}

	public static B046 build046(byte[] bytes) throws SeedException {
		int offset = 7;
		B046 b = new B046();

		return b;
	}

	public static B047 build047(byte[] bytes) throws SeedException {
		int offset = 7;
		B047 b = new B047();

		b.setLookupKey(BlocketteBuilder.parseInt(bytes, offset, 4));
		offset = offset + 4;

		int i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == (byte) '~') {
				break;
			}
		}
		String responseName = new String(bytes, i, offset);
		b.setResponseName(responseName);
		// skip ~
		offset++;

		double sampleRate = BlocketteBuilder.parseDouble(bytes, offset, 10);
		b.setSampleRate(sampleRate);
		offset = offset + 10;
		b.setDecimationFactor(BlocketteBuilder.parseInt(bytes, offset, 5));
		offset = offset + 5;
		b.setDecimationOffset(BlocketteBuilder.parseInt(bytes, offset, 5));
		offset = offset + 5;

		b.setEstimatedDelay(BlocketteBuilder.parseDouble(bytes, offset, 11));
		offset = offset + 11;
		b.setCorrection(BlocketteBuilder.parseDouble(bytes, offset, 11));

		return b;
	}

	public static B048 build048(byte[] bytes) throws SeedException {
		int offset = 7;
		B048 b = new B048();

		b.setLookupKey(BlocketteBuilder.parseInt(bytes, offset, 4));
		offset = offset + 4;

		int i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == (byte) '~') {
				break;
			}
		}
		String responseName = new String(bytes, i, offset);
		b.setResponseName(responseName);
		// skip ~
		offset++;

		double sensitivity = BlocketteBuilder.parseDouble(bytes, offset, 12);
		b.setSensitivity(sensitivity);
		offset = offset + 12;
		double frequency = BlocketteBuilder.parseDouble(bytes, offset, 12);
		offset = offset + 12;
		b.setFrequency(frequency);

		int numberOfHistoryValues = BlocketteBuilder.parseInt(bytes, offset, 2);
		offset = offset + 2;

		for (i = 0; i < numberOfHistoryValues; i++) {
			double s = BlocketteBuilder.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			double f = BlocketteBuilder.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			int x = offset;
			for (; offset < bytes.length; offset++) {
				if (bytes[offset] == (byte) '~') {
					break;
				}
			}
			byte[] copy = Arrays.copyOfRange(bytes, x, offset);
			b.add(new Calibration(s, f, BTime.valueOf(copy)));
		}
		return b;
	}

	public static B049 build049(byte[] bytes) throws SeedException {
		if (bytes == null || bytes.length == 0) {
			throw new IllegalArgumentException("object null|empty");
		}
		int offset = 7;
		B049 b = new B049();

		b.setTransferFunctionType((char) bytes[offset]);
		offset++;

		b.setLookupKey(BlocketteBuilder.parseInt(bytes, offset, 4));
		offset = offset + 4;

		b.setSignalInputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;

		b.setSignalOutputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;

		b.setApproximationType((char) bytes[offset]);
		offset++;

		b.setFrequencyUnit((char) bytes[offset]);
		offset++;

		b.setLowerValidFrequencyBound(BlocketteBuilder.parseDouble(bytes, offset, 12));
		offset = offset + 12;
		b.setUpperValidFrequencyBound(BlocketteBuilder.parseDouble(bytes, offset, 12));
		offset = offset + 12;

		b.setLowerBoundOfApproximation(BlocketteBuilder.parseDouble(bytes, offset, 12));
		offset = offset + 12;
		b.setUpperBoundOfApproximation(BlocketteBuilder.parseDouble(bytes, offset, 12));
		offset = offset + 12;
		b.setMaximumAbsoluteError(BlocketteBuilder.parseDouble(bytes, offset, 12));
		offset = offset + 12;

		int numberOfCoefficients = BlocketteBuilder.parseInt(bytes, offset, 3);
		offset = offset + 3;

		for (int i = 0; i < numberOfCoefficients; i++) {
			Float value = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			Float error = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			b.add(new Number(value, error));
		}
		return b;
	}

	public static B050 build050(byte[] bytes) throws SeedException {
		int offset = 7;
		B050 b = new B050(new String(bytes));

		String stationCode = BlocketteBuilder.readString(bytes, offset, 5);
		b.setStationCode(stationCode);
		// offset = offset + 5;
		offset = advance(bytes, offset, 5);
		Double latitude = BlocketteBuilder.parseDouble(bytes, offset, 10);
		if (latitude != null) {
			b.setLatitude(latitude);
		}

		offset = offset + 10;
		Double longitude = BlocketteBuilder.parseDouble(bytes, offset, 11);
		if (longitude != null) {
			b.setLongitude(longitude);
		}
		offset = offset + 11;
		Double elevation = BlocketteBuilder.parseDouble(bytes, offset, 7);
		if (elevation != null) {
			b.setElevation(elevation);
		}
		offset = offset + 7;
		int numberOfChannels = BlocketteBuilder.parseInt(bytes, offset, 4);
		b.setNumberOfChannels(numberOfChannels);
		offset = offset + 4;
		int numberOfComments = BlocketteBuilder.parseInt(bytes, offset, 3);
		b.setNumberOfComments(numberOfComments);
		offset = offset + 3;

		int i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == (byte) '~') {
				break;
			}
		}
		if (offset >= bytes.length) {
			throw new SeedException("B050: Invalid byte array. Field 09,  missing ~");
		}
		String siteName = new String(bytes, i, offset - i);
		if (siteName != null && !siteName.isEmpty()) {
			b.setSiteName(cleanTextContent(siteName));
		}
		offset = advance(bytes, offset, 1);
		Integer networkIdentifierCode = BlocketteBuilder.parseInt(bytes, offset, 3);
		b.setNetworkIdentifierCode(networkIdentifierCode);

		// offset = offset + 3;
		offset = advance(bytes, offset, 3);
		int wordOrder32 = BlocketteBuilder.parseInt(bytes, offset, 4);
		b.setBit32BitOrder(wordOrder32);
		offset = offset + 4;
		int wordOrder16 = BlocketteBuilder.parseInt(bytes, offset, 2);
		b.setBit16BitOrder(wordOrder16);
		offset = offset + 2;

		i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == (byte) '~') {
				break;
			}
		}
		if (offset >= bytes.length) {
			throw new SeedException("B050: Invalid byte array. Field 13,  missing ~");
		}
		byte[] copy = Arrays.copyOfRange(bytes, i, offset);
		b.setStartTime(BTime.valueOf(copy));
		// skip ~
		offset = advance(bytes, offset, 1);

		i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == (byte) '~') {
				break;
			}
		}
		if (offset >= bytes.length) {
			throw new SeedException("B050: Invalid byte array. Field 14,  missing ~");
		}
		copy = Arrays.copyOfRange(bytes, i, offset);
		if (copy != null) {
			b.setEndTime(BTime.valueOf(copy));
		}
		offset = advance(bytes, offset, 1);
		// offset++;
		b.setUpdateFlag((char) bytes[offset]);
		offset = advance(bytes, offset, 1);
		// offset++;
		String networkCode = new String(bytes, offset, 2).trim();
		b.setNetworkCode(networkCode);

		return b;
	}

	public static B051 build051(byte[] bytes) throws SeedException {
		int offset = 7;
		int i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == (byte) '~') {
				break;
			}
		}
		B051 b = new B051(new String(bytes));

		byte[] copy = Arrays.copyOfRange(bytes, i, offset);
		b.setStartTime(BTime.valueOf(copy));

		offset++;
		i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == (byte) '~') {
				break;
			}
		}
		copy = Arrays.copyOfRange(bytes, i, offset);
		b.setEndTime(BTime.valueOf(copy));

		offset++;
		int key = BlocketteBuilder.parseInt(bytes, offset, 4);
		b.setLookupKey(key);
		offset = offset + 4;

		if (offset >= bytes.length) {
			return b;
		}
		int level = BlocketteBuilder.parseInt(bytes, offset, 6);
		b.setLevel(level);

		return b;
	}

	public static B052 build052(byte[] bytes) throws SeedException {
		String dataToParse = new String(bytes);
		int offset = 7;
		B052 b = new B052(dataToParse);

		String location = BlocketteBuilder.readString(bytes, offset, 2);
		b.setLocationCode(location);
		offset += 2;
		String code = BlocketteBuilder.readString(bytes, offset, 3);
		b.setChannelCode(code);
		offset += 3;

		b.setSubChannelCode(BlocketteBuilder.parseInt(bytes, offset, 4));
		offset = offset + 4;
		b.setInstrumentIdentifier(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;

		int i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == (byte) '~') {
				break;
			}
		}
		String channelOptionalComment = dataToParse.substring(i, offset);
		if (channelOptionalComment != null && channelOptionalComment.trim().length() > 0) {
			b.setOptionalComment(channelOptionalComment);
		}
		// skip ~
		offset++;

		b.setUnitsOfSignalResponse(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;
		b.setUnitsOfCalibrationInput(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;

		b.setLatitude(BlocketteBuilder.parseDouble(bytes, offset, 10));
		offset = offset + 10;

		b.setLongitude(BlocketteBuilder.parseDouble(bytes, offset, 11));
		offset = offset + 11;

		b.setElevation(BlocketteBuilder.parseDouble(bytes, offset, 7));
		offset = offset + 7;

		b.setLocalDepth(BlocketteBuilder.parseDouble(bytes, offset, 5));
		offset = offset + 5;

		b.setAzimuth(BlocketteBuilder.parseDouble(bytes, offset, 5));
		offset = offset + 5;

		b.setDip(BlocketteBuilder.parseDouble(bytes, offset, 5));
		offset = offset + 5;

		b.setDataFormatIdentifier(BlocketteBuilder.parseInt(bytes, offset, 4));
		offset = offset + 4;
		b.setDataRecordLength(BlocketteBuilder.parseInt(bytes, offset, 2));
		offset = offset + 2;

		b.setSampleRate(BlocketteBuilder.parseDouble(bytes, offset, 10));
		offset += 10;

		b.setMaxClockDrift(BlocketteBuilder.parseDouble(bytes, offset, 10));
		offset += 10;

		int numberOfComments = BlocketteBuilder.parseInt(bytes, offset, 4);
		if (numberOfComments > 0) {
			b.setNumberOfComments(numberOfComments);
		}
		offset += 4;

		// int index = dataToParse.indexOf('~');

		i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == (byte) '~') {
				break;
			}
		}

		b.setChannelFlags(new String(bytes, i, offset - i));
		// skip ~
		offset++;

		i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == (byte) '~') {
				break;
			}
		}
		byte[] copy = Arrays.copyOfRange(bytes, i, offset);
		// String startDate = dataToParse.substring(i, offset);
		b.setStartTime(BTime.valueOf(copy));
		// skip ~
		offset++;

		i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == (byte) '~') {
				break;
			}
		}
		copy = Arrays.copyOfRange(bytes, i, offset);
		// String endDate = dataToParse.substring(i, offset);
		b.setEndTime(BTime.valueOf(copy));
		// skip ~
		offset++;
		b.setUpdateFlag((char) bytes[offset]);
		return b;
	}

	public static B053 build053(byte[] bytes) throws SeedException {
		int offset = 7;
		B053 b = new B053();

		b.setTransferFunctionType((char) bytes[offset]);
		offset++;

		b.setStageSequence(BlocketteBuilder.parseInt(bytes, offset, 2));
		offset = offset + 2;
		b.setSignalInputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;
		b.setSignalOutputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;

		b.setNormalizationFactor(BlocketteBuilder.parseFloat(bytes, offset, 12));
		offset = offset + 12;

		b.setNormalizationFrequency(BlocketteBuilder.parseFloat(bytes, offset, 12));
		offset = offset + 12;

		int numberOfZeros = BlocketteBuilder.parseInt(bytes, offset, 3);
		offset = offset + 3;

		for (int i = 0; i < numberOfZeros; i++) {
			float real = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			float imaginary = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			float realError = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			float imaginaryError = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			b.add(new Zero(real, realError, imaginary, imaginaryError));
		}

		int numberOfPoles = BlocketteBuilder.parseInt(bytes, offset, 3);
		offset = offset + 3;
		for (int i = 0; i < numberOfPoles; i++) {
			float real = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			float imaginary = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			float realError = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			float imaginaryError = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			b.add(new Pole(real, realError, imaginary, imaginaryError));
		}

		return b;
	}

	public static B054 build054(byte[] bytes) throws SeedException {
		int offset = 7;
		B054 b = new B054();

		b.setResponseType((char) bytes[offset]);
		offset++;
		b.setStageSequence(BlocketteBuilder.parseInt(bytes, offset, 2));
		offset = offset + 2;
		b.setSignalInputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;
		b.setSignalOutputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;

		int numberOfNumerators = BlocketteBuilder.parseInt(bytes, offset, 4);
		offset = offset + 4;

		for (int i = 0; i < numberOfNumerators; i++) {
			float numerator = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			float error = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			b.addNumerator(new Number(numerator, error));
		}

		int numberOfDenominators = BlocketteBuilder.parseInt(bytes, offset, 4);
		offset = offset + 4;
		for (int i = 0; i < numberOfDenominators; i++) {
			float denominator = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			float error = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			b.addDenominator(new Number(denominator, error));
		}

		return b;
	}

	public static B055 build055(byte[] bytes) throws SeedException {
		int offset = 7;
		B055 b = new B055();

		b.setStageSequence(BlocketteBuilder.parseInt(bytes, offset, 2));
		offset = offset + 2;
		b.setSignalInputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;
		b.setSignalOutputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;

		int numberOfNumerators = BlocketteBuilder.parseInt(bytes, offset, 4);
		offset = offset + 4;

		for (int i = 0; i < numberOfNumerators; i++) {
			double frequency = BlocketteBuilder.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			double amplitude = BlocketteBuilder.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			double amplitudeError = BlocketteBuilder.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			double phaseAngle = BlocketteBuilder.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			double phaseAngleError = BlocketteBuilder.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			b.add(b.new Response(frequency, amplitude, amplitudeError, phaseAngle, phaseAngleError));
		}

		return b;
	}

	public static B056 build056(byte[] bytes) throws SeedException {
		int offset = 7;
		B056 b = new B056();
		return b;
	}

	public static B057 build057(byte[] bytes) throws SeedException {
		int offset = 7;
		B057 b = new B057();
		b.setStageSequence(BlocketteBuilder.parseInt(bytes, offset, 2));
		offset = offset + 2;

		double sampleRate = BlocketteBuilder.parseDouble(bytes, offset, 10);
		b.setSampleRate(sampleRate);
		offset = offset + 10;
		b.setDecimationFactor(BlocketteBuilder.parseInt(bytes, offset, 5));
		offset = offset + 5;
		b.setDecimationOffset(BlocketteBuilder.parseInt(bytes, offset, 5));
		offset = offset + 5;

		b.setEstimatedDelay(BlocketteBuilder.parseDouble(bytes, offset, 11));
		offset = offset + 11;
		b.setCorrection(BlocketteBuilder.parseDouble(bytes, offset, 11));

		return b;
	}

	public static B058 build058(byte[] bytes) throws SeedException {
		int offset = 7;
		B058 b = new B058();

		b.setStageSequence(BlocketteBuilder.parseInt(bytes, offset, 2));
		offset = offset + 2;

		double sensitivity = BlocketteBuilder.parseDouble(bytes, offset, 12);
		b.setSensitivity(sensitivity);
		offset = offset + 12;
		double frequency = BlocketteBuilder.parseDouble(bytes, offset, 12);
		offset = offset + 12;
		b.setFrequency(frequency);

		int numberOfHistoryValues = BlocketteBuilder.parseInt(bytes, offset, 2);
		offset = offset + 2;

		for (int i = 0; i < numberOfHistoryValues; i++) {
			double s = BlocketteBuilder.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			double f = BlocketteBuilder.parseDouble(bytes, offset, 12);
			offset = offset + 12;
			int x = offset;
			for (; offset < bytes.length; offset++) {
				if (bytes[offset] == (byte) '~') {
					break;
				}
			}
			byte[] copy = Arrays.copyOfRange(bytes, x, offset);
			b.add(new Calibration(s, f, BTime.valueOf(copy)));
		}
		return b;
	}

	public static B059 build059(byte[] bytes) throws SeedException {
		int offset = 7;
		int i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == (byte) '~') {
				break;
			}
		}
		B059 b = new B059();

		byte[] copy = Arrays.copyOfRange(bytes, i, offset);
		b.setStartTime(BTime.valueOf(copy));

		offset++;
		i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == (byte) '~') {
				break;
			}
		}
		copy = Arrays.copyOfRange(bytes, i, offset);
		b.setEndTime(BTime.valueOf(copy));

		offset++;
		int key = BlocketteBuilder.parseInt(bytes, offset, 4);
		b.setLookupKey(key);
		offset = offset + 4;
		if (offset >= bytes.length) {
			return b;
		}

		int level = BlocketteBuilder.parseInt(bytes, offset, 6);
		b.setLevel(level);

		return b;
	}

	public static B060 build060(byte[] bytes) throws SeedException {
		int offset = 7;
		B060 b = new B060();

		int numberOfStages = BlocketteBuilder.parseInt(bytes, offset, 2);
		offset = offset + 2;

		for (int i = 0; i < numberOfStages; i++) {
			int stageSequenceNumber = BlocketteBuilder.parseInt(bytes, offset, 2);
			offset = offset + 2;
			Stage stage = new Stage();
			stage.setSequence(stageSequenceNumber);
			b.add(stage);
			int numberOfResponses = BlocketteBuilder.parseInt(bytes, offset, 2);
			offset = offset + 2;
			for (int x = 0; x < numberOfResponses; x++) {
				stage.add(BlocketteBuilder.parseInt(bytes, offset, 4));
				offset = offset + 4;
			}
		}
		return b;
	}

	public static B061 build061(byte[] bytes) throws SeedException {
		int offset = 7;
		B061 b = new B061();

		b.setStageSequence(BlocketteBuilder.parseInt(bytes, offset, 2));
		offset = offset + 2;

		int i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == (byte) '~') {
				break;
			}
		}
		b.setName(new String(bytes, i, offset - i));
		offset++;

		b.setSymetryCode((char) bytes[offset]);
		offset++;
		b.setSignalInputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;
		b.setSignalOutputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;

		int numberOfCoefficients = BlocketteBuilder.parseInt(bytes, offset, 4);
		offset = offset + 4;

		for (i = 0; i < numberOfCoefficients; i++) {
			b.addCoefficient(BlocketteBuilder.parseDouble(bytes, offset, 14));
			offset = offset + 14;
		}
		return b;
	}

	public static B062 build062(byte[] bytes) throws SeedException {
		if (bytes == null || bytes.length == 0) {
			throw new IllegalArgumentException("object null|empty");
		}
		int offset = 7;
		B062 b = new B062();

		b.setTransferFunctionType((char) bytes[offset]);
		offset++;

		b.setStageSequence(BlocketteBuilder.parseInt(bytes, offset, 2));
		offset = offset + 2;

		b.setSignalInputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;

		b.setSignalOutputUnit(BlocketteBuilder.parseInt(bytes, offset, 3));
		offset = offset + 3;

		b.setApproximationType((char) bytes[offset]);
		offset++;

		b.setFrequencyUnit((char) bytes[offset]);
		offset++;

		b.setLowerValidFrequencyBound(BlocketteBuilder.parseDouble(bytes, offset, 12));
		offset = offset + 12;
		b.setUpperValidFrequencyBound(BlocketteBuilder.parseDouble(bytes, offset, 12));
		offset = offset + 12;

		b.setLowerBoundOfApproximation(BlocketteBuilder.parseDouble(bytes, offset, 12));
		offset = offset + 12;
		b.setUpperBoundOfApproximation(BlocketteBuilder.parseDouble(bytes, offset, 12));
		offset = offset + 12;
		b.setMaximumAbsoluteError(BlocketteBuilder.parseDouble(bytes, offset, 12));
		offset = offset + 12;

		int numberOfCoefficients = BlocketteBuilder.parseInt(bytes, offset, 3);
		offset = offset + 3;
		for (int i = 0; i < numberOfCoefficients; i++) {
			Float value = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			Float error = BlocketteBuilder.parseFloat(bytes, offset, 12);
			offset = offset + 12;
			b.add(new Number(value, error));
		}
		return b;
	}

	public static int parseInt(byte[] buf, int begin, int length) throws SeedException {
		if (buf == null || length + begin > buf.length) {
			throw new SeedException("Error reading buffer: " + new String(buf) + " at:" + begin + ", length:" + length);
		}
		String s = null;
		try {
			s = new String(buf, begin, length, "us-ascii").trim();
		} catch (UnsupportedEncodingException e) {
			throw new SeedException("Couldn't parse:" + new String(buf));
		}
		s = s.trim();
		if (s.isEmpty()) {
			return 0;
		}
		int i = 0;
		try {
			i = Integer.parseInt(s.trim());
		} catch (NumberFormatException nfe) {
			throw new SeedException("Couldn't parse: [" + s + "]  " + new String(buf));
		}
		return i;
	}

	public static double parseDouble(byte[] buf, int begin, int length) throws SeedException {
		if (buf == null || length + begin > buf.length) {
			throw new SeedException("Error reading buffer: " + new String(buf) + " at:" + begin + ", length:" + length);
		}
		try {
			String s = new String(buf, begin, length, "us-ascii");
			return Double.parseDouble(s);
		} catch (NumberFormatException | UnsupportedEncodingException e) {
			throw new SeedException(e);
		}

	}

	public static float parseFloat(byte[] buf, int begin, int length) throws SeedException {
		if (buf == null || length + begin > buf.length) {
			throw new SeedException("Error reading buffer: " + new String(buf) + " at:" + begin + ", length:" + length);
		}

		try {
			String s = new String(buf, begin, length, "us-ascii");
			return Float.parseFloat(s);
		} catch (NumberFormatException | UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new SeedException("Error reading buffer: " + new String(buf) + " at:" + begin + ", length:" + length);
		}
	}

	public static String readString(byte[] buf, int begin, int length) throws SeedException {
		if (buf == null || length + begin > buf.length) {
			throw new SeedException("Error reading buffer: " + new String(buf) + " at:" + begin + ", length:" + length);
		}
		try {
			String text=new String(buf, begin, length, "us-ascii");
			
			return text.trim();
		} catch (UnsupportedEncodingException e) {
			throw new SeedException("Error reading buffer: " + new String(buf) + " at:" + begin + ", length:" + length);
		}
	}

	private static int advance(byte[] bytes, int offset, int cnt) throws SeedException {
		for (int i = 0; i < cnt; i++) {
			offset++;
		}
		if (offset >= bytes.length) {
			throw new SeedException("Unable to advance offset");
		}
		return offset;
	}

	private static String cleanTextContent(String text) {
		// strips off all non-ASCII characters
		text = text.replaceAll("[^\\x00-\\x7F]", "");

		// erases all the ASCII control characters
		text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");

		// removes non-printable characters from Unicode
		text = text.replaceAll("\\p{C}", "");

		return text.trim();
	}

}
