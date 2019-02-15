package edu.iris.dmc.seed;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.iris.dmc.seed.control.dictionary.B030;
import edu.iris.dmc.seed.control.dictionary.B031;
import edu.iris.dmc.seed.control.dictionary.B033;
import edu.iris.dmc.seed.control.dictionary.B034;
import edu.iris.dmc.seed.control.dictionary.DictionaryBlockette;
import edu.iris.dmc.seed.control.station.ResponseBlockette;
import edu.iris.dmc.seed.control.dictionary.AbstractDictionaryBlockette;

public class Dictionary {

	private int b030 = 1;
	private int b031 = 1;
	private int b032 = 1;
	private int b033 = 1;
	private int b034 = 1;
	private int b035 = 1;
	private int b060 = 0;

	private Map<String, B030> b030Map = new HashMap<>();
	private Map<String, B031> b031Map = new HashMap<>();
	private Map<String, B033> b033Map = new HashMap<>();
	private Map<String, B034> b034Map = new HashMap<>();
	private Map<Integer, ResponseBlockette> responseMap = new HashMap<>();
	private DictionaryMap dictionaryMap = new DictionaryMap();

	public DictionaryBlockette put(AbstractDictionaryBlockette b) throws SeedException {
		int type = b.getType();
		int lookupId = 0;
		if (30 == type) {
			String key = ((B030) b).getName() + "" + ((B030) b).getDataFamilyType();
			B030 blockette = b030Map.get(key);
			if (blockette != null) {
				return blockette;
			}
			this.b030Map.put(key, (B030) b);
			lookupId = b030++;
		} else if (31 == type) {
			B031 blockette = (B031) b;
			String key = blockette.getClassCode() + blockette.getDescription() + blockette.getUnitsOfCommentLevel();

			blockette = b031Map.get(key);
			if (blockette != null) {
				return blockette;
			}
			this.b031Map.put(key, (B031) b);
			if (b031 >= 9999) {
				throw new SeedException("b031 lookup code exceeds available space " + 9999);
			}
			lookupId = this.b031++;
		} else if (32 == type) {
			if (b032 >= 99) {
				throw new SeedException("b032 lookup code exceeds available space " + 99);
			}
			lookupId = this.b032++;
		} else if (33 == type) {
			String key = ((B033) b).getDescription();
			B033 blockette = b033Map.get(key);
			if (blockette != null) {
				return blockette;
			}
			this.b033Map.put(key, (B033) b);
			if (b033 >= 999) {
				throw new SeedException("b033 lookup code exceeds available space " + 999);
			}
			lookupId = this.b033++;
		} else if (34 == type) {
			String key = ((B034) b).getName() + "" + ((B034) b).getDescription();
			B034 blockette = b034Map.get(key);
			if (blockette != null) {
				return blockette;
			}
			if (b034 >= 999) {
				throw new SeedException("b034 lookup code exceeds available space " + 99);
			}
			this.b034Map.put(key, (B034) b);
			lookupId = this.b034++;
		} else if (35 == type) {
			if (b035 >= 999) {
				throw new SeedException("b035 lookup code exceeds available space " + 999);
			}
			lookupId = this.b035++;
		} else if (type > 40 && type < 50) {
			// ReferenceBlockette r = (ReferenceBlockette) b;
			// referenceMap.put(b.getType(), r);
			int id = b.getLookupKey();
			if (id <= 0) {
				id = this.b060++;
				b.setLookupKey(id);
			}
			responseMap.put(id, (ResponseBlockette) b);
		} else {
			throw new SeedException("Could not add blockette type " + type + " to dictionary headers.  Type is invalid."
					+ b.toSeedString());
		}
		if (b.getLookupKey() < 1) {
			b.setLookupKey(lookupId);
		}
		this.dictionaryMap.put(b.getType(), b.getLookupKey(), b);
		return b;
	}

	public AbstractDictionaryBlockette get(int blocketteType, int lookupCode) {
		return this.dictionaryMap.get(blocketteType, lookupCode);
	}

	public ResponseBlockette getResponse(int lookupCode) {
		return this.responseMap.get(lookupCode);
	}

	public List<Blockette> getAll() {
		return this.dictionaryMap.getAll();
	}

	public boolean isEmpty() {
		return this.dictionaryMap.isEmpty();
	}

	public int size() {
		return this.dictionaryMap.size();
	}

	@Override
	public String toString() {
		return "DictionaryHeader [dictionaryMap=" + dictionaryMap + "]";
	}

}
