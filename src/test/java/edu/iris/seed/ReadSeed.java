package edu.iris.seed;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.iris.seed.io.SeedFileUtils;


public class ReadSeed {

	public static void main(String[] args) throws Exception {

		try (Stream<Path> walk = Files.walk(Paths.get("/Users/Suleiman/dataless"))) {

			List<Path> result = walk.filter(Files::isRegularFile)
					.filter(f -> f.getFileName().toString().contains("less")).collect(Collectors.toList());

			for (Path p : result) {
				System.out.println(">>>" + p.toString());
				SeedFileUtils.toSeedVolume(p.toFile());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main1(String[] args) throws Exception {

		try (Stream<Path> walk = Files.walk(Paths.get("/Users/Suleiman/dataless/ZR_2013_fromSEISUK.dataless.seed"))) {

			List<Path> result = walk.filter(Files::isRegularFile).collect(Collectors.toList());

			for (Path p : result) {
				System.out.println(">>>" + p.toString());
				SeedFileUtils.toSeedVolume(p.toFile());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
