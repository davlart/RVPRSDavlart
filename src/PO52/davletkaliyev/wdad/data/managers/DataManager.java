package PO52.davletkaliyev.wdad.data.managers;

import PO52.davletkaliyev.wdad.utils.date.Building;
import PO52.davletkaliyev.wdad.utils.date.Flat;
import PO52.davletkaliyev.wdad.utils.date.Registration;

import javax.xml.crypto.dsig.TransformException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by ArthurArt on 06.11.2017.
 */
public interface  DataManager extends Remote {

    public double getBill (Building building, int flatNumber) throws RemoteException, SQLException, ParseException;// – возвращающий cумму платежа за текущий месяц.
    public Flat getFlat (Building building, int flatNumber) throws ParseException, RemoteException, SQLException;// – возвращающий квартиру с указанным адресом.
    public void setTariff (String tariffName, double newValue) throws TransformException, RemoteException, SQLException;// – изменяющий стоимость заданной единицы показания счетчика
    // (ХВС, ГВС, электроэнергия, газ).
    public void addRegistration (Building building, int flatNumber, Registration registration) throws TransformException,RemoteException, SQLException;//
    // – добавляющий (или заменяющий) показания счетчиков к заданной квартире в заданный период.

}
