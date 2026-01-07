package com.triage.steps;

import com.triage.model.Patient;
import com.triage.model.Symptom;
import com.triage.model.TriageResult;
import com.triage.rules.TriageEngine;
import com.triage.rules.TriageRuleGenerator;
import com.triage.service.RuleGenerationService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TriageStepDefinitions {

    private TriageEngine engine;
    private Patient patient;
    private TriageResult result;
    private static final String TEST_DRL_PATH = "target/test-classes/generated_rules/triage_test.drl";
    private static final String PDF_PATH = "spec/national-emergency-medicine-programme-irish-childrens-triage-system-icts.pdf";

    @Before
    public void setup() throws IOException {
        // Ensure rules are generated for testing
        File drlFile = new File(TEST_DRL_PATH);
        if (!drlFile.exists()) {
            new TriageRuleGenerator().generate(PDF_PATH, TEST_DRL_PATH);
        }
    }

    @Given("the Triage System is initialized with valid rules")
    public void initializedWithValidRules() {
        engine = new TriageEngine();
        engine.setDrlPath(TEST_DRL_PATH);
        engine.init();
    }

    @When("a patient of age {int} with symptom {string} is triaged")
    public void patientWithSymptomIsTriaged(int age, String symptomName) {
        patient = new Patient();
        patient.setAge(age);
        List<Symptom> symptoms = new ArrayList<>();
        symptoms.add(new Symptom(symptomName));
        patient.setSymptoms(symptoms);

        result = engine.executeTriage(patient);
    }

    @Then("the triage result should be {string}")
    public void triageResultShouldBe(String expectedColor) {
        Assertions.assertEquals(expectedColor, result.getColor());
    }

    @Then("the priority should be {int}")
    public void priorityShouldBe(int expectedPriority) {
        // Assuming TriageResult has logic to map Color -> Priority or we check logic
        // For now, let's map color to priority for verification if not present in model
        int actualPriority = getPriorityFromColor(result.getColor());
        Assertions.assertEquals(expectedPriority, actualPriority);
    }

    @Then("the triage result should be {string} or {string}")
    public void triageResultShouldBeOneOf(String color1, String color2) {
        String actual = result.getColor();
        Assertions.assertTrue(actual.equals(color1) || actual.equals(color2),
                "Expected " + color1 + " or " + color2 + " but got " + actual);
    }

    private int getPriorityFromColor(String color) {
        switch (color) {
            case "Red":
                return 1;
            case "Orange":
                return 2;
            case "Yellow":
                return 3;
            case "Green":
                return 4;
            case "Blue":
                return 5;
            default:
                return 0;
        }
    }
}
