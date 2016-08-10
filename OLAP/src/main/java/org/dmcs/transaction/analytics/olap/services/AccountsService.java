package org.dmcs.transaction.analytics.olap.services;

import java.util.Optional;

/**
 * Created by Zielony on 2016-08-10.
 */
public interface AccountsService {

    Long countAccountsByCountry(String country);

    Double averageAccountsBalanceByCountryAndAge(String country, Optional<Integer> minAge, Optional<Integer> maxAge);
}
