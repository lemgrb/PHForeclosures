package com.phforeclosures.cucumber;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnitConfigurable;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/phforeclosures/cucumber/features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.phforeclosures.cucumber.glue")
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberIntegrationTest {
}
