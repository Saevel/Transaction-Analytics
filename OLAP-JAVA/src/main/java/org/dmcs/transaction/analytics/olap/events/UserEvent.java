package org.dmcs.transaction.analytics.classical.events;

import java.sql.Timestamp;
import java.util.Objects;

public class UserEvent extends Event {

    private Long id;

    private String username;

    private String password;

    private PersonalData personalData;

    private ContactData contactData;

    private UserEventType kind;

    public UserEvent(Timestamp timestamp, Long id, UserEventType kind, ContactData contactData,
                     PersonalData personalData, String password, String username) {
        super(timestamp);
        this.id = id;
        this.kind = kind;
        this.contactData = contactData;
        this.personalData = personalData;
        this.password = password;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public ContactData getContactData() {
        return contactData;
    }

    public UserEventType getKind() {
        return kind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEvent userEvent = (UserEvent) o;
        return Objects.equals(id, userEvent.id) &&
                Objects.equals(username, userEvent.username) &&
                Objects.equals(password, userEvent.password) &&
                Objects.equals(personalData, userEvent.personalData) &&
                Objects.equals(contactData, userEvent.contactData) &&
                kind == userEvent.kind;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, personalData, contactData, kind);
    }

    @Override
    public String toString() {
        return "UserEvent(" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", personalData=" + personalData +
                ", contactData=" + contactData +
                ", kind=" + kind +
                ')';
    }
}