package edu.iris.seed.abbreviation;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedException;
import edu.iris.seed.station.ResponseDictionaryBlockette;

public class B046 extends AbstractAbbreviationBlockette<B046> implements AbbreviationBlockette, ResponseDictionaryBlockette {

	public B046() {
		super(56, "Generic Response Dictionary Blockette");

	}

	@Override
	public String toSeedString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getStageNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getResponseName() {
		// TODO Auto-generated method stub
		return null;
	}

	public BlocketteBuilder<B046> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B046> {

		public Builder() {
			super(46);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B046 build() throws SeedException {
			return new B046();
		}
	}
}
