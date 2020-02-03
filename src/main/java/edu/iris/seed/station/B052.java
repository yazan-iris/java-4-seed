package edu.iris.seed.station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

import edu.iris.seed.BTime;
import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedBlockette;
import edu.iris.seed.SeedContainer;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class B052 extends SeedBlockette<B052>
		implements StationBlockette, SeedContainer<StationBlockette>, Comparable<B052> {

	private String locationCode;

	private String channelCode;

	private int subChannelCode;

	private int instrumentIdentifier;

	private String optionalComment;

	private int unitsOfSignalResponse;
	private int unitsOfCalibrationInput;

	private double latitude;
	private double longitude;
	private double elevation;
	private double localDepth;// meter
	private double azimuth;// degrees
	private double dip;// degrees
	private int dataFormatIdentifier;
	private int dataRecordLength;
	private double sampleRate;// Hz
	private double maxClockDrift;// seconds
	private int numberOfComments = 0;
	private String channelFlags;
	private BTime startTime;
	private BTime endTime;
	private char updateFlag = 'N';

	private List<B059> b059s = new ArrayList<>();

	private Map<Integer, Stage> stages = new TreeMap<>();

	public B052() {
		this("Channel Identifier Blockette");
	}

	public B052(String text) {
		super(52, text);
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public Integer getSubChannelCode() {
		return subChannelCode;
	}

	public void setSubChannelCode(Integer subChannelCode) {
		this.subChannelCode = subChannelCode;
	}

	public Integer getInstrumentIdentifier() {
		return instrumentIdentifier;
	}

	public void setInstrumentIdentifier(Integer instrumentIdentifier) {
		this.instrumentIdentifier = instrumentIdentifier;
	}

	public Integer getUnitsOfSignalResponse() {
		return unitsOfSignalResponse;
	}

	public void setUnitsOfSignalResponse(Integer unitsOfSignalResponse) {
		this.unitsOfSignalResponse = unitsOfSignalResponse;
	}

	public Integer getUnitsOfCalibrationInput() {
		return unitsOfCalibrationInput;
	}

	public void setUnitsOfCalibrationInput(Integer unitsOfCalibrationInput) {
		this.unitsOfCalibrationInput = unitsOfCalibrationInput;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getElevation() {
		return elevation;
	}

	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	public double getLocalDepth() {
		return localDepth;
	}

	public void setLocalDepth(double localDepth) {
		this.localDepth = localDepth;
	}

	public double getAzimuth() {
		return azimuth;
	}

	public void setAzimuth(double azimuth) {
		this.azimuth = azimuth;
	}

	public double getDip() {
		return dip;
	}

	public void setDip(double dip) {
		this.dip = dip;
	}

	public int getDataFormatIdentifier() {
		return dataFormatIdentifier;
	}

	public void setDataFormatIdentifier(int dataFormatIdentifier) {
		this.dataFormatIdentifier = dataFormatIdentifier;
	}

	public int getDataRecordLength() {
		return dataRecordLength;
	}

	public void setDataRecordLength(int dataRecordLength) {
		this.dataRecordLength = dataRecordLength;
	}

	public double getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(double sampleRate) {
		this.sampleRate = sampleRate;
	}

	public Double getMaxClockDrift() {
		return maxClockDrift;
	}

	public void setMaxClockDrift(Double maxClockDrift) {
		this.maxClockDrift = maxClockDrift;
	}

	public int getNumberOfComments() {
		return numberOfComments;
	}

	public void setNumberOfComments(int numberOfComments) {
		this.numberOfComments = numberOfComments;
	}

	public String getChannelFlags() {
		return channelFlags;
	}

	public void setChannelFlags(String channelFlags) {
		this.channelFlags = channelFlags;
	}

	public BTime getStartTime() {
		return startTime;
	}

	public void setStartTime(BTime startTime) {
		this.startTime = startTime;
	}

	public BTime getEndTime() {
		return endTime;
	}

	public void setEndTime(BTime endTime) {
		this.endTime = endTime;
	}

	public char getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(char updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getOptionalComment() {
		return optionalComment;
	}

	public void setOptionalComment(String optionalComment) {
		this.optionalComment = optionalComment;
	}

	public List<B059> getB059s() {
		return b059s;
	}

	@Override
	public StationBlockette add(StationBlockette b) throws SeedException {
		if (log.isDebugEnabled()) {
			log.debug("Adding {} to B052", b.getType());
		}
		if (b instanceof B059) {
			this.b059s.add((B059) b);
			return b;
		} else {
			ResponseBlockette blockette = (ResponseBlockette) b;
			int sequence = blockette.getStageNumber();
			Stage stage = this.stages.get(sequence);

			if (stage == null) {
				stage = new Stage(sequence);
				this.stages.put(sequence, stage);
			}
			return stage.add(blockette);
		}
	}

	public void addAll(List<B059> b059s) {
		this.b059s.addAll(b059s);
	}

	public Stage getStage(int sequence) {
		return this.stages.get(sequence);
	}

	public List<Stage> getStages() {
		return new ArrayList<>(this.stages.values());
	}

	@Override
	public List<StationBlockette> blockettes() {
		List<StationBlockette> list = new ArrayList<>();
		list.addAll(this.b059s);
		for (Stage s : this.stages.values()) {
			list.addAll(s.blockettes());
		}
		return list;
	}

	@Override
	public boolean addAll(Collection<StationBlockette> c) throws SeedException {
		int size = size();
		for (StationBlockette s : c) {
			add(s);
		}
		return size != size();
	}

	@Override
	public boolean isEmpty() {
		if (this.b059s.isEmpty()) {
			return this.stages.isEmpty();
		}
		return false;
	}

	@Override
	public void clear() {
		this.b059s.clear();
		this.stages.clear();

	}

	@Override
	public StationBlockette remove(StationBlockette e) {
		if (e instanceof B059) {
			if (this.b059s.remove(e)) {
				return e;
			} else {
				return null;
			}
		}
		ResponseBlockette b = (ResponseBlockette) e;

		Stage stage = this.stages.get(b.getStageNumber());
		if (stage == null) {
			return null;
		}

		return stage.remove(b);
	}

	@Override
	public ListIterator<StationBlockette> listIterator() {
		return blockettes().listIterator();
	}

	@Override
	public ListIterator<StationBlockette> listIterator(int index) {
		return blockettes().listIterator(index);
	}

	@Override
	public int size() {
		int size = this.b059s.size();
		for (Stage s : this.stages.values()) {
			size += s.size();
		}
		return size;
	}

	@Override
	public String toSeedString() throws SeedException {
		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");
		builder.append(this.locationCode, 2);
		builder.append(this.channelCode, 3);

		builder.append(this.subChannelCode, 4);
		builder.append(this.instrumentIdentifier, 3);
		if (this.optionalComment != null) {
			builder.append(this.optionalComment);
		}
		builder.append("~");

		builder.append(this.unitsOfSignalResponse, 3);

		builder.append(this.unitsOfCalibrationInput, 3);

		builder.appendLatitude(this.latitude);
		builder.appendLongitude(this.longitude);
		builder.appendElevation(this.elevation);

		builder.appendLocalDepth(this.localDepth);
		builder.appendAzimuth(this.azimuth);
		builder.appendDip(this.dip);

		builder.append(this.dataFormatIdentifier, 4);
		builder.append(this.dataRecordLength, 2);

		builder.append(this.sampleRate, "0.0000E-00", 10);
		builder.append(this.maxClockDrift, "0.0000E-00", 10);

		builder.append(this.numberOfComments, 4);
		builder.append(this.channelFlags).append("~");
		builder.append(this.startTime).append("~");
		if (this.endTime != null) {
			builder.append(this.endTime);
		}
		builder.append("~");
		builder.append(this.updateFlag);

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public BlocketteBuilder<B052> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B052> {

		public Builder() {
			super(52);
			// TODO Auto-generated constructor stub
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B052 build() throws SeedException {
			String dataToParse = new String(bytes);
			int offset = 7;
			B052 b = new B052(dataToParse);

			String location = SeedStrings.readString(bytes, offset, 2);
			b.setLocationCode(location);
			offset += 2;
			String code = SeedStrings.readString(bytes, offset, 3);
			b.setChannelCode(code);
			offset += 3;

			b.setSubChannelCode(SeedStrings.parseInt(bytes, offset, 4));
			offset = offset + 4;
			b.setInstrumentIdentifier(SeedStrings.parseInt(bytes, offset, 3));
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

			b.setUnitsOfSignalResponse(SeedStrings.parseInt(bytes, offset, 3));
			offset = offset + 3;
			b.setUnitsOfCalibrationInput(SeedStrings.parseInt(bytes, offset, 3));
			offset = offset + 3;

			b.setLatitude(SeedStrings.parseDouble(bytes, offset, 10));
			offset = offset + 10;

			b.setLongitude(SeedStrings.parseDouble(bytes, offset, 11));
			offset = offset + 11;

			b.setElevation(SeedStrings.parseDouble(bytes, offset, 7));
			offset = offset + 7;

			b.setLocalDepth(SeedStrings.parseDouble(bytes, offset, 5));
			offset = offset + 5;

			b.setAzimuth(SeedStrings.parseDouble(bytes, offset, 5));
			offset = offset + 5;

			b.setDip(SeedStrings.parseDouble(bytes, offset, 5));
			offset = offset + 5;

			b.setDataFormatIdentifier(SeedStrings.parseInt(bytes, offset, 4));
			offset = offset + 4;
			b.setDataRecordLength(SeedStrings.parseInt(bytes, offset, 2));
			offset = offset + 2;

			b.setSampleRate(SeedStrings.parseDouble(bytes, offset, 10));
			offset += 10;

			b.setMaxClockDrift(SeedStrings.parseDouble(bytes, offset, 10));
			offset += 10;

			int numberOfComments = SeedStrings.parseInt(bytes, offset, 4);
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
	}

	@Override
	public int compareTo(B052 o) {
		return Comparator.comparing(B052::getChannelCode).thenComparing(B052::getLocationCode)
				.thenComparing(B052::getStartTime).thenComparing(B052::getEndTime).compare(this, o);
	}

	public class Stage implements SeedContainer<ResponseBlockette> {
		int number;
		B053 b053;
		B054 b054;
		B055 b055;
		B056 b056;
		B057 b057;
		B058 b058;
		B060 b060;
		B061 b061;
		B062 b062;

		Stage(int number) {
			this.number = number;
		}

		@Override
		public ResponseBlockette add(ResponseBlockette blockette) throws SeedException {
			int sequence = ((ResponseBlockette) blockette).getStageNumber();
			if (sequence != this.number) {
				throw new SeedException("Blockette stage number[{}] does not match this stage sequence number [{}]",
						sequence, number);
			}
			int type = blockette.getType();
			if (sequence == 0 && (type != 58 && type != 62 && type != 60)) {
				throw new SeedException("Blockette {} is not allowed in stage 0", type);
			}
			switch (type) {
			case 53:
				this.b053 = this.b053 == null ? (B053) blockette : this.b053.append((B053) blockette);
				return this.b053;
			case 54:
				this.b054 = this.b054 == null ? (B054) blockette : this.b054.append((B054) blockette);
				return this.b054;
			case 55:
				this.b055 = (B055) blockette;
				return this.b055;
			case 56:
				this.b056 = (B056) blockette;
				return this.b055;
			case 57:
				this.b057 = (B057) blockette;
				return this.b057;
			case 58:
				this.b058 = (B058) blockette;
				return this.b058;
			case 60:
				this.b060 = (B060) blockette;
				return this.b060;
			case 61:
				this.b061 = this.b061 == null ? (B061) blockette : this.b061.append((B061) blockette);
				return this.b061;
			case 62:
				this.b062 = this.b062 == null ? (B062) blockette : this.b062.append((B062) blockette);
				return this.b062;
			default:
				throw new SeedException("Unkown reponse type {}, stage[{}]", type, number);
			}
		}

		@Override
		public List<ResponseBlockette> blockettes() {
			List<ResponseBlockette> list = new ArrayList<>();
			if (b053 != null) {
				list.add(b053);
			}

			if (b054 != null) {
				list.add(b054);
			}
			if (b055 != null) {
				list.add(b055);
			}
			if (b056 != null) {
				list.add(b056);
			}
			if (b057 != null) {
				list.add(b057);
			}
			if (b058 != null) {
				list.add(b058);
			}
			if (b060 != null) {
				list.add(b060);
			}
			if (b061 != null) {
				list.add(b061);
			}
			if (b062 != null) {
				list.add(b062);
			}
			return list;
		}

		public int getNumber() {
			return this.number;
		}

		@Override
		public int size() {
			int size = 0;
			if (this.b053 != null) {
				size++;
			}
			if (this.b054 != null) {
				size++;
			}
			if (this.b055 != null) {
				size++;
			}
			if (this.b056 != null) {
				size++;
			}
			if (this.b057 != null) {
				size++;
			}
			if (this.b058 != null) {
				size++;
			}
			if (this.b060 != null) {
				size++;
			}
			if (this.b061 != null) {
				size++;
			}
			if (this.b062 != null) {
				size++;
			}

			return size;
		}

		//TODO:check when appending.
		@Override
		public boolean addAll(Collection<ResponseBlockette> c) throws SeedException {
			int size = size();
			for (ResponseBlockette r : c) {
				add(r);
			}
			return size != size();
		}

		@Override
		public boolean isEmpty() {
			if (this.b053 != null) {
				return false;
			}
			if (this.b055 != null) {
				return false;
			}
			if (this.b055 != null) {
				return false;
			}
			if (this.b056 != null) {
				return false;
			}
			if (this.b057 != null) {
				return false;
			}
			if (this.b058 != null) {
				return false;
			}
			if (this.b060 != null) {
				return false;
			}
			if (this.b062 != null) {
				return false;
			}
			return true;
		}

		@Override
		public void clear() {
			this.b053 = null;
			this.b055 = null;
			this.b055 = null;
			this.b056 = null;
			this.b057 = null;
			this.b058 = null;
			this.b060 = null;
			this.b062 = null;
		}

		@Override
		public ResponseBlockette remove(ResponseBlockette responseBlockette) {
			if (responseBlockette == null) {

			}
			int type = responseBlockette.getType();
			ResponseBlockette r = null;
			switch (type) {
			case 53:
				r = this.b053;
				this.b053 = null;
				break;
			case 54:
				r = this.b054;
				this.b054 = null;
				break;
			case 55:
				r = this.b055;
				this.b055 = null;
				break;
			case 56:
				r = this.b056;
				this.b056 = null;
				break;
			case 57:
				r = this.b057;
				this.b057 = null;
				break;
			case 58:
				r = this.b058;
				this.b058 = null;
				break;
			case 61:
				r = this.b061;
				this.b061 = null;
				break;
			case 62:
				r = this.b062;
				this.b062 = null;
				break;
			}
			return r;
		}

		@Override
		public ListIterator<ResponseBlockette> listIterator() {
			return blockettes().listIterator();
		}

		@Override
		public ListIterator<ResponseBlockette> listIterator(int index) {
			return blockettes().listIterator(index);
		}
	}

}
