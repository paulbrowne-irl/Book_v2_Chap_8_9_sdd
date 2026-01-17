# Irish Children’s Triage System (ICTS) - Specification (Merged)

## Overview

A Java-based application that digitizes the National Emergency Medicine Programme Irish Children’s Triage System (ICTS). It parses PDF guidelines to generate executable business rules and provides a **Java Swing GUI** for triaging patients.

**Purpose:** Automate the triage process using rule-based logic derived directly from official medical guidelines, assisting healthcare professionals in determining clinical priority.

---

## What Users Can Do

1.  **Generate Rules:** Parse the official ICTS PDF to create executable Drools rules.
2.  **Simulate Triage:** Input a patient's age and symptoms via a GUI.
3.  **Select Symptoms:** Choose from a dynamic dropdown list of symptoms extracted from the generated rules.
4.  **View Results:** Instantly see the Triage Category (Colour) and Priority in the GUI display area.
5.  **Save Output:** Save the triage result to a file.
6.  **Clear Form:** Clear previous patient information.
7.  **Exit:** Exit the application.

---

## User Interface (Java Swing)

The interface is a Java Swing Application replacing the previous CLI.

### Features
*   **Input Fields:**
    *   **Age:** Text input or spinner for patient age.
    *   **Symptom:** Dropdown (ComboBox) populated dynamically from the `triage.drl` file (or internal rule structure).
*   **Buttons:**
    *   **Submit/Triage:** Triggers the triage engine.
    *   **Clear:** Resets inputs and output display.
    *   **Save Output:** Saves the current result to a text file.
    *   **Exit:** Closes the application.
*   **Display Area:**
    *   Shows the triage result (Color, Priority).

---

## Technical Architecture

### Business Components
*   **Data Model**:
    *   `Patient`: Holds ID, Age, List of Symptoms.
    *   `Symptom`: Wrapper for symptom strings.
    *   `TriageResult`: Encapsulates Color and Priority.

### Core Components
1.  **User Interface (Swing GUI)**:
    *   Replaces the Console/CLI.
    *   Implemented using Java Swing (`JFrame`, `JPanel`, `JButton`, `JComboBox`, etc.).
2.  **PDF Parser (`TriageRuleGenerator`)**:
    *   Library: `Apache PDFBox`
    *   Logic: Parses the PDF to generate the `triage.drl` file.
    *   **Constraint:** Do not manualy update the `triage.drl` file; it should be generated.
3.  **Rules Engine (`TriageEngine`)**:
    *   Library: `Drools (Apache KIE)`
    *   Logic: Executes rules against the patient data.
4.  **Spring Boot**:
    *   The application context is managed by Spring Boot.
    *   The GUI launch should be integrated with the Spring Boot startup cycle (e.g., setting `headless(false)`).

### Testing
*   Update tests to verify the core logic and GUI components where feasible.
*   Ensure BDD tests (Serenity/Cucumber) still pass for the core logic.

### Deployment
*   Maven build.
*   `run.bat` (to be created/updated) to launch the Swing GUI.

---

## Deliverables
1.  **Source Code**: Updated Java code with Swing GUI.
2.  **pom.xml**: Updated if necessary (Swing is part of Java SE, so no extra deps usually, but check for testing libs like AssertJ-Swing if needed, though not explicitly requested).
3.  **README.md**: Updated instructions for running the GUI.
4.  **run.bat**: Script to run the application.

## Constraints
*   **Do not update the triage.drl file** manually.
