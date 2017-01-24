package org.dmcs.transaction.analytics.olap.repositories;

import org.dmcs.transaction.analytics.olap.model.UserData;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Created by Zielony on 2016-08-10.
 */
@Repository
public interface UsersRepository extends CrudRepository<UserData, Long> {
    @Query("SELECT * FROM user_data")
    Collection<UserData> findAllAsCollection();
}
