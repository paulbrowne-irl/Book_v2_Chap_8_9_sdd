package com.example.icts.controller;

import com.example.icts.model.Patient;
import com.example.icts.model.TriageResult;
import com.example.icts.service.TriageEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/triage")
public class TriageController {

    private final TriageEngine triageEngine;

    @Autowired
    public TriageController(TriageEngine triageEngine) {
        this.triageEngine = triageEngine;
    }

    @PostMapping
    public TriageResult triagePatient(@RequestBody Patient patient) {
        return triageEngine.triage(patient);
    }
}
