package org.dmcs.transaction.analytics.olap.repositories;

import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.dmcs.transaction.analytics.olap.configuration.RepositoriesTestContext;
import org.dmcs.transaction.analytics.olap.model.UserAccount;
import org.dmcs.transaction.analytics.olap.configuration.RepositoriesContext;
import org.dmcs.transaction.analytics.olap.test.Unit;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Zielony on 2016-10-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoriesTestContext.class)
public class AccountsRepositoryIntegrationTest {

    private List<UserAccount> accounts = Arrays.asList(
        new UserAccount(1L, Optional.<Integer>empty(), Optional.of("Poland"), 1000.00, 1L),
        new UserAccount(2L, Optional.of(30), Optional.of("Poland"), 1300.00, 2L),
        new UserAccount(3L, Optional.<Integer>empty(), Optional.<String>empty(), 1700.00, 3L),
        new UserAccount(4L, Optional.of(20), Optional.of("France"), 700.00, 4L),
        new UserAccount(5L, Optional.of(40), Optional.of("France"), 300.00, 5L)
    );

    @Autowired
    private AccountsRepository accountsRepository;

    @Test
    public void shouldCountByCountry() throws Exception {withAccounts(() -> {
        long count = accountsRepository.countByCountry("Poland");
        assertEquals("Two entries with country: Poland", 2, count);
    });}

    @Test
    public void shouldSelectByCountry(){withAccounts(() -> {
        Collection<UserAccount> accounts = accountsRepository.findByCountry("France");
        assertNotNull("There should be accounts with country: France", accounts);
        assertEquals("Two entries with country: France", 2, accounts.size());
    });}

    @Test
    public void shouldSelectByCountryAndMinAge(){ withAccounts(() -> {
        Collection<UserAccount> accounts = accountsRepository.findByCountryAndMinAge("France", 30);
        assertNotNull("There should be accounts with country: France and age above 30", accounts);
        assertEquals("One account with country: France and age above: 30", 1, accounts.size());
    });}

    @Test
    public void shouldSelectByCountryAndMaxAge(){ withAccounts(() -> {
        Collection<UserAccount> accounts = accountsRepository.findByCountryAndMaxAge("France", 100);
        assertNotNull("There should be accounts with country: France and age below 100", accounts);
        assertEquals("Two accounts with country: France and age below 100", 2, accounts.size());
    });}

    @Test
    public void shouldSelectByCountryAndAgeInterval(){ withAccounts(() -> {
       Collection<UserAccount> accounts = accountsRepository.findByCountryAndAgeInterval("France", 10, 30);
       assertNotNull("There should be accounts with country: France and age between 10 and 30", accounts);
       assertEquals("One account with country: France and age between 10 and 30", 1, accounts.size());
    });}

    private void withAccounts(Unit f) {
        accountsRepository.save(accounts);
        f.apply();
        accountsRepository.deleteAll();
    }
}