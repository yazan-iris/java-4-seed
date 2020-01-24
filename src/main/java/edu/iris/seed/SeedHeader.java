package edu.iris.seed;


public interface SeedHeader {

	enum Type {
		V('V'), A('A'), S('S'), T('T'), D('D'), R('R'), M('M'), Q('Q'), E('E');

		private char c;

		private Type(char c) {
			this.c = c;
		}

		public char valueAsChar() {
			return c;
		}

		public static Type from(char c) throws SeedException {
			switch (c) {
			case 'V':
				return V;
			case 'A':
				return A;
			case 'S':
				return S;
			case 'T':
				return T;
			case 'D':
				return D;
			case 'R':
				return R;
			case 'M':
				return M;
			case 'Q':
				return Q;
			case 'E':
				return E;
			case ' ':
				return E;
			}
			throw new SeedException("Invalid record type [{}]", c);
		}
	}
	public int getSequence();

	public Type getRecordType();

	public boolean isContinuation();
}
