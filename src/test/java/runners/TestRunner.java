package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/index.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml",
                "timeline:target/cucumber-reports/timeline"
        },
        monochrome = true,
        publish = true,
        tags = "@smoke or @positive or @negative or @boundary"
)
public class TestRunner {
}

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/smoke-html",
                "json:target/cucumber-reports/smoke.json"
        },
        monochrome = true,
        tags = "@smoke"
)
class SmokeTestRunner {
}

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/positive-html",
                "json:target/cucumber-reports/positive.json"
        },
        monochrome = true,
        tags = "@positive"
)
class PositiveTestRunner {
}

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/negative-html",
                "json:target/cucumber-reports/negative.json"
        },
        monochrome = true,
        tags = "@negative"
)
class NegativeTestRunner {
}

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/boundary-html",
                "json:target/cucumber-reports/boundary.json"
        },
        monochrome = true,
        tags = "@boundary"
)
class BoundaryTestRunner {
}