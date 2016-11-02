package org.dmcs.transaction.analytics.olap.events;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by Zielony on 2016-10-16.
 */
public class PersonalData {

    private String name;

    private String surname;

    private Optional<Integer> age = Optional.empty();

    public PersonalData(String name, String surname, Optional<Integer> age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public PersonalData(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Optional<Integer> getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalData that = (PersonalData) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, age);
    }

    @Override
    public String toString() {
        return "PersonalData(" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ')';
    }
}

