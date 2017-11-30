package PO52.davletkaliyev.wdad.learn.xml.rmi.Server;

import PO52.davletkaliyev.wdad.learn.xml.Housekeeper;
import PO52.davletkaliyev.wdad.data.managers.DataManager;
import PO52.davletkaliyev.wdad.utils.date.Building;
import PO52.davletkaliyev.wdad.utils.date.Flat;
import PO52.davletkaliyev.wdad.utils.date.Registration;

import javax.xml.crypto.dsig.TransformException;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;

/**
 * Created by ArthurArt on 06.11.2017.
 */
public class XmlDataManagerImpl extends UnicastRemoteObject implements DataManager {
    Housekeeper housekeeper;

    public XmlDataManagerImpl() throws RemoteException, ParseException {
        housekeeper = new Housekeeper("D:\\Work\\РВПРС\\laba1\\src\\PO52\\davletkaliyev\\wdad\\learn\\xml\\fersion2.xml");
    }

    protected XmlDataManagerImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    @Override
    public double getBill(Building building, int flatNumber) {
        return housekeeper.getBill(building,flatNumber);
    }

    @Override
    public Flat getFlat(Building building, int flatNumber) throws ParseException {
        return housekeeper.getFlat(building,flatNumber);
    }

    @Override
    public void setTariff(String tariffName, double newValue) throws TransformException {
        housekeeper.setTariff(tariffName,newValue);
    }

    @Override
    public void addRegistration(Building building, int flatNumber, Registration registration) throws TransformException {
        housekeeper.addRegistration(building, flatNumber, registration);
    }
}
