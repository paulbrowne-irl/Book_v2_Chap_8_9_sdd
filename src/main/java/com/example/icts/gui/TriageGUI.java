package com.example.icts.gui;

import com.example.icts.model.Patient;
import com.example.icts.model.TriageResult;
import com.example.icts.service.TriageEngine;
import com.example.icts.service.TriageRuleGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component
@Profile("!test")
public class TriageGUI extends JFrame {

    private final TriageEngine triageEngine;
    private final TriageRuleGenerator ruleGenerator;

    private JTextField ageField;
    private JComboBox<String> symptomDropdown;
    private JTextArea resultArea;

    @Autowired
    public TriageGUI(TriageEngine triageEngine, TriageRuleGenerator ruleGenerator) {
        this.triageEngine = triageEngine;
        this.ruleGenerator = ruleGenerator;
        initUI();
    }

    private void initUI() {
        setTitle("Irish Children’s Triage System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Age
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        ageField = new JTextField(10);
        add(ageField, gbc);

        // Symptoms
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Symptom:"), gbc);
        gbc.gridx = 1;
        symptomDropdown = new JComboBox<>();
        refreshSymptoms();
        add(symptomDropdown, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton triageBtn = new JButton("Triage");
        JButton clearBtn = new JButton("Clear");
        JButton saveBtn = new JButton("Save Output");
        JButton exitBtn = new JButton("Exit");

        buttonPanel.add(triageBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(saveBtn);
        buttonPanel.add(exitBtn);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        // Result Area
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createTitledBorder("Triage Result"));
        add(new JScrollPane(resultArea), gbc);

        // Actions
        triageBtn.addActionListener(e -> performTriage());
        clearBtn.addActionListener(e -> clearForm());
        saveBtn.addActionListener(e -> saveOutput());
        exitBtn.addActionListener(e -> System.exit(0));
    }

    private void refreshSymptoms() {
        // Ensure rules exist
        ruleGenerator.generateRules("spec/national-emergency-medicine-programme-irish-childrens-triage-system-icts.pdf",
                "generated_rules/triage.drl");

        List<String> symptoms = ruleGenerator.getAvailableSymptoms("generated_rules/triage.drl");
        Collections.sort(symptoms);
        symptomDropdown.removeAllItems();
        for (String s : symptoms) {
            symptomDropdown.addItem(s);
        }
    }

    private void performTriage() {
        try {
            int age = Integer.parseInt(ageField.getText().trim());
            String symptom = (String) symptomDropdown.getSelectedItem();

            if (symptom == null || symptom.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a symptom.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Ensure engine is loaded
            triageEngine.reloadRules();

            Patient patient = new Patient();
            patient.setAge(age);
            patient.addSymptom(symptom);

            TriageResult result = triageEngine.triage(patient);

            String resultText = String.format("Time: %s\nAge: %d\nSymptom: %s\nResult: %s (Priority %d)\n",
                    LocalDateTime.now(), age, symptom, result.getColor(), result.getPriority());

            resultArea.setText(resultText);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Age. Please enter a number.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Triage Failed: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void clearForm() {
        ageField.setText("");
        resultArea.setText("");
        if (symptomDropdown.getItemCount() > 0) {
            symptomDropdown.setSelectedIndex(0);
        }
    }

    private void saveOutput() {
        String content = resultArea.getText();
        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No result to save.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (FileWriter writer = new FileWriter("triage_output.txt", true)) {
            writer.write(content + "\n--------------------------\n");
            JOptionPane.showMessageDialog(this, "Output saved to triage_output.txt", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to save output: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
