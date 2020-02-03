package edu.iris.seed.station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

import edu.iris.seed.BTime;
import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedBlockette;
import edu.iris.seed.SeedContainer;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;
import edu.iris.seed.station.B052.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class B050 extends SeedBlockette<B050>
		implements StationBlockette, SeedContainer<StationBlockette>, Comparable<B050> {

	private String stationCode;
	private double latitude;

	private double longitude;

	private double elevation;

	private int numberOfChannels;

	private int numberOfComments;

	private String siteName;

	private int networkIdentifierCode;
	private int bit32BitOrder;
	private int bit16BitOrder;
	private BTime startTime;
	private BTime endTime;
	private char updateFlag = 'N';
	private String networkCode;

	private List<B051> b051s = new ArrayList<>();
	private List<B052> b052s = new ArrayList<>();

	private B052 b052;

	public B050() {
		super(50, "Station Identifier Blockette");
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
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

	public int getNumberOfChannels() {
		return numberOfChannels;
	}

	public void setNumberOfChannels(int numberOfChannels) {
		this.numberOfChannels = numberOfChannels;
	}

	public int getNumberOfComments() {
		return numberOfComments;
	}

	public void setNumberOfComments(int numberOfComments) {
		this.numberOfComments = numberOfComments;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public int getNetworkIdentifierCode() {
		return networkIdentifierCode;
	}

	public void setNetworkIdentifierCode(int networkIdentifierCode) {
		this.networkIdentifierCode = networkIdentifierCode;
	}

	public int getBit32BitOrder() {
		return bit32BitOrder;
	}

	public void setBit32BitOrder(int bit32BitOrder) {
		this.bit32BitOrder = bit32BitOrder;
	}

	public int getBit16BitOrder() {
		return bit16BitOrder;
	}

	public void setBit16BitOrder(int bit16BitOrder) {
		this.bit16BitOrder = bit16BitOrder;
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

	public String getNetworkCode() {
		return networkCode;
	}

	public void setNetworkCode(String networkCode) {
		this.networkCode = networkCode;
	}

	@Override
	public List<StationBlockette> blockettes() {
		List<StationBlockette> l = new ArrayList<>();
		l.addAll(this.b051s);
		for (B052 b052 : this.b052s) {
			l.add(b052);
			l.addAll(b052.getB059s());
			for (Stage stage : b052.getStages()) {
				l.addAll(stage.blockettes());
			}
		}
		return l;
	}

	@Override
	public boolean addAll(Collection<StationBlockette> c) throws SeedException {
		int size = size();
		for (StationBlockette b : c) {
			add(b);
		}
		return size != size();
	}

	@Override
	public int size() {
		int size = this.b051s.size() + this.b052s.size();

		for (B052 b : this.b052s) {
			size += b.size();
		}
		return size;
	}

	@Override
	public boolean isEmpty() {
		if (this.b051s.isEmpty()) {
			return this.b052s.isEmpty();
		}
		return false;
	}

	@Override
	public void clear() {
		this.b051s.clear();
		this.b052s.clear();
		this.b052 = null;

	}

	@Override
	public StationBlockette remove(StationBlockette e) {
		if (e == null) {
			return null;
		}
		if (e instanceof B051) {
			if (this.b051s.remove(e)) {
				return e;
			} else {
				return null;
			}
		}
		if (e instanceof B052) {
			if (this.b052s.remove(e)) {
				return e;
			} else {
				return null;
			}
		}

		for (B052 b052 : this.b052s) {
			if (b052.remove(e) != null) {
				return e;
			}
		}
		return null;
	}

	@Override
	public ListIterator<StationBlockette> listIterator() {
		return this.blockettes().listIterator();
	}

	@Override
	public ListIterator<StationBlockette> listIterator(int index) {
		return this.blockettes().listIterator(index);
	}

	public String toSeedString() throws SeedException {
		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");

		builder.append(this.stationCode, 5);
		builder.appendLatitude(this.latitude);
		builder.appendLongitude(this.longitude);
		builder.appendElevation(this.elevation);
		builder.append(this.b052s.size(), 4);
		builder.append(this.b051s.size(), 3);
		builder.append(this.siteName).append("~");
		builder.append(this.networkIdentifierCode, 3);
		builder.append(this.bit32BitOrder, 4);
		builder.append(this.bit16BitOrder, 2);
		builder.append(this.startTime).append("~");
		if (this.endTime != null) {
			builder.append(this.endTime);
		}
		builder.append("~");
		builder.append(this.updateFlag);
		builder.append(this.networkCode, 2);
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public BlocketteBuilder<B050> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B050> {

		public Builder() {
			super(50);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B050 build() throws SeedException {

			int offset = 7;
			B050 b = new B050();

			String stationCode = SeedStrings.readString(bytes, offset, 5);
			b.setStationCode(stationCode);
			// offset = offset + 5;
			offset = SeedStrings.advance(bytes, offset, 5);
			Double latitude = SeedStrings.parseDouble(bytes, offset, 10);
			if (latitude != null) {
				b.setLatitude(latitude);
			}

			offset = offset + 10;
			Double longitude = SeedStrings.parseDouble(bytes, offset, 11);
			if (longitude != null) {
				b.setLongitude(longitude);
			}
			offset = offset + 11;
			Double elevation = SeedStrings.parseDouble(bytes, offset, 7);
			if (elevation != null) {
				b.setElevation(elevation);
			}
			offset = offset + 7;
			int numberOfChannels = SeedStrings.parseInt(bytes, offset, 4);
			b.setNumberOfChannels(numberOfChannels);
			offset = offset + 4;
			int numberOfComments = SeedStrings.parseInt(bytes, offset, 3);
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
				b.setSiteName(SeedStrings.cleanTextContent(siteName));
			}
			offset = SeedStrings.advance(bytes, offset, 1);
			Integer networkIdentifierCode = SeedStrings.parseInt(bytes, offset, 3);
			b.setNetworkIdentifierCode(networkIdentifierCode);

			// offset = offset + 3;
			offset = SeedStrings.advance(bytes, offset, 3);
			int wordOrder32 = SeedStrings.parseInt(bytes, offset, 4);
			b.setBit32BitOrder(wordOrder32);
			offset = offset + 4;
			int wordOrder16 = SeedStrings.parseInt(bytes, offset, 2);
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
			offset = SeedStrings.advance(bytes, offset, 1);

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
			offset = SeedStrings.advance(bytes, offset, 1);
			// offset++;
			b.setUpdateFlag((char) bytes[offset]);
			offset = SeedStrings.advance(bytes, offset, 1);
			// offset++;
			String networkCode = new String(bytes, offset, 2).trim();
			b.setNetworkCode(networkCode);

			return b;
		}
	}

	public StationBlockette add(StationBlockette blockette) throws SeedException {

		if (blockette instanceof B051) {
			if (log.isDebugEnabled()) {
				log.debug("Adding {} to B050", blockette.getType());
			}
			if (this.b051s.add((B051) blockette)) {
				return blockette;
			} else {
				return null;
			}
		} else if (blockette instanceof B052) {
			if (log.isDebugEnabled()) {
				log.debug("Adding {} to B050", blockette.getType());
			}
			B052 b052 = (B052) blockette;
			if (this.b052s.add(b052)) {
				this.b052 = b052;
				return blockette;
			} else {
				return null;
			}
		} else {
			if (b052 == null) {
				throw new SeedException("No blockette of type 52 exist, cannot add {}", blockette.toSeedString());
			} else {
				return b052.add(blockette);
			}
		}
	}

	public List<B051> getB051s() {
		return b051s;
	}

	public List<B052> getB052s() {
		return b052s;
	}

	@Override
	public int compareTo(B050 o) {
		return Comparator.comparing(B050::getNetworkCode).thenComparing(B050::getStationCode)
				.thenComparing(B050::getStartTime)
				.thenComparing(B050::getEndTime, Comparator.nullsFirst(Comparator.naturalOrder())).compare(this, o);
	}

}
