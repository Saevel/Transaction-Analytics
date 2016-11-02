package org.dmcs.transaction.analytics.olap.model;

import com.datastax.driver.core.DataType;
import org.springframework.data.cassandra.mapping.*;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * Created by Zielony on 2016-10-16.
 */
@Table(value = "USER_ACCOUNT")
public class UserAccount {

    @PrimaryKey("USER_ID")
    @CassandraType(type = DataType.Name.BIGINT)
    private Long userId;

    @Column("ACCOUNT_ID")
    @CassandraType(type = DataType.Name.BIGINT)
    private long accountId;

    private double balance;

    @Indexed("USER_ACCOUNT_COUNTRY")
    private String country;

    @Indexed("USER_ACCOUNT_AGE")
    private Integer age;

    public UserAccount(){}

    public UserAccount(long userId, Integer age, String country, double balance, long accountId) {
        this.userId = userId;
        this.age = age;
        this.country = country;
        this.balance = balance;
        this.accountId = accountId;
    }

    public UserAccount(long userId, Optional<Integer> age, Optional<String> country, double balance, long accountId) {
        this.userId = userId;
        this.age = age.orElse(null);
        this.country = country.orElse(null);
        this.balance = balance;
        this.accountId = accountId;
    }

    public long getUserId() {
        return userId;
    }

    public Optional<Integer> getAge() {
        return Optional.ofNullable(age);
    }

    public double getBalance() {
        return balance;
    }

    public long getAccountId() {
        return accountId;
    }

    public Optional<String> getCountry() {
        return Optional.ofNullable(country);
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return userId == that.userId &&
                accountId == that.accountId &&
                Double.compare(that.balance, balance) == 0 &&
                Objects.equals(country, that.country) &&
                Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, accountId, balance, country, age);
    }

    @Override
    public String toString() {
        return "UserAccount(" +
                "userId=" + userId +
                ", accountId=" + accountId +
                ", balance=" + balance +
                ", country=" + country +
                ", age=" + age +
                ')';
    }
}