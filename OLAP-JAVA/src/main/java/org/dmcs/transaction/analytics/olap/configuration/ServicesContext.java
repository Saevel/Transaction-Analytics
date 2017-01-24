package org.dmcs.transaction.analytics.olap.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Zielony on 2016-10-16.
 */
@Configuration
@ComponentScan("org.dmcs.transaction.analytics.olap.services")
@Import(RepositoriesContext.class)
public class ServicesContext {
    ;
}
