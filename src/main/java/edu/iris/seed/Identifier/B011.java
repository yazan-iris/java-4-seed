package edu.iris.seed.Identifier;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedBlockette;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;
import edu.iris.seed.station.B050;

public class B011 extends SeedBlockette<B011> implements IdentifierBlockette {

	// private int numberOfStations;
	private Map<String, Row> rows = new LinkedHashMap<>();

	public B011() {
		super(11, "Volume Station Header Index Blockette");
	}

	public int getNumberOfStations() {
		return rows.size();
	}

	public boolean add(B050 b050, int sequence) {
		return this.add(b050.getStationCode().trim(), sequence);
	}

	public boolean add(String code, int sequence) {
		if (this.rows.get(code) == null) {
			this.rows.put(code, new Row(code, sequence));
			return true;
		}
		return false;
	}

	public void update(B050 b050, int sequence) {
		Row row = this.rows.get(b050.getStationCode());
		if (row != null) {
			row.updateSequence(sequence);
		}
	}

	public void update(String code, int sequence) {
		Row row = this.rows.get(code);
		if (row != null) {
			row.updateSequence(sequence);
		}
	}

	public void updateSequences(int value) {
		for (Map.Entry<String, Row> entry : rows.entrySet()) {
			entry.getValue().sequence = entry.getValue().sequence + value;
		}
	}

	public List<Row> getRows() {
		return new ArrayList<>(this.rows.values());
	}

	@Override
	public String toSeedString() throws SeedException {

		SeedStringBuilder builder = new SeedStringBuilder(this.getType(), 3).append("####");

		int number = rows.size();

		builder.append(number, 3);

		for (Row row : this.rows.values()) {
			builder.append(row.getCode(), 5);
			builder.leftPad(row.getSequence(), 6, '0');
		}

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public class Row {
		private String code;
		private int sequence;

		public Row(String code, int sequence) {
			this.code = code;
			this.sequence = sequence;
		}

		public String getCode() {
			return code;
		}

		public int getSequence() {
			return sequence;
		}

		public void updateSequence(int sequence) {
			this.sequence = sequence;
		}
	}

	public BlocketteBuilder<B011> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B011> {

		public Builder() {
			super(11);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B011 build(boolean relax) throws SeedException {
			validate(11, 21, bytes);
			B011 b = new B011();

			int offset = 7;
			int numberOfStations = -1;
			numberOfStations = SeedStrings.parseInt(bytes, 7, 3);
			if (numberOfStations > 999) {
				throw new SeedException("B011: exceeded number of stations allowed.  Expected less than 999 but was {}",
						numberOfStations);
			}
			// b.setNumberOfStations(numberOfStations);
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
	}
}
