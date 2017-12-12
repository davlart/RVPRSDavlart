package PO52.davletkaliyev.wdad.data.managers.dao;


import java.util.HashMap;
import java.util.Date;

public class Registration {
    private int id;
    private Date date;
    private Flat flat;

    public Flat getFlat() {
        return flat;
    }

    public void setFlat(Flat flat) {
        this.flat = flat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public HashMap<Tariff, Double> getAmounts() {
        return amounts;
    }

    public void setAmounts(HashMap<Tariff, Double> amounts) {
        this.amounts = amounts;
    }

    private HashMap<Tariff, Double> amounts;

    public Registration() {
        this.id = -1;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "id=" + id +
                ", date=" + date +
                ", flat=" + flat.getId() +
                ", amounts=" + amounts +
                '}';
    }
}
