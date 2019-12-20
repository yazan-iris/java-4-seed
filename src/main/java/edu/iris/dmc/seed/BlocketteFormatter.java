package edu.iris.dmc.seed;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import edu.iris.dmc.seed.control.dictionary.B030;
import edu.iris.dmc.seed.control.dictionary.B031;
import edu.iris.dmc.seed.control.dictionary.B032;
import edu.iris.dmc.seed.control.dictionary.B033;
import edu.iris.dmc.seed.control.dictionary.B034;
import edu.iris.dmc.seed.control.dictionary.B035;
import edu.iris.dmc.seed.control.dictionary.B041;
import edu.iris.dmc.seed.control.dictionary.B043;
import edu.iris.dmc.seed.control.dictionary.B044;
import edu.iris.dmc.seed.control.dictionary.B045;
import edu.iris.dmc.seed.control.dictionary.B046;
import edu.iris.dmc.seed.control.dictionary.B047;
import edu.iris.dmc.seed.control.dictionary.B048;
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
import edu.iris.dmc.seed.control.station.B055.Response;
import edu.iris.dmc.seed.control.station.B056;
import edu.iris.dmc.seed.control.station.B057;
import edu.iris.dmc.seed.control.station.B058;
import edu.iris.dmc.seed.control.station.B059;
import edu.iris.dmc.seed.control.station.B060;
import edu.iris.dmc.seed.control.station.B061;
import edu.iris.dmc.seed.control.station.B062;
import edu.iris.dmc.seed.control.station.Calibration;
import edu.iris.dmc.seed.control.station.Pole;
import edu.iris.dmc.seed.control.station.Stage;
import edu.iris.dmc.seed.control.station.Zero;

public class BlocketteFormatter implements SeedFormatter {

	private static Logger LOGGER = Logger.getLogger(BlocketteFormatter.class.getName());

	@Override
	public String format(Blockette blockette) throws SeedException {
		LOGGER.info("Formatting: " + blockette);
		switch (blockette.getType()) {
		case 5:
			return format((B005) blockette);
		case 8:
			return format((B008) blockette);
		case 10:
			return format((B010) blockette);
		case 11:
			return format((B011) blockette);
		case 12:
			return format((B012) blockette);
		case 30:
			return format((B030) blockette);
		case 31:
			return format((B031) blockette);
		case 32:
			return format((B032) blockette);
		case 33:
			return format((B033) blockette);
		case 34:
			return format((B034) blockette);
		case 35:
			return format((B035) blockette);
		case 41:
			return format((B041) blockette);
		case 43:
			return format((B043) blockette);
		case 44:
			return format((B044) blockette);
		case 45:
			return format((B045) blockette);
		case 46:
			return format((B046) blockette);
		case 47:
			return format((B047) blockette);
		case 48:
			return format((B048) blockette);
		case 50:
			return format((B050) blockette);
		case 51:
			return format((B051) blockette);
		case 52:
			return format((B052) blockette);
		case 53:
			return format((B053) blockette);
		case 54:
			return format((B054) blockette);
		case 55:
			return format((B055) blockette);
		case 56:
			return format((B056) blockette);
		case 57:
			return format((B057) blockette);
		case 58:
			return format((B058) blockette);
		case 59:
			return format((B059) blockette);
		case 60:
			return format((B060) blockette);
		case 61:
			return format((B061) blockette);
		case 62:
			return format((B062) blockette);

		}
		throw new IllegalArgumentException("Invalid header type [" + blockette.getType() + "]");

	}

