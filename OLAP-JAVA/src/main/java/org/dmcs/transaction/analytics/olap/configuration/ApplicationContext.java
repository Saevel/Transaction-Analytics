package org.dmcs.transaction.analytics.olap.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Zielony on 2016-08-10.
 */
@Configuration
@ComponentScan(value = "org.dmcs.transaction.analytics.olap.rest")
@EnableWebMvc
@EnableAutoConfiguration
@Import(ServicesContext.class)
public class ApplicationContext {
    ;
}