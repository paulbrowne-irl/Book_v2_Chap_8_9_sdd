Feature: Triage System Logic
  In order to prioritize patient care
  As a healthcare professional
  I want to triage patients based on their symptoms and age

  Scenario: Generate Rules from PDF
    Given the PDF specification file exists
    When I trigger the rule generation
    Then the rules file should be created
    And the rules should be loaded into the engine

  Scenario: Triage a high priority patient
    Given the rules are loaded
    When I triage a patient with age 5 and symptom "Airway compromise"
    Then the triage result should be "Red" with priority 1

  Scenario: Triage a lower priority patient
    Given the rules are loaded
    When I triage a patient with age 8 and symptom "Minor limb injury"
    Then the triage result should be "Green" or "Blue"
