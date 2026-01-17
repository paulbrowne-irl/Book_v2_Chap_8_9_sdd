package com.example.icts.service;

import com.example.icts.model.Patient;
import com.example.icts.model.TriageResult;
import org.drools.compiler.kie.builder.impl.KieFileSystemImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.Message;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collection;
import java.util.Comparator;

@Service
public class TriageEngine {

    private KieContainer kieContainer;
    private static final String DRL_PATH = "generated_rules/triage.drl";

    public void reloadRules() {
        File drlFile = new File(DRL_PATH);
        if (!drlFile.exists()) {
            System.out.println("No rules file found at " + DRL_PATH + ". Triage will default to Blue.");
            this.kieContainer = null;
            return;
        }

        KieServices ks = KieServices.Factory.get();
        KieFileSystem kfs = ks.newKieFileSystem();
        kfs.write(ks.getResources().newFileSystemResource(drlFile));

        KieBuilder kb = ks.newKieBuilder(kfs);
        kb.buildAll();

        if (kb.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("Build Errors:\n" + kb.getResults().toString());
        }

        KieModule kieModule = kb.getKieModule();
        this.kieContainer = ks.newKieContainer(kieModule.getReleaseId());
        System.out.println("Rules loaded successfully from " + DRL_PATH);
    }

    public TriageResult triage(Patient patient) {
        if (kieContainer == null) {
            reloadRules(); // Try to load
            if (kieContainer == null) {
                return new TriageResult("Blue", 5); // Default if no rules
            }
        }

        KieSession kSession = kieContainer.newKieSession();
        try {
            kSession.insert(patient);
            kSession.fireAllRules();

            Collection<TriageResult> results = (Collection<TriageResult>) kSession
                    .getObjects(o -> o instanceof TriageResult);

            return results.stream()
                    .min(Comparator.comparingInt(TriageResult::getPriority))
                    .orElse(new TriageResult("Blue", 5));
        } finally {
            kSession.dispose();
        }
    }
}
