package org.dmcs.transaction.analytics.olap.services;

import org.dmcs.transaction.analytics.olap.model.CashOperation;
import org.dmcs.transaction.analytics.olap.model.CashOperationType;
import org.dmcs.transaction.analytics.olap.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Zielony on 2016-08-10.
 */
@Service
public class DefaultTransactionsService implements TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Override
    public Double capitalVarianceInPeriod(Optional<Timestamp> minDate, Optional<Timestamp> maxDate) {
        double totalInserted = operationsInPeriod(CashOperationType.Insertion, minDate, maxDate)
                .mapToDouble((insertion) -> insertion.getAmount()).sum();

        double totalWithdrawn = operationsInPeriod(CashOperationType.Withdrawal, minDate, maxDate)
                .mapToDouble((insertion) -> insertion.getAmount()).sum();

        return (totalInserted - totalWithdrawn);
    }

    @Override
    public Double averageInsertionInPeriod(Optional<Timestamp> minDate, Optional<Timestamp> maxDate) {
        return operationsInPeriod(CashOperationType.Insertion, minDate, maxDate)
                .mapToDouble((insertion) -> insertion.getAmount())
                .average()
                .orElse(0.0);
    }

    @Override
    public Double averageWithdrawalInPeriod(Optional<Timestamp> minDate, Optional<Timestamp> maxDate) {
        return operationsInPeriod(CashOperationType.Withdrawal, minDate, maxDate)
                .mapToDouble((insertion) -> insertion.getAmount())
                .average()
                .orElse(0.0);
    }

    private Stream<CashOperation> operationsInPeriod(CashOperationType kind, Optional<Timestamp> minDate, Optional<Timestamp> maxDate) {
        if(minDate.isPresent() && maxDate.isPresent()){
            return transactionsRepository.findByKindAndTimestampBetween(kind.name(),
                    minDate.get().getTime(), maxDate.get().getTime()).stream();
        }
        else if(minDate.isPresent()){
            return transactionsRepository.findByKindAndTimestampAfter(kind.name(), minDate.get().getTime()).stream();
        }
        else if(maxDate.isPresent()){
            return transactionsRepository.findByKindAndTimestampBefore(kind.name(), maxDate.get().getTime()).stream();
        }
        else {
            return transactionsRepository.findByKind(kind.name()).stream();
        }
    }
}