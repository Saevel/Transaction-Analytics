package org.dmcs.transaction.analytics.classical.services;

import org.dmcs.transaction.analytics.classical.model.UserAccount;
import org.dmcs.transaction.analytics.classical.repositories.AccountsRepository;
import org.dmcs.transaction.analytics.classical.utils.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

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
        Collection<UserAccount> accounts = null;

        if(minAge.isPresent() && maxAge.isPresent()){
            accounts = accountsRepository.findByCountryAndAgeInterval(country, minAge.get(), maxAge.get());
        }
        else if(minAge.isPresent()){
            accounts = accountsRepository.findByCountryAndMinAge(country, minAge.get());
        }
        else if(maxAge.isPresent()){
            accounts = accountsRepository.findByCountryAndMaxAge(country, maxAge.get());
        }
        else {
            accounts = accountsRepository.findByCountry(country);
        }

        Optional<Tuple<Integer, Double>> resultAndCount = accounts.stream().map((account) ->
           new Tuple<Integer, Double>(1, account.getBalance())
        ).reduce((Tuple<Integer, Double> firstRecord, Tuple<Integer, Double> secondRecord) ->
           new Tuple(firstRecord.getFirst() + secondRecord.getFirst(), firstRecord.getSecond() + secondRecord.getSecond())
        );

        return resultAndCount.map((pair) -> pair.getSecond() / pair.getFirst()).orElse(0.0);
    }
}