package PO52.davletkaliyev.wdad.utils.date;

/**
 * Created by ArthurArt on 06.11.2017.
 */
public class Building {
    String street;
    String number;

    public Building(String street, String number) {
        this.street = street;
        this.number = number;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(String number) {
        this.number = number;
    }



    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }
}
