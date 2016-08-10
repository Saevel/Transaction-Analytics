package org.dmcs.transaction.analytics.olap.repositories;

import org.dmcs.transaction.analyst.lambda.model.CashOperation;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Zielony on 2016-08-10.
 */
@Repository
public interface TransactionsRepository extends CassandraRepository<CashOperation>{

    //TODO: CQL

    List<CashOperation> findByKindAndStartDate(String kind, LocalDateTime start);

    List<CashOperation> findByKindAndEndDate(String kind, LocalDateTime end);

    List<CashOperation> findByKindAndInPeriod(String kind, LocalDateTime start, LocalDateTime end);

    List<CashOperation> findByKind(String kind);
}
