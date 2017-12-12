package PO52.davletkaliyev.wdad.data.managers.dao;

public class Tariff {
    private String name;
    private double cost;

    public Tariff() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String toString(){
        return name + " " + cost;
    }
}
