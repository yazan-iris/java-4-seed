package edu.iris.dmc.seed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.iris.dmc.seed.control.dictionary.AbstractDictionaryBlockette;

public class DictionaryMap {

	Map<String, AbstractDictionaryBlockette> map = new HashMap<>();

	public AbstractDictionaryBlockette put(int blocketteType, int lookupCode, AbstractDictionaryBlockette blockette) {
		String key=blocketteType + "-" + lookupCode;
		return map.put(key, blockette);
	}

	public AbstractDictionaryBlockette get(int blocketteType, int lookupCode) {
		return this.map.get(blocketteType + "-" + lookupCode);
	}

	public List<Blockette> getAll() {
		return new ArrayList<Blockette>(this.map.values());
	}

	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	public int size() {
		return this.map.size();
	}

	@Override
	public String toString() {
		StringBuilder b=new StringBuilder("DictionaryMap [map=");
		map.entrySet().forEach(entry -> {
		    b.append("Key : " + entry.getKey()).append(" Value : " + entry.getValue()).append("\n");
		});
		b.append("]");
		return b.toString();
	}

}
