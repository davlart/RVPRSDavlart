package PO52.davletkaliyev.wdad.utils.date;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ArthurArt on 06.11.2017.
 */
public class Building implements Serializable {
    private  String street;
    private int number;
    private List<Flat> flatList;

    public List<Flat> getFlatList() {
        return flatList;
    }

    public void setFlatList(List<Flat> flatList) {
        this.flatList = flatList;
    }

    public Building(String street, int number) {
        this.street = street;
        this.number = number;
    }
    public Building() {
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(int number) {
        this.number = number;
    }



    public String getStreet() {
        return street;
    }

    public int getNumber() {
        return number;
    }
}
