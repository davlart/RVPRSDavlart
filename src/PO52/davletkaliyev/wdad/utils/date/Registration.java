package PO52.davletkaliyev.wdad.utils.date;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ArthurArt on 06.11.2017.
 */
public class Registration implements Serializable {
    Date date;
    double coldwater;
    double hotwater;
    double electricity;
    double gas;

    public  Registration(){

    }

    public Registration(Date date, double coldwater, double hotwater, double electricity, double gas) {
        this.date = date;
        this.coldwater = coldwater;
        this.hotwater = hotwater;
        this.electricity = electricity;
        this.gas = gas;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getColdwater() {
        return coldwater;
    }

    public void setColdwater(double coldwater) {
        this.coldwater = coldwater;
    }

    public double getHotwater() {
        return hotwater;
    }

    public void setHotwater(double hotwater) {
        this.hotwater = hotwater;
    }

    public double getElectricity() {
        return electricity;
    }

    public void setElectricity(double electricity) {
        this.electricity = electricity;
    }

    public double getGas() {
        return gas;
    }

    public void setGas(double gas) {
        this.gas = gas;
    }
}
