package PO52.davletkaliyev.wdad.learn.xml.rmi.Client;

import PO52.davletkaliyev.wdad.data.managers.PreferencesManager;
import PO52.davletkaliyev.wdad.data.managers.DataManager;
import PO52.davletkaliyev.wdad.utils.PreferencesConstantManager;
import PO52.davletkaliyev.wdad.utils.date.Building;
import PO52.davletkaliyev.wdad.utils.date.Registration;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;

/**
 * Created by ArthurArt on 06.11.2017.
 */
public class Client {

//    static final private String EXECUTOR_NAME = "mySuperPuperExecutor";

    public static void main(String[] args) throws Exception {
        PreferencesManager prefManager = PreferencesManager.getInstance();
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(Integer.parseInt(prefManager.getProperty(PreferencesConstantManager.REGISTRYPORT))); // получает ссылку на реестр


        } catch (RemoteException re) {
            System.err.println("cant locate registry");
            re.printStackTrace();
        }
        if (registry != null) {
            try {
                DataManager DataManager = (DataManager) registry.lookup(prefManager.getName("DataManager")); // получаем ссылку на удаленный объект
                Building bulding = new Building("N.Ponova", 64);
                int flatNumber = 43;
                System.out.println(DataManager.getBill(bulding, flatNumber)); //вызываем метод удаленного объекта

                DataManager.setTariff("coldwater",101);

                SimpleDateFormat format = new SimpleDateFormat();
                format.applyPattern("MM.yyyy");
                DataManager.addRegistration(new Building("N.Ponova",64),43,new Registration(format.parse(1+"."+2000),0,0,0,0));

              System.out.println(DataManager.getFlat(bulding,43).toString());
            } catch (NotBoundException nbe) {
                System.err.println("cant find object");
                nbe.printStackTrace();
            } catch (RemoteException re) {
                System.err.println("something unbelievable happens");
                re.printStackTrace();
            } catch (Exception e) {
                System.err.println("user input err");
                e.printStackTrace();
            }
        }
    }
}
