package com.stepsdefinitions;

import com.interactions.DeleteBusinessUnit;
import com.questions.ValidatesBusinessMeeting;
import com.questions.ValidatesBusinessUnit;
import com.questions.ValidatesUser;
import com.tasks.CreatesBusinessMeeting;
import com.tasks.CreatesNewBusinessUnit;
import com.tasks.DeletesSchedules;
import com.tasks.LogIn;
import com.utils.NavigateTo;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.hamcrest.CoreMatchers;

import java.util.Map;

import static com.utils.SessionVariables.BUSINESS_UNIT_NAME;
import static com.utils.SessionVariables.MEETING_NAME_VALUE;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class StepsDefinitions {

    @Before
    public void startsPage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("^(.*) opens the browser in the home page$")
    public void opensBrowserOnHomePage(String actor) {
        //WebDriverManager.chromedriver().setup();
        theActorCalled(actor).attemptsTo(NavigateTo.theWebSideHome());
    }

    @When("^he enters his credentials, (.*) and (.*)$")
    public void entersTheCredentials(String user, String pass) {
        theActorInTheSpotlight().attemptsTo(LogIn.forLoginIn(user, pass));
    }

    @Given("^he should see the (.*) in the login home$")
    public void validatesTheTextInThePaymentForm(String user) {
        theActorInTheSpotlight().should(seeThat(ValidatesUser.displayed(), CoreMatchers.equalTo(user)));
    }

    @Given("^he creates new business unit as (.*)$")
    public void createNewBusinessUnit(String businessUnit) {
        theActorInTheSpotlight().attemptsTo(CreatesNewBusinessUnit.letsGo(businessUnit));
    }

    @When("^he should see the business unit name$")
    public void validatesBusinessUnitName() {
        theActorInTheSpotlight().should(seeThat(ValidatesBusinessUnit.wasCreate(),
                CoreMatchers.equalTo(theActorInTheSpotlight().recall(BUSINESS_UNIT_NAME.toString()))));
    }

    @Then("^he should delete business unit$")
    public void deleteBusinessUnit() {
        theActorInTheSpotlight().attemptsTo(DeleteBusinessUnit.letsGo());
    }

    @And("^he schedules a business meeting$")
    public void scheduleAMeet(Map<String, String> meetingInfo) {
        theActorInTheSpotlight().attemptsTo(CreatesBusinessMeeting.letsGo(meetingInfo));
    }

    @Then("^he should see the business meeting$")
    public void validatesBusinessMeeting() {
        theActorInTheSpotlight().should(seeThat(ValidatesBusinessMeeting.wasCreate(),
                CoreMatchers.equalTo(theActorInTheSpotlight().recall(MEETING_NAME_VALUE.toString()))));
    }

    @And("^he should delete meeting and unit$")
    public void cleanSchedules() {
        theActorInTheSpotlight().attemptsTo(DeletesSchedules.letsGo());
    }

}
