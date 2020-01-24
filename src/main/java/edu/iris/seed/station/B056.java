package edu.iris.seed.station;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedException;

public class B056 extends AbstractResponseBlockette {

	public B056() {
		super(56, "Generic Response Blockette");

	}

	@Override
	public String toSeedString() {
		// TODO Auto-generated method stub
		return null;
	}

	public BlocketteBuilder<B056> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B056> {

		public Builder() {
			super(56);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B056 build() throws SeedException { 
			return null;
		}
	}
}
