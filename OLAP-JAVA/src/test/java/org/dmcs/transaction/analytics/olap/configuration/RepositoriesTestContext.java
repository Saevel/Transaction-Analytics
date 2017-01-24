package org.dmcs.transaction.analytics.olap.configuration;

import com.sun.media.jfxmediaimpl.MediaDisposer;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Zielony on 2016-10-20.
 */
@Configuration
public class RepositoriesTestContext extends RepositoriesContext implements InitializingBean, DisposableBean {

    @Value("${cassandra.timeout.ms}")
    private Long cassandraTimeout;

    @Override
    public void destroy() throws Exception {
        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
        EmbeddedCassandraServerHelper.stopEmbeddedCassandra();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        EmbeddedCassandraServerHelper.startEmbeddedCassandra(cassandraTimeout);
    }
}