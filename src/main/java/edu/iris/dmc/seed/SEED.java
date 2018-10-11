package edu.iris.dmc.seed;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SEED {
	public static int[] INDEX = new int[] { 5, 8, 10, 11, 12 };
	public static int[] DICTIONARY = new int[] { 30, 31, 32, 33, 34, 35, 41, 43, 44, 45, 46, 47, 48 };
	public static int[] STATION = new int[] { 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62 };
	public static int[] TIMESPAN = new int[] { 70, 71, 72, 73, 74 };

	public static Set<Integer> TYPES = new HashSet<Integer>(Arrays.asList(5, 8, 10, 11, 12, 30, 31, 32, 33, 34, 35, 41,
			43, 44, 45, 46, 47, 48, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 70, 71, 72, 73, 74));

}
