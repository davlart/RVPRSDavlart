package PO52.davletkaliyev.wdad.data.managers.dao;

public class RegistrationTariff {
    private int id;
    private double amount;
    private Tariff tariff;
    private Registration registration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public RegistrationTariff() {
        this.id = -1;
    }
}
