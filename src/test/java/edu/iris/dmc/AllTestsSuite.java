package edu.iris.dmc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import edu.iris.dmc.seed.BTime;
import edu.iris.dmc.seed.BTimeTest;
import edu.iris.dmc.seed.BlocketteBuilderTest;
import edu.iris.dmc.seed.DictionaryHeaderTest;
import edu.iris.dmc.seed.SeedStringBuilderTest;
import edu.iris.dmc.seed.VolumeTest;
import edu.iris.dmc.seed.control.dictionary.B005Test;
import edu.iris.dmc.seed.control.dictionary.B008Test;
import edu.iris.dmc.seed.control.dictionary.B010Test;
import edu.iris.dmc.seed.control.dictionary.B011Test;
import edu.iris.dmc.seed.control.dictionary.B012Test;
import edu.iris.dmc.seed.control.dictionary.B033Test;
import edu.iris.dmc.seed.control.dictionary.B034Test;
import edu.iris.dmc.seed.control.dictionary.B040sTest;
import edu.iris.dmc.seed.control.station.B050Test;
import edu.iris.dmc.seed.control.station.B051Test;
import edu.iris.dmc.seed.control.station.B052Test;
import edu.iris.dmc.seed.control.station.B053Test;
import edu.iris.dmc.seed.control.station.B054Test;
import edu.iris.dmc.seed.control.station.B057Test;
import edu.iris.dmc.seed.control.station.B058Test;
import edu.iris.dmc.seed.control.station.B059Test;
import edu.iris.dmc.seed.control.station.B061Test;
import edu.iris.dmc.seed.control.station.B062Test;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({ BlocketteBuilderTest.class, BTimeTest.class, DictionaryHeaderTest.class,
		SeedStringBuilderTest.class, VolumeTest.class, B005Test.class, B008Test.class, B010Test.class, B011Test.class,
		B012Test.class, B033Test.class, B034Test.class, B040sTest.class, B050Test.class, B051Test.class, B052Test.class,
		B053Test.class, B054Test.class, B057Test.class, B058Test.class, B059Test.class, B061Test.class,
		B062Test.class })
public final class AllTestsSuite {
} // or ModuleFooSuite, and that in AllTests
