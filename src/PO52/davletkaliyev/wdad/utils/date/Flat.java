package PO52.davletkaliyev.wdad.utils.date;

import java.util.List;

/**
 * Created by ArthurArt on 06.11.2017.
 */
public class Flat {
    int number;
    int personsQuantity;
    double area;
    List<Registration> registrations;

    public Flat(){

    }

    public Flat(int number, int personsQuantity, double area, List<Registration> registrations) {
        this.number = number;
        this.personsQuantity = personsQuantity;
        this.area = area;
        this.registrations = registrations;
    }

    public void setNumber(int number) {

        this.number = number;
    }

    public void setPersonsQuantity(int personsQuantity) {
        this.personsQuantity = personsQuantity;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
    }

    public int getNumber() {

        return number;
    }

    public int getPersonsQuantity() {
        return personsQuantity;
    }

    public double getArea() {
        return area;
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }
}
