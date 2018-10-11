package edu.iris.dmc.seed.record;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.control.index.B010;
import edu.iris.dmc.seed.control.index.B011;

public class VolumeRecord extends AbstractRecord {

	public VolumeRecord(int sequence, boolean continuation) {
		super(sequence, 'V', continuation);
	}

	public VolumeRecord(int size, int sequence, boolean continuation) {
		super(sequence, 'V', continuation, size);
	}

	private B011 b011;
	private B010 b010;

	public void add(Blockette b) {
		if (b instanceof B010) {
			add((B010) b);
		} else if (b instanceof B011) {
			add((B011) b);
		} else {

		}
	}

	public void add(B011 b) {
		this.b011 = b;
	}

	public void add(B010 b) {
		this.b010 = b;
	}

	@Override
	public byte[] getBytes() {
		byte[] bytes = new byte[this.size()];
		byte[] s = buildSequence();
		System.arraycopy(s, 0, bytes, 0, s.length);

		int index = s.length;
		if (b010 != null) {
			byte[] array = b010.toSeedString().getBytes();
			System.arraycopy(array, 0, bytes, index, array.length);
			index += array.length;
		}

		if (b011 != null) {
			byte[] array = b011.toSeedString().getBytes();
			System.arraycopy(array, 0, bytes, index, array.length);
			index += array.length;
		}
		return bytes;
	}

}
