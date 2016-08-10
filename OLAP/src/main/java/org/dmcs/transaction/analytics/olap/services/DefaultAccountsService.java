package org.dmcs.transaction.analytics.olap.services;

import org.dmcs.transaction.analytics.olap.repositories.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Zielony on 2016-08-10.
 */
@Service
public class DefaultAccountsService implements AccountsService {

    @Autowired
    private AccountsRepository accountsRepository;

    @Override
    public Long countAccountsByCountry(String country) {
        return accountsRepository.countByCountry(country);
    }

    @Override
    public Double averageAccountsBalanceByCountryAndAge(String country, Optional<Integer> minAge, Optional<Integer> maxAge) {
        return null;
    }
}
