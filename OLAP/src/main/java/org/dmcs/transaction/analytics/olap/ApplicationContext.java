package org.dmcs.transaction.analytics.olap;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Zielony on 2016-08-10.
 */
@Configuration
@ComponentScan("org.dmcs.transaction.analytics.olap")
public class ApplicationContext {
    ;

    //TODO: Entity scan?
    //TODO: Setup Spring Data Cassandra
}
