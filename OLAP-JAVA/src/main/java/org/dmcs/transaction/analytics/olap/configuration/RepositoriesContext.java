package org.dmcs.transaction.analytics.classical.configuration;

import com.datastax.driver.core.Cluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cassandra.core.keyspace.CreateKeyspaceSpecification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Zielony on 2016-10-16.
 */
@Configuration
@PropertySource("cassandra.properties")
@ComponentScan("org.dmcs.transaction.analytics.olap.repositories")
@EnableCassandraRepositories(basePackages = "org.dmcs.transaction.analytics.olap.repositories")
public class RepositoriesContext extends AbstractCassandraConfiguration {

    @Value("${cassandra.keyspace}")
    private String cassandraKeyspace;

    @Value("${cassandra.contactpoints}")
    private String cassandraContactPoints;

    @Value("${cassandra.port}")
    private int cassandraPort;

    @Value("${cassandra.schema.action}")
    private String cassandraSchemaAction;

    @Override
    protected String getKeyspaceName() {
        return cassandraKeyspace;
    }

    @Override
    @Bean
    public SchemaAction getSchemaAction(){
        return SchemaAction.valueOf(cassandraSchemaAction);
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"org.dmcs.transaction.analytics.olap.model"};
    }

    @Bean
    @Override
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();

        cluster.setKeyspaceCreations(Arrays.asList(
                new CreateKeyspaceSpecification()
                        .name(cassandraKeyspace)
                        .ifNotExists()
        ));

        cluster.setContactPoints(cassandraContactPoints);
        cluster.setPort(cassandraPort);


        List<String> scriptsFromFile = new ArrayList<String>();
        try {
            scriptsFromFile = new BufferedReader(new InputStreamReader(
                    new ClassPathResource("initialize.cql").getInputStream())).lines().collect(Collectors.toList());

            cluster.setStartupScripts(scriptsFromFile);
        } catch(IOException e) {
            e.printStackTrace();
        }
            /*
            List<String> scripts = Arrays.asList(
                    "USE transaction_analytics_integration_tests;",
                    "CREATE TABLE IF NOT EXISTS user_account(user_id BIGINT " +
                    "PRIMARY KEY, account_id BIGINT, country TEXT, balance DOUBLE, age INT);",
                    "CREATE INDEX IF NOT EXISTS user_account_country ON user_account(country);"
            );
            */

        return cluster;
    }

    /*
    @Bean
    public CassandraMappingContext cassandraMapping()
            throws ClassNotFoundException {
        return new BasicCassandraMappingContext();
    }
    */
}