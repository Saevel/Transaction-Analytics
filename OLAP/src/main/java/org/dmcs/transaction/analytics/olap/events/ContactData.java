package org.dmcs.transaction.analytics.olap.events;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by Zielony on 2016-10-16.
 */
public class ContactData {

    private Optional<String> phone = Optional.empty();

    private Optional<String> email = Optional.empty();

    private Optional<String> address = Optional.empty();

    private Optional<String> country = Optional.empty();

    public ContactData(Optional<String> phone, Optional<String> email, Optional<String> address, Optional<String> country) {
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.country = country;
    }

    public ContactData(Optional<String> phone, Optional<String> email, Optional<String> address) {
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public ContactData(Optional<String> phone, Optional<String> email) {
        this.phone = phone;
        this.email = email;
    }

    public ContactData(Optional<String> phone) {
        this.phone = phone;
    }

    public ContactData() {}

    public Optional<String> getPhone() {
        return phone;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public Optional<String> getAddress() {
        return address;
    }

    public Optional<String> getCountry() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return Objects.equals(phone, that.phone) &&
                Objects.equals(email, that.email) &&
                Objects.equals(address, that.address) &&
                Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, email, address, country);
    }

    @Override
    public String toString() {
        return "ContactData(" +
                "phone=" + phone +
                ", email=" + email +
                ", address=" + address +
                ", country=" + country +
                ')';
    }
}
