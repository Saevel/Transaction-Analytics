package org.dmcs.transaction.analytics.classical;

import org.dmcs.transaction.analytics.classical.configuration.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Zielony on 2016-08-10.
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationContext.class, args);
    }
}