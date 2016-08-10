package org.dmcs.transaction.analytics.olap.services;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by Zielony on 2016-08-10.
 */
public interface TransactionsService {

    Double capitalVarianceInPeriod(Optional<LocalDateTime> minDate, Optional<LocalDateTime> maxDate);

    Double averageInsertionInPeriod(Optional<LocalDateTime> minDate, Optional<LocalDateTime> maxDate);

    Double averageWithdrawalInPeriod(Optional<LocalDateTime> minDate, Optional<LocalDateTime> maxDate);
}
