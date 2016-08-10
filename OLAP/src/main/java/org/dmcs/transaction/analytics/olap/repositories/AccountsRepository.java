package org.dmcs.transaction.analytics.olap.repositories;

import org.dmcs.transaction.analyst.lambda.model.UserAccount;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Zielony on 2016-08-10.
 */
@Repository
public interface AccountsRepository extends CassandraRepository<UserAccount> {

    //TODO: CQL?

    Long countByCountry(String country);
}
