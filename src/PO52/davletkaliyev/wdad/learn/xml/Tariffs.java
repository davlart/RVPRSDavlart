package PO52.davletkaliyev.wdad.learn.xml;

/**
 * Created by ArthurArt on 02.11.2017.
 */
public class Tariffs {
    public double coldwater;
    public double hotwater;
    public double electricity;
    public double gas;

    public Tariffs(double coldwater, double hotwater, double electricity, double gas) {
        this.coldwater = coldwater;
        this.hotwater = hotwater;
        this.electricity = electricity;
        this.gas = gas;
    }
}
