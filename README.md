# Irish Children’s Triage System (ICTS)

A Java-based application that digitizes the National Emergency Medicine Programme Irish Children’s Triage System (ICTS). This system parses clinical guidelines from a PDF document to generate executable business rules using Drools, providing both a **Swing GUI** and a REST API for triaging patients.

## Features

-   **Dynamic Rule Generation**: Parses the ICTS PDF guidelines to generate Drools (`.drl`) rules automatically.
-   **Triage Engine**: Uses Apache KIE (Drools) to evaluate patient symptoms against generated rules.
-   **Interfaces**:
    -   **GUI**: Java Swing desktop interface for interactive triage.
    -   **REST API**: Endpoint for integrating with other systems.
-   **Testing**: Comprehensive BDD tests using Serenity BDD and Cucumber.

## Prerequisites

-   Java 21
-   Maven 3.x

## Build and Run

1.  **Build the project**:
    ```bash
    mvn clean install
    ```

2.  **Run the application (GUI)**:
    Double-click `run.bat` or execute:
    ```bash
    mvn spring-boot:run
    ```
    The GUI window should appear.

3.  **Run Tests**:
    ```bash
    mvn clean verify
    ```

## Usage

### GUI
1.  **Launch**: Run the application.
2.  **Input**: Enter patient Age and select a Symptom from the dropdown.
3.  **Triage**: Click "Triage" to see the result.
4.  **Save**: Click "Save Output" to save the result to `triage_output.txt`.
5.  **Clear/Exit**: Use the respective buttons.

### REST API
You can also triage a patient via the REST API:

**Endpoint**: `POST /api/triage`
**Body**:
```json
{
  "age": 5,
  "symptoms": [
    { "name": "Airway compromise" }
  ]
}
```

## Architecture

-   **Core**: Spring Boot 3.2.x
-   **Rule Engine**: Drools 8.x
-   **PDF Parsing**: Apache PDFBox 3.0
-   **UI**: Java Swing (Headless mode disabled)
