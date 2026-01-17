package com.example.icts.runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "cucumber.glue", value = "com.example.icts.steps")
@ConfigurationParameter(key = "cucumber.plugin", value = "pretty")
@ConfigurationParameter(key = "cucumber.object-factory", value = "io.cucumber.spring.SpringFactory")
public class TestRunner {
}
