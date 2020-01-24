package edu.iris.seed.record;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.iris.seed.SeedControlHeader;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedHeader.Type;
import edu.iris.seed.SeedOutputStream;
import edu.iris.seed.SeedRecord;
import edu.iris.seed.Identifier.B005;
import edu.iris.seed.Identifier.B008;
import edu.iris.seed.Identifier.B010;
import edu.iris.seed.Identifier.B011;
import edu.iris.seed.Identifier.B012;
import edu.iris.seed.Identifier.IdentifierBlockette;
import edu.iris.seed.io.BlocketteIterator;

public class IdentifierRecord extends SeedRecord<IdentifierBlockette> {

	private B005 b005;
	private B008 b008;
	private B010 b010;
	private B011 b011 = new B011();
	private B012 b012;

	public IdentifierRecord() {
		this(1, false);
	}

	public IdentifierRecord(int sequence, boolean continuation) {
		this(SeedControlHeader.Builder.newInstance(sequence, Type.V, continuation).build());
	}

	public IdentifierRecord(SeedControlHeader header) {
		super(header);
	}

	public void setB005(B005 b005) throws SeedException {
		this.b005 = b005;
	}

	public void setB008(B008 b008) {
		this.b008 = b008;
	}

	public void setB010(B010 b010) {
		this.b010 = b010;
	}

	public void setB012(B012 b012) {
		this.b012 = b012;
	}

	public B011 getB011() {
		return this.b011;
	}

	public IdentifierBlockette add(IdentifierBlockette t) throws SeedException {
		switch (t.getType()) {
		case 5:
			this.b005 = (B005) t;
			break;
		case 8:
			this.b008 = (B008) t;
			break;
		case 10:
			this.b010 = (B010) t;
			break;
		case 11:
			this.b011 = (B011) t;
			break;
		case 12:
			this.b012 = (B012) t;
			break;
		}
		return t;
	}

	public boolean addAll(Collection<IdentifierBlockette> c) throws SeedException {
		int size = this.size();
		for (IdentifierBlockette i : c) {
			add(i);
		}
		return size != this.size();
	}

	public int size() {
		int size = 0;
		if (this.b005 != null) {
			size++;
		}
		if (this.b008 != null) {
			size++;
		}
		if (this.b010 != null) {
			size++;
		}
		if (this.b011 != null) {
			size++;
		}
		if (this.b012 != null) {
			size++;
		}
		return size;
	}

	public void clear() {
		this.b005 = null;
		this.b008 = null;
		this.b010 = null;
		this.b011 = null;
		this.b012 = null;

	}

	public List<IdentifierBlockette> blockettes() {
		List<IdentifierBlockette> list = new ArrayList<>();
		if (this.b005 != null) {
			list.add(b005);
		}
		if (this.b008 != null) {
			list.add(b008);
		}
		if (this.b010 != null) {
			list.add(b010);
		}
		if (this.b011 != null) {
			list.add(b011);
		}
		if (this.b012 != null) {
			list.add(b012);
		}
		return list;
	}

	public IdentifierBlockette get(int... number) {

		for (int n : number) {
			switch (n) {
			case 5:
				return b005;
			case 8:
				return b008;
			case 10:
				return b010;
			case 11:
				return b011;
			case 12:
				return b012;
			}
		}
		return null;
	}

	@Override
	public int writeTo(OutputStream outputStream, int recordLength, int sequence) throws SeedException, IOException {
		SeedOutputStream stream = new SeedOutputStream(outputStream, recordLength, sequence,
				this.getHeader().getRecordType());
		stream.write(blockettes());
		return stream.flush();
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

		public IdentifierRecord build() throws SeedException {
			IdentifierRecord record = new IdentifierRecord(SeedControlHeader.Builder.newInstance(bytes).build());

			BlocketteIterator<IdentifierBlockette> it = new BlocketteIterator<IdentifierBlockette>(8, bytes);
			while (it.hasNext()) {
				IdentifierBlockette b = it.next();
				record.add(b);
			}
			return record;
		}
	}
}
