package PO52.davletkaliyev.wdad.utils.date;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ArthurArt on 06.11.2017.
 */
public class Flat implements Serializable {
    private int number;
    private int personsQuantity;
    private double area;
    private List<Registration> registrations;

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

    public String toString(){
        StringBuilder result = new StringBuilder();

        result.append(String.format("Квартира: %d, Площадь: %f, Кол-во людей: %d %n",this.number,this.area,this.personsQuantity));
        for (Registration reg: this.registrations) {
        result.append(reg.toString()+"\n");
        }
        return result.toString();
    }
}
