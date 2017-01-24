package org.dmcs.transaction.analytics.olap.model;

import com.datastax.driver.core.DataType;
import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Objects;

/**
 * Created by Zielony on 2016-10-16.
 */
@Table("USER_DATA")
public class UserData {

    @PrimaryKey("USER_ID")
    @CassandraType(type = DataType.Name.BIGINT)
    private long userId;

    private int age;

    public UserData(){}

    public UserData(long userId, int age) {
        this.userId = userId;
        this.age = age;
    }

    public long getUserId() {
        return userId;
    }

    public int getAge() {
        return age;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return userId == userData.userId &&
                age == userData.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, age);
    }

    @Override
    public String toString() {
        return "UserData(" +
                "userId=" + userId +
                ", age=" + age +
                ')';
    }
}