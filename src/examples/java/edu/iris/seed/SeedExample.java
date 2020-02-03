package edu.iris.seed;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import edu.iris.seed.io.SeedBlocketteIterator;
import edu.iris.seed.io.SeedIOUtils;
import edu.iris.timeseries.Timeseries;

public class SeedExample {

	public static void main(String[] args) throws Exception {
		File source = new File("");
		File target = new File("");
		try (FileInputStream fileInputStream = new FileInputStream(source);
				OutputStream fileOutputStream = target == null ? System.out : new FileOutputStream(target)) {
			SeedVolume volume = SeedIOUtils.toSeedVolume(fileInputStream);

			for (Blockette b : volume.blockettes()) {
				// do Something
			}
		} catch (SeedException e) {

		}
	}

	public static void example2(String[] args) throws Exception {
		File source = new File("");
		File target = new File("");
		try (FileInputStream fileInputStream = new FileInputStream(source);
				OutputStream fileOutputStream = target == null ? System.out : new FileOutputStream(target)) {
			SeedBlocketteIterator it = SeedIOUtils.toBlocketteIterator(fileInputStream);
			while (it.hasNext()) {
				Blockette b = it.next();
				// do Something
			}
		}
	}

	public static void example3(String[] args) throws Exception {
		File source = new File("");
		File target = new File("");
		try (FileInputStream fileInputStream = new FileInputStream(source);
				OutputStream fileOutputStream = target == null ? System.out : new FileOutputStream(target)) {
			List<Timeseries> list = SeedIOUtils.toTimeseries(fileInputStream, false);
			for (Timeseries ts : list) {
				// do Something
			}
		} catch (SeedException e) {

		}
	}
}
