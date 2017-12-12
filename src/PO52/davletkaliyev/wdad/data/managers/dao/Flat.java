package PO52.davletkaliyev.wdad.data.managers.dao;

import java.util.Set;

public class Flat {
    private int id;
    private int number;
    private Building building;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Set<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(Set<Registration> registrations) {
        this.registrations = registrations;
    }

    private Set<Registration> registrations;

    public Flat() {
        this.id = -1;
        this.number = -1;
    }

    @Override
    public String toString() {
        return "Flat{" +
                "id=" + id +
                ", number=" + number +
                ", building=" + building +
                ", registrations=" + registrations +
                '}';
    }
}
