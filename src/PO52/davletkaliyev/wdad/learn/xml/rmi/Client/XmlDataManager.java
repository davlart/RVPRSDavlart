package PO52.davletkaliyev.wdad.learn.xml.rmi.Client;

import PO52.davletkaliyev.wdad.utils.date.Building;
import PO52.davletkaliyev.wdad.utils.date.Flat;
import PO52.davletkaliyev.wdad.utils.date.Registration;

import javax.xml.crypto.dsig.TransformException;
import java.rmi.Remote;
import java.text.ParseException;

/**
 * Created by ArthurArt on 06.11.2017.
 */
public interface  XmlDataManager extends Remote {

    public double getBill (Building building, int flatNumber);// – возвращающий cумму платежа за текущий месяц.
    public Flat getFlat (Building building, int flatNumber) throws ParseException;// – возвращающий квартиру с указанным адресом.
    public void setTariff (String tariffName, double newValue) throws TransformException;// – изменяющий стоимость заданной единицы показания счетчика
    // (ХВС, ГВС, электроэнергия, газ).
    public void addRegistration (Building building, int flatNumber, Registration registration) throws TransformException;//
    // – добавляющий (или заменяющий) показания счетчиков к заданной квартире в заданный период.

}
