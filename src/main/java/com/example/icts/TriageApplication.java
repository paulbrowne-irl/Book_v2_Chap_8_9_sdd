package com.example.icts;

import com.example.icts.gui.TriageGUI;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.*;

@SpringBootApplication
public class TriageApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(TriageApplication.class)
                .headless(false)
                .run(args);

        EventQueue.invokeLater(() -> {
            TriageGUI gui = context.getBean(TriageGUI.class);
            gui.setVisible(true);
        });
    }

}
