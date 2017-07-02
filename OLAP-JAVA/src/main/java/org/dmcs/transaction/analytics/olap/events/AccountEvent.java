package org.dmcs.transaction.analytics.classical.events;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by Zielony on 2016-10-16.
 */
public class AccountEvent extends Event {

    private long userId;

    private long accountId;

    private double balance;

    private AccountEventType kind;

    public AccountEvent(Timestamp timestamp, long userId, AccountEventType kind, double balance, long accountId) {
        super(timestamp);
        this.userId = userId;
        this.kind = kind;
        this.balance = balance;
        this.accountId = accountId;
    }

    public long getUserId() {
        return userId;
    }

    public long getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public AccountEventType getKind() {
        return kind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEvent that = (AccountEvent) o;
        return userId == that.userId &&
                accountId == that.accountId &&
                Double.compare(that.balance, balance) == 0 &&
                kind == that.kind;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, accountId, balance, kind);
    }

    @Override
    public String toString() {
        return "AccountEvent(" +
                "userId=" + userId +
                ", accountId=" + accountId +
                ", balance=" + balance +
                ", kind=" + kind +
                ')';
    }
}
