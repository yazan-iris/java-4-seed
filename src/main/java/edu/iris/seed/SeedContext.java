package edu.iris.seed;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SeedContext {
	private static SeedContext context = new SeedContext();
	private Map<Integer, BlocketteDefinition> map = new HashMap<>();

	private SeedContext() {
		ObjectMapper objectMapper = new ObjectMapper();
		try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream("seed.2.4.manual")) {
			List<BlocketteDefinition> blockettes = objectMapper.readValue(stream,
					new TypeReference<List<BlocketteDefinition>>() {
					});
			for (BlocketteDefinition b : blockettes) {
				map.put(b.number, b);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static SeedContext get() {
		return context;
	}

	public BlocketteDefinition get(int blocketteNumber) {
		return map.get(blocketteNumber);
	}

	public static void main(String[] args) {
		SeedContext manual = new SeedContext();
		System.out.println(manual.get(71));
	}
}