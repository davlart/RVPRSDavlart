package PO52.davletkaliyev.wdad.learn.xml.rmi.Client;

import PO52.davletkaliyev.wdad.utils.date.Building;
import PO52.davletkaliyev.wdad.utils.date.Registration;

import java.rmi.Remote;

/**
 * Created by ArthurArt on 06.11.2017.
 */
public interface  XmlDataManager extends Remote {

    public double getBill (Building building, int flatNumber);// – возвращающий cумму платежа за текущий месяц.
    public double getFlat (Building building, int flatNumber);// – возвращающий квартиру с указанным адресом.
    public void setTariff (String tariffName, double newValue);// – изменяющий стоимость заданной единицы показания счетчика
    // (ХВС, ГВС, электроэнергия, газ).
    public void addRegistration (Building building, int flatNumber, Registration registration);//
    // – добавляющий (или заменяющий) показания счетчиков к заданной квартире в заданный период.

}
