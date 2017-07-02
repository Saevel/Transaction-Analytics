package org.dmcs.transaction.analytics.classical.repositories;

import org.dmcs.transaction.analytics.classical.configuration.RepositoriesTestContext;
import org.dmcs.transaction.analytics.classical.model.CashOperation;
import org.dmcs.transaction.analytics.classical.model.CashOperationType;
import org.dmcs.transaction.analytics.classical.test.Unit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Zielony on 2016-10-20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RepositoriesTestContext.class)
public class TransactionsRepositoryIntegrationTest {

    @Autowired
    private TransactionsRepository transactionsRepository;

    private LocalDateTime now = LocalDateTime.now();

    private String Withdrawal = CashOperationType.Withdrawal.name();
    private String Insertion = CashOperationType.Insertion.name();
    private String Transfer = CashOperationType.Transfer.name();

    private List<CashOperation> cashOperations = Arrays.asList(
            new CashOperation(Timestamp.valueOf(now.minusDays(10)), 130.00, Insertion, 1L, null),
            new CashOperation(Timestamp.valueOf(now.minusDays(7)), 300.00, Insertion, 2L, null),
            new CashOperation(Timestamp.valueOf(now.minusDays(5)), 50.00, Withdrawal, 1L, null),
            new CashOperation(Timestamp.valueOf(now.minusDays(3)), 80.00, Withdrawal, 1L, null),
            new CashOperation(Timestamp.valueOf(now.minusDays(1)), 50.00, Transfer, 2L, 1L)
    );

    @Test
    public void shouldFindAllTransactionsByKind(){ withTransactions(() -> {
        Collection<CashOperation> withdrawals = transactionsRepository.findByKind(Withdrawal);
        assertNotNull("There were no withdrawals", withdrawals);
        assertEquals("There was an incorrect number of withdrawals", 2, withdrawals.size());
        withdrawals.forEach((withdrawal) -> {
            assertEquals("The type of the retrieved operation was not: Withdrawal", CashOperationType.Withdrawal, withdrawal.getKind());
        });
    });}

    @Test
    public void shouldFindTransactionsByKindAndMinTimestamp(){ withTransactions(() -> {
        Collection<CashOperation> withdrawals = transactionsRepository.findByKindAndTimestampAfter(Withdrawal, Timestamp.valueOf(now.minusDays(4)).getTime());
        assertNotNull("There were no withdrawals", withdrawals);
        assertEquals("There was an incorrect number of withdrawals", 1, withdrawals.size());
        withdrawals.forEach((withdrawal) -> {
            assertEquals("The type of the retrieved operation was not: Withdrawal", CashOperationType.Withdrawal, withdrawal.getKind());
        });
    });}

    @Test
    public void shouldFindTransactionsByKindAndMaxTimestamp(){ withTransactions(() -> {
        Collection<CashOperation> insertions = transactionsRepository.findByKindAndTimestampBefore(Insertion, Timestamp.valueOf(now.minusDays(8)).getTime());
        assertNotNull("There were no insertions", insertions);
        assertEquals("There was an incorrect number of insertions", 1, insertions.size());
        insertions.forEach((insertion) -> {
            assertEquals("The type of the retrieved operation was not: Insertion", CashOperationType.Insertion, insertion.getKind());
        });
    });}

    @Test
    public void shouldFindTransactionsByKindAndTimestampRange(){ withTransactions(() -> {
        Collection<CashOperation> withdrawals = transactionsRepository.findByKindAndTimestampBetween(Withdrawal,
                Timestamp.valueOf(now.minusDays(6)).getTime(), Timestamp.valueOf(now.minusDays(4)).getTime());
        assertNotNull("There were no withdrawals", withdrawals);
        assertEquals("There was an incorrect number of withdrawals", 1, withdrawals.size());
        withdrawals.forEach((withdrawal) -> {
            assertEquals("The type of the retrieved operation was not: Insertion", CashOperationType.Withdrawal, withdrawal.getKind());
        });
    });}

    private void withTransactions(Unit f) {
        transactionsRepository.save(cashOperations);
        f.apply();
        transactionsRepository.deleteAll();
    }
}