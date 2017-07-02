package org.dmcs.transaction.analytics.classical.services;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * Created by Zielony on 2016-08-10.
 */
public interface AccountsService {

    Long countAccountsByCountry(String country);

    Double averageAccountsBalanceByCountryAndAge(String country, Optional<Integer> minAge, Optional<Integer> maxAge);
}
