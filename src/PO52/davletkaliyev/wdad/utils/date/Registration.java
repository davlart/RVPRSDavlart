package PO52.davletkaliyev.wdad.utils.date;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ArthurArt on 06.11.2017.
 */
public class Registration implements Serializable {
    private Date date;
    private double coldwater;
    private double hotwater;
    private double electricity;
    private double gas;

    public  Registration(){}

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

    public String toString(){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(this.date);
        return String.format("Месяц: %d, Год: %d, холодная вода: %f, горячая вода: %f, электричество: %f, газ: %f",
                (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.YEAR),this.coldwater,this.hotwater,this.electricity,this.gas);
    }
}
