package edu.iris.seed.abbreviation;


import edu.iris.seed.SeedException;
import edu.iris.seed.station.ResponseDictionaryBlockette;

public class B046 extends AbstractAbbreviationBlockette implements AbbreviationBlockette,ResponseDictionaryBlockette  {


	
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
	
	public static B046 build046(byte[] bytes) throws SeedException {
		int offset = 7;
		B046 b = new B046();

		return b;
	}
	@Override
	public String getResponseName() {
		// TODO Auto-generated method stub
		return null;
	}
}
