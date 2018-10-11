package edu.iris.dmc.seed.control.index;

import java.util.ArrayList;
import java.util.List;

import edu.iris.dmc.io.SeedStringBuilder;
import edu.iris.dmc.seed.AbstractBlockette;
import edu.iris.dmc.seed.control.station.B050;

public class B011 extends AbstractBlockette implements IndexBlockette {

	private int numberOfStations;
	private List<Row> rows = new ArrayList<>();

	public B011() {
		super(11, "Volume Station Header Index Blockette");
	}

	public int getNumberOfStations() {
		return numberOfStations;
	}

	public void setNumberOfStations(int numberOfStations) {
		this.numberOfStations = numberOfStations;
	}

	public void add(B050 b050, int sequence) {
		this.add(b050.getStationCode(), sequence);
	}

	public void add(String code, int sequence) {
		this.rows.add(new Row(code, sequence));
	}

	public List<Row> getRows() {
		return this.rows;
	}

	@Override
	public int getLength() {
		return 10 + (this.rows.size() * 11);
	}

	@Override
	public String toSeedString() {

		SeedStringBuilder builder = new SeedStringBuilder(this.getType(), 3).append("####");

		int number = rows.size();

		builder.append(number, 3);

		for (Row row : this.rows) {
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
	}

}
