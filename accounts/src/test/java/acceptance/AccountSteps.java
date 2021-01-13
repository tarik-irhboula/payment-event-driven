package acceptance;

import static org.junit.jupiter.api.Assertions.*;

import com.sfeir.kata.accounts.domain.Account;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AccountSteps {

    private String email;
    private Account account;

    @Given("a user with email {string}")
    public void aUserWithEmail(String email) {
        this.email = email;
    }

    @When("I create an account of type {string}")
    public void iCreateAnAccountOfType(String type) throws Exception {
        var resolvedType = Account.Type.valueOf(type);
        this.account = Account.create(resolvedType, this.email);
    }

    @Then("I should have an empty account with id")
    public void iShouldHaveAnEmptyAccountWithId() {
        assertNotNull(this.account);
        assertNotNull(this.account.getId());
    }
}
