package org.dmcs.transaction.analytics.classical.model;

import com.datastax.driver.core.DataType;
import org.springframework.data.cassandra.mapping.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.UUID;

/**
 * Created by Zielony on 2016-10-16.
 */
@Table("CASH_OPERATION")
public class CashOperation {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    private UUID id = UUID.randomUUID();

    @Indexed("CASH_OPERATION_TIMESTAMP")
    @CassandraType(type = DataType.Name.BIGINT)
    private Long timestamp;

    @Column("SOURCE_ACCOUNT_ID")
    @CassandraType(type = DataType.Name.BIGINT)
    private Long sourceAccountId;

    @Column("TARGET_ACCOUNT_ID")
    @CassandraType(type = DataType.Name.BIGINT)
    private Long targetAccountId;

    @Indexed("CASH_OPERATION_KIND")
    private String kind;

    private double amount;

    public CashOperation(){}

    public CashOperation(Timestamp timestamp, double amount, String kind, Long targetAccountId,
                         Long sourceAccountId) {
        this.timestamp = timestamp.getTime();
        this.amount = amount;
        this.kind = kind;
        this.targetAccountId = targetAccountId;
        this.sourceAccountId = sourceAccountId;
    }

    public CashOperation(Timestamp timestamp, double amount, CashOperationType kind, Optional<Long> targetAccountId,
                         Optional<Long> sourceAccountId) {
        this.timestamp = timestamp.getTime();
        this.amount = amount;
        this.kind = kind.name();
        this.targetAccountId = targetAccountId.orElse(null);
        this.sourceAccountId = sourceAccountId.orElse(null);
    }

    public Timestamp getTimestamp() {
        return new Timestamp(timestamp);
    }

    public Optional<Long> getSourceAccountId() {
        return Optional.ofNullable(sourceAccountId);
    }

    public Optional<Long> getTargetAccountId() {
        return Optional.ofNullable(targetAccountId);
    }

    public CashOperationType getKind() {
        return CashOperationType.valueOf(kind);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setTargetAccountId(Long targetAccountId) {
        this.targetAccountId = targetAccountId;
    }

    public void setSourceAccountId(Long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashOperation that = (CashOperation) o;
        return sourceAccountId == that.sourceAccountId &&
                Double.compare(that.amount, amount) == 0 &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(targetAccountId, that.targetAccountId) &&
                kind == that.kind;
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, sourceAccountId, targetAccountId, kind, amount);
    }

    @Override
    public String toString() {
        return "CashOperation(" +
                "timestamp=" + timestamp +
                ", sourceAccountId=" + sourceAccountId +
                ", targetAccountId=" + targetAccountId +
                ", kind=" + kind +
                ", amount=" + amount +
                ')';
    }
}