package edu.iris.dmc.seed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Index {

	private Blockette b005;
	private Blockette b008;
	private Blockette b010;
	private Blockette b011;
	private Blockette b012;

	public Blockette add(Blockette blockette) {
		if(5==blockette.getType()) {
			this.b005=blockette;
			return this.b005;
		}else if(8==blockette.getType()) {
			this.b008=blockette;
			return this.b008;
		}else if(10==blockette.getType()) {
			this.b010=blockette;
			return this.b010;
		}else if(11==blockette.getType()) {
			this.b011=blockette;
			return this.b011;
		}else if(12==blockette.getType()) {
			this.b012=blockette;
			return this.b012;
		}
		return null;
	}

	public Blockette getB010() {
		return this.b010;
	}

	public Blockette getB011() {
		return this.b011;
	}

	public List<Blockette> getAll() {
		Arrays.asList(this.b005,this.b008,this.b010,this.b012);
		List<Blockette> l = new ArrayList<>();
		if (b005 != null) {
			l.add(b005);
		}
		if (b008 != null) {
			l.add(b008);
		}
		if (b010 != null) {
			l.add(b010);
		}
		if (b011 != null) {
			l.add(b011);
		}
		if (b012 != null) {
			l.add(b012);
		}
		return l;
	}

	public boolean isEmpty() {
		return b005 == null && b008 == null && b010 == null && b011 != null && b012 == null;
	}
}
