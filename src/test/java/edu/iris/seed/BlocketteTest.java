package edu.iris.seed;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BlocketteTest {

	static Map<Integer, List<String>> examplesMap = new HashMap<>();

	@BeforeAll
	public static void setUp() throws Exception {

		try (InputStream stream = BlocketteTest.class.getClassLoader().getResourceAsStream("example.blockettes");

				BufferedReader br = new BufferedReader(new InputStreamReader(stream));) {

			// read until end of file
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.trim().isEmpty()) {
					int key = Integer.parseInt(line.substring(0, 3));
					List<String> list = examplesMap.get(key);
					if (list == null) {
						list = new ArrayList<>();
						examplesMap.put(key, list);
					}
					list.add(line);
				}
			}
		}
	}

	@Test
	public void run() throws Exception {

		for (Entry<Integer, List<String>> e : examplesMap.entrySet()) {
			List<String> list = e.getValue();

			for (String expected : list) {
				Blockette blockette = SeedBlockette.builder(e.getKey()).fromBytes(expected.getBytes()).build();
				if (blockette instanceof ControlBlockette) {
					assertEquals(expected, ((ControlBlockette) blockette).toSeedString());
				}
			}
		}
	}
}
