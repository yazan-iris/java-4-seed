package edu.iris;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("JUnit Platform Suite for validator")
@SelectPackages("edu.iris")
public final class TestRun {
} // or ModuleFooSuite, and that in AllTests

