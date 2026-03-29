package com.sentimant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.AppShellConfigurator;

@SpringBootApplication
@StyleSheet("styles.css")
public class SentimantAnalysisApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(SentimantAnalysisApplication.class, args);
    }
}
