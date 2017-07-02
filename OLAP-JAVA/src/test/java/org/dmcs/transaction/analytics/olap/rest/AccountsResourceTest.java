package org.dmcs.transaction.analytics.classical.rest;

import org.dmcs.transaction.analytics.classical.configuration.ApplicationContext;
import org.dmcs.transaction.analytics.classical.configuration.RepositoriesTestContext;
import org.dmcs.transaction.analytics.classical.configuration.ServicesContext;
import org.dmcs.transaction.analytics.classical.model.UserAccount;
import org.dmcs.transaction.analytics.classical.repositories.AccountsRepository;
import org.dmcs.transaction.analytics.classical.test.Unit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Zielony on 2016-10-24.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT ,
        classes = {RepositoriesTestContext.class, ServicesContext.class, ApplicationContext.class})
@RunWith(SpringRunner.class)
public class AccountsResourceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountsRepository accountsRepository;

    private List<UserAccount> accounts = Arrays.asList(
            new UserAccount(1L, Optional.<Integer>empty(), Optional.of("Poland"), 1000.00, 1L),
            new UserAccount(2L, Optional.of(30), Optional.of("Poland"), 1300.00, 2L),
            new UserAccount(3L, Optional.<Integer>empty(), Optional.<String>empty(), 1700.00, 3L),
            new UserAccount(4L, Optional.of(20), Optional.of("France"), 700.00, 4L),
            new UserAccount(5L, Optional.of(40), Optional.of("France"), 300.00, 5L)
    );

    @Test
    public void shouldCountByCountry(){ withAccounts(() -> {
        Long count = restTemplate.getForObject("/accounts/country/France/active", Long.class);
        assertNotNull("Null value was returned", count);
        assertEquals("The count was incorrect", 2L, count.longValue());
    });}

    @Test
    public void shouldCalculateAverageBalanceByCountry(){ withAccounts(() -> {
        Double average = restTemplate.getForObject("/accounts/country/Poland/balance", Double.class);
        assertNotNull("Null value was returned", average);
        assertEquals("The count was incorrect", 1150.0, average.doubleValue(), 0.01);
    });}

    //TODO: Should calculate average balance by country

    //TODO: Should calculate average balance by country and minimal age

    //TODO: Should calculate average balance by country and maximal age

    //TODO: Should calculate average balance by country and age interval

    private void withAccounts(Unit f){
        accountsRepository.save(accounts);
        f.apply();
        accountsRepository.deleteAll();
    }
}