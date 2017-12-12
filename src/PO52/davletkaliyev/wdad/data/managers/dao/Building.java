package PO52.davletkaliyev.wdad.data.managers.dao;

import java.util.Set;

public class Building {
    private int id;
    private String streetName;
    private int number;
    private Street street;

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Set<Flat> getFlats() {
        return flats;
    }

    public void setFlats(Set<Flat> flats) {
        this.flats = flats;
    }

    private Set<Flat> flats;

    public Building(int id, String streetName, int number, Set<Flat> flats) {
        this.id = id;
        this.streetName = streetName;
        this.number = number;
        this.flats = flats;
    }
    public Building() {
        this.id = -1;
        this.number = -1;
    }
}