	private String format(B005 blockette) throws SeedException {
		SeedStringBuilder builder = new SeedStringBuilder(blockette.getType(), 3).append("####");
		if (blockette.getVersion() != null) {
			builder.append(blockette.getVersion(), 4);
		}
		builder.append(blockette.getLogicalRecordLength(), 2);
		if (blockette.getStartTime() != null) {
			builder.append(blockette.getStartTime());
		}
		builder.append("~");
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	private String format(B008 blockette) throws SeedException {
		SeedStringBuilder builder = new SeedStringBuilder("00" + blockette.getType() + "####");
		if (blockette.getVersion() != null) {
			builder.append(blockette.getVersion(), 4);
		}
		builder.append(blockette.getLogicalRecordLength(), 2);
		builder.append(blockette.getStationCode(), 5).append(blockette.getLocationCode(), 2)
				.append(blockette.getChannelCode(), 3);

		if (blockette.getVolumeStartTime() != null) {
			builder.append(blockette.getVolumeStartTime());
		}
		builder.append("~");
		if (blockette.getVolumeEndTime() != null) {
			builder.append(blockette.getVolumeEndTime());
		}
		builder.append("~");

		if (blockette.getStationInformationEffectiveDate() != null) {
			builder.append(blockette.getStationInformationEffectiveDate());
		}
		builder.append("~");
		if (blockette.getChannelInformationEffectiveDate() != null) {
			builder.append(blockette.getChannelInformationEffectiveDate());
		}
		builder.append("~");
		if (blockette.getNetworkCode() != null) {
			builder.append(blockette.getNetworkCode(), 2);
		}

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	private String format(B010 blockette) throws SeedException {
		SeedStringBuilder builder = new SeedStringBuilder(blockette.getType(), 3).append("####");
		builder.append(blockette.getVersion());
		builder.leftPad(blockette.getNthPower(), 2, '0');

		builder.append(blockette.getStartTime()).append("~");
		builder.append(blockette.getEndTime()).append("~");
		builder.append(blockette.getVolumeTime()).append("~");
		builder.append(blockette.getOrganization()).append("~");
		builder.append(blockette.getLabel()).append("~");

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	private String format(B011 b011) throws SeedException {
		SeedStringBuilder builder = new SeedStringBuilder(b011.getType(), 3).append("####");

		int number = b011.getRows().size();
		builder.append(BlocketteFormatter.format(number, 3));

		for (B011.Row row : b011.getRows()) {
			builder.append(BlocketteFormatter.rightPad(row.getCode(), 5));
			builder.append(BlocketteFormatter.leftPad(row.getSequence(), 6));
		}

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	private String format(B012 blockette) throws SeedException {
		SeedStringBuilder builder = new SeedStringBuilder(blockette.getType(), 3).append("####");

		int number = 0;
		Map<String, Span> spans = blockette.getAvailableStations();

		if (spans != null) {
			number = spans.size();
		}
		builder.append(BlocketteFormatter.format(number, 4));

		if (spans != null) {
			Iterator<Entry<String, Span>> iterator = spans.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Span> mapEntry = (Map.Entry<String, Span>) iterator.next();
				Span span = mapEntry.getValue();
				if (span.getStart() != null) {
					builder.append(span.getStart());
				}
				builder.append("~");

				if (span.getEnd() != null) {
					builder.append(span.getEnd());
				}
				builder.append("~");
				builder.append(BlocketteFormatter.rightPad(mapEntry.getKey(), 6));
			}
		}
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public String format(B030 blockette) throws SeedException {

		SeedStringBuilder builder = new SeedStringBuilder(blockette.getType(), 3).append("####");

		if (blockette.getName() != null) {
			builder.append(blockette.getName());
		}
		builder.append("~");
		builder.append(BlocketteFormatter.format(blockette.getLookupKey(), 4))
				.append(BlocketteFormatter.format(blockette.getDataFamilyType(), 3));

		int numberOfKeys = 0;
		if (blockette.getDecoderKeys() != null) {
			numberOfKeys = blockette.getDecoderKeys().size();
		}
		builder.append(BlocketteFormatter.format(numberOfKeys, 2));
		if (blockette.getDecoderKeys() != null) {
			for (String s : blockette.getDecoderKeys()) {
				builder.append(s).append("~");
			}
		}
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public String format(B031 blockette) throws SeedException {

		SeedStringBuilder builder = new SeedStringBuilder(blockette.getType(), 3).append("####");

		builder.append(BlocketteFormatter.format(blockette.getLookupKey(), 4)).append(blockette.getClassCode());
		if (blockette.getDescription() != null) {
			builder.append(blockette.getDescription());
		}
		builder.append("~");
		builder.append(blockette.getLookupKey(), 3);

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public String format(B032 blockette) throws SeedException {

		SeedStringBuilder builder = new SeedStringBuilder(blockette.getType(), 3).append("####");

		builder.append(blockette.getLookupKey(), 2);
		if (blockette.getTitleAuthor() != null) {
			builder.append(blockette.getTitleAuthor());
		}
		builder.append("~");

		if (blockette.getPublishedCatalog() != null) {
			builder.append(blockette.getPublishedCatalog());
		}
		builder.append("~");

		if (blockette.getPublisherName() != null) {
			builder.append(blockette.getPublisherName());
		}
		builder.append("~");

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public String format(B033 blockette) throws SeedException {
		SeedStringBuilder builder = new SeedStringBuilder(blockette.getType(), 3).append("####");

		builder.append(blockette.getLookupKey(), 3);
		if (blockette.getDescription() != null) {
			builder.append(blockette.getDescription());
		}
		builder.append("~");
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public String format(B034 blockette) throws SeedException {

		SeedStringBuilder builder = new SeedStringBuilder(blockette.getType(), 3).append("####");

		builder.append(blockette.getLookupKey(), 3);
		if (blockette.getName() != null) {
			builder.append(blockette.getName());
		}
		builder.append("~");
		if (blockette.getDescription() != null) {
			builder.append(blockette.getDescription());
		}
		builder.append("~");
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public String format(B035 blockette) throws SeedException {
		SeedStringBuilder builder = new SeedStringBuilder(blockette.getType(), 3).append("####");

		builder.append(blockette.getLookupKey(), 3);

		builder.append(blockette.getComponents().size(), 4);

		for (Component component : blockette.getComponents()) {
			builder.append(component.getStationCode(), 5);
			builder.append(component.getLocation(), 2);
			builder.append(component.getChannelCode(), 3);
			builder.append(component.getSubChannelId(), 4);
			builder.append("" + component.getWeight(), 5);
		}
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public String format(B041 blockette) throws SeedException {
		SeedStringBuilder builder = new SeedStringBuilder("0" + blockette.getType() + "####");

		builder.append(blockette.getLookupKey(), 4);
		builder.append(blockette.getName()).append("~");
		builder.append(blockette.getSymetryCode());
		builder.append(blockette.getSignalInputUnit(), 3);
		builder.append(blockette.getSignalOutputUnit(), 3);

		int size = 0;
		if (blockette.getCoefficients() != null) {
			size = blockette.getCoefficients().size();
		}
		builder.append(size, 4);

		if (blockette.getCoefficients() != null) {
			for (Double coefficient : blockette.getCoefficients()) {
				builder.append(coefficient, "#0.0000000E00", 14);
			}
		}
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public String format(B043 blockette) throws SeedException {
		SeedStringBuilder builder = new SeedStringBuilder("0" + blockette.getType() + "####");
		builder.append(blockette.getTransferFunctionType());
		builder.append(blockette.getLookupKey(), 4);
		builder.append(blockette.getSignalInputUnit(), 3);
		builder.append(blockette.getSignalOutputUnit(), 3);

		builder.append(blockette.getNormalizationFactor(), "#0.00000E00", 12);
		builder.append(blockette.getNormalizationFrequency(), "#0.00000E00", 12);

		builder.append(blockette.getZeros().size(), 3);

		for (Zero zero : blockette.getZeros()) {
			builder.append(zero.getReal().getValue(), "#0.00000E00", 12);
			builder.append(zero.getImaginary().getValue(), "#0.00000E00", 12);
			builder.append(zero.getReal().getError(), "#0.00000E00", 12);
			builder.append(zero.getImaginary().getError(), "#0.00000E00", 12);
		}

		builder.append(blockette.getPoles().size(), 3);

		for (Pole pole : blockette.getPoles()) {
			builder.append(pole.getReal().getValue(), "#0.00000E00", 12);
			builder.append(pole.getImaginary().getValue(), "#0.00000E00", 12);
			builder.append(pole.getReal().getError(), "#0.00000E00", 12);
			builder.append(pole.getImaginary().getError(), "#0.00000E00", 12);
		}

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public String format(B044 blockette) throws SeedException {
		SeedStringBuilder builder = new SeedStringBuilder("0" + blockette.getType() + "####");
		builder.append(blockette.getResponseType());
		builder.append(blockette.getLookupKey(), 4);
		builder.append(blockette.getSignalInputUnit(), 3);
		builder.append(blockette.getSignalOutputUnit(), 3);

		builder.append(blockette.getNumerators().size(), 3);

		for (edu.iris.dmc.seed.control.station.Number coefficient : blockette.getNumerators()) {
			builder.append(coefficient.getValue(), "#0.00000E00", 12);
			builder.append(coefficient.getError(), "#0.00000E00", 12);
		}

		builder.append(blockette.getDenominators().size(), 3);

		for (edu.iris.dmc.seed.control.station.Number coefficient : blockette.getDenominators()) {
			builder.append(coefficient.getValue(), "#0.00000E00", 12);
			builder.append(coefficient.getError(), "#0.00000E00", 12);
		}

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public String format(B045 blockette) throws SeedException {
		SeedStringBuilder builder = new SeedStringBuilder("0" + blockette.getType() + "####");
		builder.append(blockette.getLookupKey(), 4);
		builder.append(blockette.getSignalInputUnit(), 3);
		builder.append(blockette.getSignalOutputUnit(), 3);

		builder.append(blockette.getResponses().size(), 4);

		for (B045.Response response : blockette.getResponses()) {
			builder.append(response.getFrequency(), "#0.00000E00", 12);
			builder.append(response.getAmplitude(), "#0.00000E00", 12);
			builder.append(response.getAmplitudeError(), "#0.00000E00", 12);
			builder.append(response.getPhaaeAngle(), "#0.00000E00", 12);
			builder.append(response.getPhaseError(), "#0.00000E00", 12);
		}
		return builder.toString();
	}

	public String format(B046 blockette) {
		return null;
	}

	public String format(B047 blockette) throws SeedException {
		SeedStringBuilder builder = new SeedStringBuilder("0" + blockette.getType() + "####");
		builder.append(blockette.getLookupKey(), 4);
		builder.append(blockette.getSampleRate(), "#0.000E00", 10);
		builder.append(blockette.getDecimationFactor(), 5);
		builder.append(blockette.getDecimationOffset(), 5);

		builder.append(blockette.getEstimatedDelay(), "#0.0000E00", 11);
		builder.append(blockette.getCorrection(), "#0.0000E00", 11);

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public String format(B048 blockette) throws SeedException {
		SeedStringBuilder builder = new SeedStringBuilder("0" + blockette.getType() + "####");
		builder.append(blockette.getLookupKey(), 4);
		builder.append(blockette.getSensitivity(), "#0.00000E00", 12);
		builder.append(blockette.getFrequency(), "#0.00000E00", 12);

		builder.append(blockette.getHistory().size(), 4);

		for (Calibration calibration : blockette.getHistory()) {
			builder.append(calibration.getSensitivity(), "#0.00000E00", 12);
			builder.append(calibration.getFrequency(), "#0.00000E00", 12);
			builder.append(calibration.getTime());
		}

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public String format(B050 blockette) throws SeedException {
		StringBuilder builder = new StringBuilder("0" + blockette.getType() + "####");
		builder.append(BlocketteFormatter.format(blockette.getStationCode(), 5));
		builder.append(BlocketteFormatter.format(blockette.getLatitude(), "+#,#00.000000;-#", 10));
		builder.append(BlocketteFormatter.format(blockette.getLongitude(), "+#,#000.000000;-#", 11));
		builder.append(BlocketteFormatter.format(blockette.getElevation(), "+#,#0000.0;-#", 7));
		builder.append(BlocketteFormatter.format(blockette.getNumberOfChannels(), 4));
		builder.append(BlocketteFormatter.format(blockette.getNumberOfComments(), 3));
		builder.append(blockette.getSiteName()).append("~");
		builder.append(BlocketteFormatter.format(blockette.getNetworkIdentifierCode(), 3));
		builder.append(BlocketteFormatter.format(blockette.getBit32BitOrder(), 4));
		builder.append(BlocketteFormatter.format(blockette.getBit16BitOrder(), 2));
		builder.append(blockette.getStartTime().toSeedString()).append("~");
		if (blockette.getEndTime() != null) {
			builder.append(blockette.getEndTime().toSeedString());
		}
		builder.append("~");
		builder.append(blockette.getUpdateFlag());
		builder.append(blockette.getNetworkCode());
		builder.replace(3, 7, BlocketteFormatter.format(builder.length(), 4));
		return builder.toString();
	}

	public String format(B051 blockette) throws SeedException {
		SeedStringBuilder builder = new SeedStringBuilder("0" + blockette.getType() + "####");
		builder.append(blockette.getStartTime()).append("~");
		builder.append(blockette.getEndTime()).append("~");
		builder.append(BlocketteFormatter.format(blockette.getLookupKey(), 4));
		builder.append(BlocketteFormatter.format(blockette.getLevel(), 6));

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public String format(B052 blockette) throws SeedException {
		SeedStringBuilder builder = new SeedStringBuilder("0" + blockette.getType() + "####");
		builder.append(blockette.getLocationCode(), 2);
		builder.append(blockette.getLocationCode(), 3);
		int key = 0;
		if (blockette.getSubChannelCode() != null) {
			key = blockette.getSubChannelCode();
		}
		builder.append(key, 4);
		key = 0;
		if (blockette.getInstrumentIdentifier() != null) {
			key = blockette.getInstrumentIdentifier();
		}
		builder.append(key, 3);
		if (blockette.getOptionalComment() != null) {
			builder.append(blockette.getOptionalComment());
		}
		builder.append("~");

		key = 0;
		if (blockette.getUnitsOfSignalResponse() != null) {
			key = blockette.getUnitsOfSignalResponse();
		}
		builder.append(key, 3);

		key = 0;
		if (blockette.getUnitsOfCalibrationInput() != null) {
			key = blockette.getUnitsOfCalibrationInput();
		}
		builder.append(key, 3);

		builder.append(blockette.getLatitude(), "+#,#00.000000;-#", 10);
		builder.append(blockette.getLongitude(), "+#,#000.000000;-#", 11);
		builder.append(blockette.getElevation(), "+#,#0000.0;-#", 7);

		builder.append(blockette.getLocalDepth(), "000.0", 5);
		builder.append(blockette.getAzimuth(), "000.0", 5);
		builder.append(blockette.getDip(), "+#,#00.0;-#", 5);

		key = 0;
		if (blockette.getDataFormatIdentifier() > 0) {
			key = blockette.getDataFormatIdentifier();
		}
		builder.append(key, 4);
		key = 0;
		if (blockette.getDataRecordLength() > 0) {
			key = blockette.getDataRecordLength();
		}
		builder.append(key, 2);
		key = 0;
		builder.append(blockette.getSampleRate(), "0.0000E00", 10);
		builder.append(blockette.getMaxClockDrift(), "0.0000E00", 10);

		builder.append(blockette.getNumberOfComments(), 4);
		builder.append(blockette.getChannelFlags()).append("~");
		builder.append(blockette.getStartTime().toSeedString()).append("~");
		if (blockette.getEndTime() != null) {
			builder.append(blockette.getEndTime().toSeedString());
		}
		builder.append("~");
		builder.append(blockette.getUpdateFlag());

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public String format(B053 blockette) {
		SeedStringBuilder builder = new SeedStringBuilder("0" + blockette.getType() + "####");
		builder.append(blockette.getTransferFunctionType());
		builder.append(blockette.getStageSequence(), 2);
		builder.append(blockette.getSignalInputUnit(), 3);
		builder.append(blockette.getSignalOutputUnit(), 3);

		builder.append(blockette.getNormalizationFactor(), "#0.00000E00", 12);
		builder.append(blockette.getNormalizationFrequency(), "#0.00000E00", 12);

		builder.append(blockette.getZeros().size(), 3);

		for (Zero zero : blockette.getZeros()) {
			builder.append(zero, "#0.00000E00", 12);
		}

		builder.append(blockette.getPoles().size(), 3);

		for (Pole pole : blockette.getPoles()) {
			builder.append(pole, "#0.00000E00", 12);
		}

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public String format(B054 blockette) {
		SeedStringBuilder builder = new SeedStringBuilder("0" + blockette.getType() + "####");
		builder.append(blockette.getResponseType());
		builder.append(blockette.getStageSequence(), 2);
		builder.append(blockette.getSignalInputUnit(), 3);
		builder.append(blockette.getSignalOutputUnit(), 3);

		builder.append(blockette.getNumerators().size(), 4);

		for (edu.iris.dmc.seed.control.station.Number coefficient : blockette.getNumerators()) {
			builder.append(coefficient.getValue(), "#0.00000E00", 12);
			builder.append(coefficient.getError(), "#0.00000E00", 12);
		}

		builder.append(blockette.getDenominators().size(), 4);

		for (edu.iris.dmc.seed.control.station.Number coefficient : blockette.getDenominators()) {
			builder.append(coefficient.getValue(), "#0.00000E00", 12);
			builder.append(coefficient.getError(), "#0.00000E00", 12);
		}

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public String format(B055 blockette) throws SeedException{
		SeedStringBuilder builder = new SeedStringBuilder("0" + blockette.getType() + "####");
		builder.append(blockette.getStageSequence(), 2);
		builder.append(blockette.getSignalInputUnit(), 3);
		builder.append(blockette.getSignalOutputUnit(), 3);

		builder.append(blockette.getResponses().size(), 4);

		for (Response response : blockette.getResponses()) {
			builder.append(response.getFrequency(), "#0.00000E00", 12);
			builder.append(response.getAmplitude(), "#0.00000E00", 12);
			builder.append(response.getAmplitudeError(), "#0.00000E00", 12);
			builder.append(response.getPhaaeAngle(), "#0.00000E00", 12);
			builder.append(response.getPhaseError(), "#0.00000E00", 12);
		}
		return builder.toString();
	}

	public String format(B056 blockette) throws SeedException{
		return null;
	}

	public String format(B057 blockette) throws SeedException{
		SeedStringBuilder builder = new SeedStringBuilder("0" + blockette.getType() + "####");
		builder.append(blockette.getStageSequence(), 2);
		builder.append(blockette.getSampleRate(), "#0.000E00", 10);
		builder.append(blockette.getDecimationFactor(), 5);
		builder.append(blockette.getDecimationOffset(), 5);

		builder.append(blockette.getEstimatedDelay(), "#0.0000E00", 11);
		builder.append(blockette.getCorrection(), "#0.0000E00", 11);

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public String format(B058 blockette) throws SeedException{
		SeedStringBuilder builder = new SeedStringBuilder("0" + blockette.getType() + "####");
		builder.append(blockette.getStageSequence(), 2);
		builder.append(blockette.getSensitivity(), "#0.00000E00", 12);
		builder.append(blockette.getFrequency(), "#0.00000E00", 12);

		builder.append(blockette.getHistory().size(), 4);

		for (Calibration calibration : blockette.getHistory()) {
			builder.append(calibration.getSensitivity(), "#0.00000E00", 12);
			builder.append(calibration.getFrequency(), "#0.00000E00", 12);
			builder.append(calibration.getTime());
		}

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public String format(B059 blockette) throws SeedException{
		StringBuilder builder = new StringBuilder("0" + blockette.getType() + "####");
		builder.append(blockette.getStartTime().toString()).append("~");
		builder.append(blockette.getEndTime().toString()).append("~");
		builder.append(BlocketteFormatter.format(blockette.getLookupKey(), 4));
		builder.append(BlocketteFormatter.format(blockette.getLevel(), 6));

		builder.replace(3, 7, BlocketteFormatter.format(builder.length(), 4));
		return builder.toString();
	}

	public String format(B060 blockette) throws SeedException{
		SeedStringBuilder builder = new SeedStringBuilder("0" + blockette.getType() + "####");

		builder.append(blockette.getStages().size(), 2);
		for (Stage stage : blockette.getStages()) {
			builder.append(stage.getSequence(), 2);
			builder.append(stage.getResponses().size(), 2);
			for (Integer lookup : stage.getResponses()) {
				builder.append(lookup, 4);
			}
		}

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public String format(B061 blockette) throws SeedException{
		SeedStringBuilder builder = new SeedStringBuilder("0" + blockette.getType() + "####");

		builder.append(blockette.getStageSequence(), 2);
		builder.append(blockette.getName()).append("~");
		builder.append(blockette.getSymetryCode());
		builder.append(blockette.getSignalInputUnit(), 3);
		builder.append(blockette.getSignalOutputUnit(), 3);

		int size = 0;
		if (blockette.getCoefficients() != null) {
			size = blockette.getCoefficients().size();
		}
		builder.append(size, 4);

		if (blockette.getCoefficients() != null) {
			for (Double coefficient : blockette.getCoefficients()) {
				builder.append(coefficient, "#0.0000000E00", 14);
			}
		}
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public String format(B062 blockette) throws SeedException{
		SeedStringBuilder builder = new SeedStringBuilder("0" + blockette.getType() + "####");
		builder.append(blockette.getTransferFunctionType());
		builder.append(blockette.getStageSequence(), 2);
		builder.append(blockette.getSignalInputUnit(), 3);
		builder.append(blockette.getSignalOutputUnit(), 3);
		builder.append(blockette.getApproximationType());
		builder.append(blockette.getFrequencyUnit());

		builder.append(blockette.getLowerValidFrequencyBound(), "#0.00000E00", 12);
		builder.append(blockette.getUpperValidFrequencyBound(), "#0.00000E00", 12);
		builder.append(blockette.getLowerBoundOfApproximation(), "#0.00000E00", 12);
		builder.append(blockette.getUpperBoundOfApproximation(), "#0.00000E00", 12);
		builder.append(blockette.getMaximumAbsoluteError(), "#0.00000E00", 12);

		int size = 0;
		if (blockette.getCoefficients() != null) {
			size = blockette.getCoefficients().size();
		}
		builder.append(size, 4);

		if (blockette.getCoefficients() != null) {
			for (edu.iris.dmc.seed.control.station.Number coefficient : blockette.getCoefficients()) {
				builder.append(coefficient.getValue(), "#0.00000E00", 12);
				builder.append(coefficient.getError(), "#0.00000E00", 12);
			}
		}
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public static String format(int num, int length) throws SeedException{
		return String.format("%0" + length + "d", num);
	}

	public static String format(String s, int width) {
		if (s == null) {
			throw new IllegalArgumentException("string cannot be null.");
		}

		StringBuilder builder = new StringBuilder(s.trim());

		if (builder.length() == width) {
			return s;
		}

		int l = width - builder.length();
		for (int i = 0; i < l; i++) {
			builder.append(" ");
		}

		return builder.toString();
	}

	public static String format(Double s, String format, int width) throws SeedException{
		if (s == null) {

		}
		DecimalFormat df = new DecimalFormat(format);
		StringBuilder builder = new StringBuilder(df.format(s));
		if (builder.length() != width) {
			throw new NumberFormatException("Couldn't format number!" + builder.toString());
		}
		return builder.toString();
	}

	public static String rightPad(String string, int length) {
		return String.format("%1$-" + length + "S", string);
	}

	public static String leftPad(int num, int length) {
		return String.format("%0" + length + "d", num);
	}

}
