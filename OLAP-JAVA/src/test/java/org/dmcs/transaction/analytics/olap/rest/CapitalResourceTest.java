package org.dmcs.transaction.analytics.olap.rest;

import org.dmcs.transaction.analytics.olap.configuration.ApplicationContext;
import org.dmcs.transaction.analytics.olap.configuration.RepositoriesTestContext;
import org.dmcs.transaction.analytics.olap.configuration.ServicesContext;
import org.dmcs.transaction.analytics.olap.model.CashOperation;
import org.dmcs.transaction.analytics.olap.model.CashOperationType;
import org.dmcs.transaction.analytics.olap.repositories.TransactionsRepository;
import org.dmcs.transaction.analytics.olap.test.Unit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.dmcs.transaction.analytics.olap.model.CashOperationType.Insertion;
import static org.dmcs.transaction.analytics.olap.model.CashOperationType.Transfer;
import static org.dmcs.transaction.analytics.olap.model.CashOperationType.Withdrawal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Zielony on 2016-10-22.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT ,
        classes = {RepositoriesTestContext.class, ServicesContext.class, ApplicationContext.class})
@RunWith(SpringRunner.class)
public class CapitalResourceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TransactionsRepository transactionsRepository;

    private LocalDateTime now = LocalDateTime.now();

    private List<CashOperation> transactions  = Arrays.asList(
      new CashOperation(Timestamp.valueOf(now.minusDays(5)), 100.0, Insertion, Optional.<Long>of(1L), Optional.<Long>empty()),
      new CashOperation(Timestamp.valueOf(now.minusDays(5)), 200.0, Insertion, Optional.<Long>of(2L), Optional.<Long>empty()),
      new CashOperation(Timestamp.valueOf(now.minusDays(4)), 50.0, Transfer, Optional.<Long>of(1L), Optional.<Long>of(2L)),
      new CashOperation(Timestamp.valueOf(now.minusDays(3)), 20.0, Withdrawal, Optional.<Long>of(1L), Optional.<Long>empty()),
      new CashOperation(Timestamp.valueOf(now.minusDays(3)), 40.0, Withdrawal, Optional.<Long>of(2L), Optional.<Long>empty()),
      new CashOperation(Timestamp.valueOf(now.minusDays(2)), 50.0, Insertion, Optional.<Long>of(1L), Optional.<Long>empty()),
      new CashOperation(Timestamp.valueOf(now.minusDays(2)), 30.0, Insertion, Optional.<Long>of(2L), Optional.<Long>empty()),
      new CashOperation(Timestamp.valueOf(now.minusDays(1)), 30.0, Transfer, Optional.<Long>of(2L), Optional.<Long>of(1L)),
      new CashOperation(Timestamp.valueOf(now.minusDays(0)), 10.0, Withdrawal, Optional.<Long>of(1L), Optional.<Long>empty()),
      new CashOperation(Timestamp.valueOf(now.minusDays(0)), 20.0, Withdrawal, Optional.<Long>of(2L), Optional.<Long>empty())
    );

    @Test
    public void shouldCalculateTotalCapitalVariance() { withTransactions(() -> {
        ResponseEntity<String> entity = restTemplate.getForEntity("/capital/variance", String.class);
        assertNotNull("Null value was returned", entity);
        assertEquals("The capital variance value is incorrect", 290.0, Double.parseDouble(entity.getBody()), 0.01);
    });}

    @Test
    public void shouldCalculateCapitalVarianceInPeriod() { withTransactions(() -> {
        HttpEntity<String> entity = restTemplate.getForEntity("/capital/variance?start={start}&end={end}", String.class,
                period(now, now.plusDays(3)));
        assertNotNull("Null value was returned", entity);
        assertEquals("The capital variance value was incorrect", -30.0, Double.parseDouble(entity.getBody()), 0.01);
    });}

    @Test
    public void shouldCalculateCapitalVarianceAfter() { withTransactions(() -> {
        Double variance = restTemplate.getForObject("/capital/variance?start={start}", Double.class, period(now.minusDays(2), null));
        assertNotNull("The result was null", variance);
        assertEquals("The variance value was incorrect", 50.0, variance, 0.01);
    });}

    @Test
    public void shouldCalculateCapitalVarianceBefore() { withTransactions(() -> {
        Double variance = restTemplate.getForObject("/capital/variance?end={end}", Double.class, period(null, now.minusDays(4)));
        assertNotNull("The result was null", variance);
        assertEquals("The variance value was incorrect", 300.0,  variance, 0.01);
    });}

    @Test
    public void shouldCalculateAverageInsertion() { withTransactions(() -> {
        Double average = restTemplate.getForObject("/capital/insertions/average", Double.class);
        assertNotNull("The result was null", average);
        assertEquals("The average insertion was incorrect", 95.0, average.doubleValue(), 0.01);
    });}

    @Test
    public void shouldCalculateAverageInsertionAfter(){ withTransactions(() -> {
        Double average = restTemplate.getForObject("/capital/insertions/average?start={start}", Double.class,
               period(now.minusDays(3), null));
        assertNotNull("The result was null", average);
        assertEquals("The average insertion was incorrect", 40.0, average, 0.01);
    });}

    @Test
    public void shouldCalculateAverageInsertionBefore(){ withTransactions(() -> {
        Double average = restTemplate.getForObject("/capital/insertions/average?end={end}", Double.class,
                period(null, now.minusDays(3)));
        assertNotNull("The result was null", average);
        assertEquals("The average insertion was incorrect", 150.0, average, 0.01);
    });}

    @Test
    public void shouldCalculateAverageInsertionInPeriod(){ withTransactions(() -> {
        Double average = restTemplate.getForObject("/capital/insertions/average?start={start}&end={end}", Double.class,
                period(now.minusDays(7), now.minusDays(3)));
        assertNotNull("The result was null", average);
        assertEquals("The average insertion was incorrect", 150.0, average, 0.01);
    });}

    //TODO: Average insertion in period

    //TODO: Average withdrawal

    //TODO: Average withdrawal from

    //TODO: Average withdrawal to

    //TODO: Average withdrawal in period

    private Map<String, LocalDateTime> period(LocalDateTime start, LocalDateTime end) {
        Map<String, LocalDateTime> period = new HashMap<String, LocalDateTime>();

        if(start != null) {period.put("start", start);}
        if(end != null) {period.put("end", end);}

        return period;
    }

    private void withTransactions(Unit f) {
        transactionsRepository.save(transactions);
        f.apply();
        transactionsRepository.deleteAll();
    }
}