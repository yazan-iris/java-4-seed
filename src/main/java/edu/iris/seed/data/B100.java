package edu.iris.seed.data;

import edu.iris.seed.SeedException;

public class B100 extends AbstractDataBlockette {

	private float actualSampleRate;

	public B100() {
		super(100, "Sample Rate Blockette");
	}

	public float getActualSampleRate() {
		return actualSampleRate;
	}

	public void setActualSampleRate(float actualSampleRate) {
		this.actualSampleRate = actualSampleRate;
	}

	public static B100 build(byte[] bytes, boolean swap) throws SeedException {
		validate(100, 12, bytes);
		B100 b = new B100();
		b.setActualSampleRate(ByteUtil.fourBytesToFloat(bytes, 4, 4));
		return b;
	}

	@Override
	public String toSeedString() throws SeedException {
		// TODO Auto-generated method stub
		return null;
	}

}
