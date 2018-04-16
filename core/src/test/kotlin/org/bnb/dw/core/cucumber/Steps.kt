package org.bnb.dw.core.cucumber

import cucumber.api.java8.En
import org.assertj.core.api.Assertions.assertThat

class Steps : En {

    private var noun: String = ""
    private var gender: String = ""
    private var choice: String = ""

    init {
        Given("^a noun (.+) with a gender (.+)$") { noun: String, gender: String ->
            this.noun = noun
            this.gender = gender
        }

        When("the user chooses gender (.+)") { choice: String ->
            this.choice = choice
        }

        Then("the answer was correct: (\\w+)") { correct: Boolean ->
            assertThat(choice == gender).isEqualTo(correct)
        }
    }
}
