package org.dmcs.transaction.analytics.olap.repositories;

import org.dmcs.transaction.analyst.lambda.model.UserData;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Zielony on 2016-08-10.
 */
@Repository
public interface UsersRepository extends CassandraRepository<UserData> {
}
