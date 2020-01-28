package edu.iris.seed.record;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.iris.seed.IncompleteBlockette;
import edu.iris.seed.SeedControlHeader;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedHeader.Type;
import edu.iris.seed.SeedOutputStream;
import edu.iris.seed.SeedRecord;
import edu.iris.seed.io.BlocketteIterator;
import edu.iris.seed.station.B050;
import edu.iris.seed.station.StationBlockette;

public class StationRecord extends SeedRecord<StationBlockette> {

	private static final Logger logger = LoggerFactory.getLogger(StationRecord.class);
	private B050 b050;

	private int size;

	public StationRecord() {
		this(1, false);
	}

	public StationRecord(int sequence, boolean continuation) {
		this(SeedControlHeader.Builder.newInstance(sequence, Type.S, continuation).build());
	}

	public StationRecord(SeedControlHeader header) {
		super(header);
	}

	public StationBlockette add(StationBlockette t) throws SeedException {
		if (t instanceof B050) {
			if (this.b050 == null) {
				this.b050 = (B050) t;
				size++;
			}
		} else {
			if (this.b050 == null) {

			} else {
				this.b050.add(t);
				size++;
			}
		}
		return t;

	}

	public B050 getB050() {
		return this.b050;
	}

	public List<StationBlockette> blockettes() {
		List<StationBlockette> blockettes = new ArrayList<>();
		if (this.b050 == null) {
			return Collections.emptyList();
		}
		blockettes.add(this.b050);
		blockettes.addAll(this.b050.getAll());
		return blockettes;
	}

	public void clear() {
		this.b050 = null;
		this.size = 0;
	}

	public List<StationBlockette> get(int type) {
		if (type == 50) {
			return Arrays.asList(this.b050);
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public StationBlockette get(int... type) {
		if (type == null || type.length == 0) {
			return null;
		}
		if (type[0] != 50) {
			return null;
		}
		List<StationBlockette> blockettes = get(type[0]);
		if (blockettes == null || blockettes.isEmpty()) {
			return null;
		}
		return blockettes.get(0);
	}

	public boolean isEmpty() {
		return this.b050 == null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public int writeTo(OutputStream outputStream, int recordLength, int sequence) throws SeedException, IOException {
		SeedOutputStream stream = new SeedOutputStream(outputStream, recordLength, sequence,
				this.getHeader().getRecordType());
		stream.writeControl(blockettes());
		return stream.flush();
	}

	public Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private byte[] bytes;

		private Builder() {
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public Builder fromBytes(byte[] bytes) throws SeedException {
			this.bytes = bytes;
			return this;
		}

		public StationRecord build() throws SeedException {
			logger.debug("Building station record");
			SeedControlHeader header = SeedControlHeader.Builder.newInstance(bytes).build();
			StationRecord record = new StationRecord(header);
			if (header.isContinuation()) {

			}
			BlocketteIterator<StationBlockette> it = new BlocketteIterator<StationBlockette>(8, bytes);
			while (it.hasNext()) {
				StationBlockette b = it.next();
				record.add(b);
				if (b instanceof IncompleteBlockette) {
					IncompleteBlockette i = (IncompleteBlockette) b;
				}
			}
			return record;
		}
	}

}
