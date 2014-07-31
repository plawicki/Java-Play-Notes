package models;

import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Constraints.Required
    @Constraints.MaxLength(100)
    String name;

    @Constraints.MaxLength(100)
    String surname;

    Date date;

    @Constraints.MaxLength(100)
    @Constraints.Email
    String email;

    @Constraints.Min(1)
    @Constraints.Max(4)
    int favDb;

    @Constraints.MaxLength(5000)
    String notes;

    public Notification() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFavDb() {
        return favDb;
    }

    public void setFavDb(int favDb) {
        this.favDb = favDb;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

