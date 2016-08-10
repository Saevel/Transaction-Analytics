package org.dmcs.transaction.analytics.olap.services;

import org.dmcs.transaction.analytics.olap.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by Zielony on 2016-08-10.
 */
@Service
public class DefaultTransactionsService implements TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Override
    public Double capitalVarianceInPeriod(Optional<LocalDateTime> minDate, Optional<LocalDateTime> maxDate) {
        return null;
    }

    @Override
    public Double averageInsertionInPeriod(Optional<LocalDateTime> minDate, Optional<LocalDateTime> maxDate) {
        return null;
    }

    @Override
    public Double averageWithdrawalInPeriod(Optional<LocalDateTime> minDate, Optional<LocalDateTime> maxDate) {
        return null;
    }
}
