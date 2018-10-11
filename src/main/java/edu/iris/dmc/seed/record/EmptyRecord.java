package edu.iris.dmc.seed.record;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.Record;
import edu.iris.dmc.seed.SeedException;

public class EmptyRecord implements Record {

	private int sequence;

	public EmptyRecord(int sequence) {
		this.sequence = sequence;
	}

	@Override
	public int getSequence() {
		return this.sequence;
	}

	@Override
	public char getType() {
		return ' ';
	}

	@Override
	public byte[] getBytes() {
		return null;
	}

	@Override
	public byte[] get(int length) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBytes(byte[] bytes) {
		// TODO Auto-generated method stub

	}

	@Override
	public Blockette next() throws SeedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isContinuation() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void add(Blockette blockette) {
		// TODO Auto-generated method stub

	}

	@Override
	public byte[] add(byte[] bytes) {
		// TODO Auto-generated method stub
		return null;
	}

}
