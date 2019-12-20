package edu.iris.dmc.seed.blockette.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.Record;
import edu.iris.dmc.seed.SeedException;

/**
 * 
 * @author Suleiman
 *
 */
public class BItrator implements Iterator<Blockette> {

	private Record record;
	Queue<Blockette> queue = new LinkedList<>();

	Blockette next = null;
	boolean noElement;

	public BItrator(Record record) throws SeedException {
		this.record = record;
		advance();
	}

	private void advance() throws SeedException {
		Blockette blockette = record.next();
		if (blockette == null) {
			noElement = true;
		} else {
			next = blockette;
			noElement = false;
		}
	}

	@Override
	public Blockette next() {
		try {
			Blockette blockette = next;
			advance();
			System.out.println(next);
			return blockette;
		} catch (SeedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean hasNext() {
		return !noElement;
	}
}
