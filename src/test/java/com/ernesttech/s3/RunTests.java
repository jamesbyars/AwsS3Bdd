package com.ernesttech.s3;

/**
 * Created by james on 3/27/17.
 */
import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;
import cucumber.api.CucumberOptions;

@RunWith(Cucumber.class)

@CucumberOptions(
        monochrome = false,
        features = { "src/test/resources/features" },
        glue     = { "com.ernesttech.s3.steps" })
public class RunTests {
}
