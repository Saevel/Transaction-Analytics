package org.dmcs.transaction.analytics.classical.services;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * Created by Zielony on 2016-08-10.
 */
public interface TransactionsService {

    Double capitalVarianceInPeriod(Optional<Timestamp> minDate, Optional<Timestamp> maxDate);

    Double averageInsertionInPeriod(Optional<Timestamp> minDate, Optional<Timestamp> maxDate);

    Double averageWithdrawalInPeriod(Optional<Timestamp> minDate, Optional<Timestamp> maxDate);
}
