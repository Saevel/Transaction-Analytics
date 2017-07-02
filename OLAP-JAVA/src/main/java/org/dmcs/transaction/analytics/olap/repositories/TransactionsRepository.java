package org.dmcs.transaction.analytics.classical.repositories;

import org.dmcs.transaction.analytics.classical.model.CashOperation;
import org.dmcs.transaction.analytics.classical.model.CashOperationType;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Zielony on 2016-08-10.
 */
@Repository
public interface TransactionsRepository extends CrudRepository<CashOperation, Long> {

    @Query("SELECT * FROM cash_operation WHERE kind = ?0 AND timestamp >= ?1 ALLOW FILTERING")
    Collection<CashOperation> findByKindAndTimestampAfter(@Param("kind") String kind, @Param("start") long start);

    @Query("SELECT * FROM cash_operation WHERE kind = ?0 AND timestamp <= ?1 ALLOW FILTERING")
    Collection<CashOperation> findByKindAndTimestampBefore(@Param("kind") String kind, @Param("end") long end);

    @Query("SELECT * FROM cash_operation WHERE kind = ?0 AND timestamp >= ?1 " +
            "AND timestamp <= ?2 ALLOW FILTERING")
    Collection<CashOperation> findByKindAndTimestampBetween(@Param("kind") String kind, @Param("start") long start,
                                                        @Param("end") long end);

    @Query("SELECT * FROM cash_operation WHERE kind = ?0")
    Collection<CashOperation> findByKind(@Param("kind") String kind);
}