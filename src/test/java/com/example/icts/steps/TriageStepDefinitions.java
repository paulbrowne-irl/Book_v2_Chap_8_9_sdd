package com.example.icts.steps;

import com.example.icts.model.Patient;
import com.example.icts.model.TriageResult;
import com.example.icts.service.TriageEngine;
import com.example.icts.service.TriageRuleGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;

@CucumberContextConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class TriageStepDefinitions {

    @Autowired
    private TriageRuleGenerator ruleGenerator;

    @Autowired
    private TriageEngine triageEngine;

    private Patient patient;
    private TriageResult result;

    @Given("the PDF specification file exists")
    public void pdfExists() {
        File file = new File("spec/national-emergency-medicine-programme-irish-childrens-triage-system-icts.pdf");
        Assertions.assertTrue(file.exists(), "PDF Specification file should exist");
    }

    @When("I trigger the rule generation")
    public void triggerRuleGeneration() {
        ruleGenerator.generateRules("spec/national-emergency-medicine-programme-irish-childrens-triage-system-icts.pdf",
                "generated_rules/triage.drl");
    }

    @Then("the rules file should be created")
    public void rulesFileCreated() {
        File file = new File("generated_rules/triage.drl");
        Assertions.assertTrue(file.exists(), "DRL file should be created");
        Assertions.assertTrue(file.length() > 0, "DRL file should not be empty");
    }

    @Then("the rules should be loaded into the engine")
    public void rulesLoaded() {
        triageEngine.reloadRules();
    }

    @Given("the rules are loaded")
    public void rulesAreLoaded() {
        File file = new File("generated_rules/triage.drl");
        if (!file.exists()) {
            ruleGenerator.generateRules(
                    "spec/national-emergency-medicine-programme-irish-childrens-triage-system-icts.pdf",
                    "generated_rules/triage.drl");
        }
        triageEngine.reloadRules();
    }

    @When("I triage a patient with age {int} and symptom {string}")
    public void triagePatient(int age, String symptom) {
        patient = new Patient();
        patient.setAge(age);
        patient.addSymptom(symptom);
        result = triageEngine.triage(patient);
    }

    @Then("the triage result should be {string} with priority {int}")
    public void checkResult(String color, int priority) {
        Assertions.assertNotNull(result, "Result should not be null");
        Assertions.assertEquals(color, result.getColor());
        Assertions.assertEquals(priority, result.getPriority());
    }

    @Then("the triage result should be {string} or {string}")
    public void checkResultOr(String color1, String color2) {
        Assertions.assertNotNull(result, "Result should not be null");
        Assertions.assertTrue(result.getColor().equals(color1) || result.getColor().equals(color2),
                "Result was " + result.getColor() + " but expected " + color1 + " or " + color2);
    }
}
