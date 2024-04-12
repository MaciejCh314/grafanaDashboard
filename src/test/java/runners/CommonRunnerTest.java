package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = {"stepsDefinitions"},
        plugin = {
                "pretty",
                "json:target/Json-reports/cucumber-json-report.json",
                "html:target/site/cucumber-pretty",
                "runners.StepDetails"
        },
        monochrome = true

)
public class CommonRunnerTest {

}