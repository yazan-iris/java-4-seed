package edu.iris.seed.record;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import edu.iris.seed.SeedException;
import edu.iris.seed.SeedOutputStream;
import edu.iris.seed.index.B005;
import edu.iris.seed.index.B008;
import edu.iris.seed.index.B010;
import edu.iris.seed.index.B011;
import edu.iris.seed.index.B012;
import edu.iris.seed.index.IndexBlockette;
import edu.iris.seed.record.Header.Type;

public class VolumeRecord extends SeedRecord<IndexBlockette> {

	private B005 b005;
	private B008 b008;
	private B010 b010;
	private B011 b011 = new B011();;
	private B012 b012;

	public VolumeRecord() {
		this(0, false);
	}

	public VolumeRecord(int sequence, boolean continuation) {
		this(ControlHeader.Builder.newInstance().build(sequence, Type.V, continuation));
	}

	public VolumeRecord(ControlHeader header) {
		super(header);
	}

	public void setB005(B005 b005) {
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

	@Override
	public IndexBlockette add(IndexBlockette t) throws SeedException {
		switch (t.getType()) {
		case 5:
			this.b005 = (B005) t;
			break;
		case 8:
			this.b008 = (B008) t;
			break;
		case 10:
			if (this.b010 != null) {
				throw new SeedException(
						"A blockette B010 already exist in this document.  B010 is included only once per volume.");
			}
			this.b010 = (B010) t;
			break;
		case 11:
			// throw new SeedException(
			// "Adding B011 is not allowed, it is the responsibilty of this volume to create
			// and manage this blockette.");
			break;
		case 12:
			this.b012 = (B012) t;
			break;
		}
		return t;
	}

	public IndexBlockette get(int... number) {

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

	public List<IndexBlockette> getAll() {
		List<IndexBlockette> l = new ArrayList<>();
		if (this.b005 != null) {
			l.add(this.b005);
		}
		if (this.b008 != null) {
			l.add(this.b008);
		}

		if (this.b010 != null) {
			l.add(this.b010);
		}

		if (this.b011 != null) {
			l.add(this.b011);
		}

		if (this.b012 != null) {
			l.add(this.b012);
		}

		return l;
	}

	public boolean isEmpty() {
		return this.getAll().isEmpty();
	}

	public int size() {
		return this.getAll().size();
	}

	public int getNumberOfBlockettes() {
		int cnt = 0;
		if (this.b005 != null) {
			cnt++;
		}
		if (this.b008 != null) {
			cnt++;
		}

		if (this.b010 != null) {
			cnt++;
		}

		if (this.b011 != null) {
			cnt++;
		}

		if (this.b012 != null) {
			cnt++;
		}

		return cnt;
	}

	@Override
	public int writeTo(OutputStream outputStream, int recordLength, int sequence) throws SeedException, IOException {
		SeedOutputStream stream = new SeedOutputStream(outputStream, recordLength, sequence,
				this.getHeader().getType());
		stream.write(getAll());
		return stream.flush();
	}

	public static class Builder {

		private Builder() {
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public VolumeRecord build(byte[] bytes) throws SeedException {
			VolumeRecord record = new VolumeRecord(ControlHeader.Builder.newInstance().build(bytes));
			record.setBytes(bytes);
			return record;
		}
	}
}
