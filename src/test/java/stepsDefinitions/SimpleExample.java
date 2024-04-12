package stepsDefinitions;

import io.cucumber.java.en.When;
import org.testng.Assert;

public class SimpleExample {

    @When("login")
    public void login() throws InterruptedException {
        Thread.sleep(10000);
        Assert.fail();
    }

    @When("search")
    public void search() throws Exception {
        Thread.sleep(10000);
        Assert.fail();
    }

    @When("select")
    public void select() throws Exception {
        Thread.sleep(10000);
    }

    @When("log out")
    public void logOut() throws Exception {
        Thread.sleep(10000);
    }
}
