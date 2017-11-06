package PO52.davletkaliyev.wdad.learn.xml.rmi.Client;

import PO52.davletkaliyev.wdad.data.managers.PreferencesManager;
import PO52.davletkaliyev.wdad.utils.PreferencesConstantManager;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by ArthurArt on 06.11.2017.
 */
public class Client {
    public static void main(String[] args) throws Exception {
        PreferencesManager prefManager = PreferencesManager.getInstance();
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(Integer.parseInt(prefManager.getProperty(PreferencesConstantManager.REGISTRYPORT))); // получает ссылку на реестр
        } catch (RemoteException re) {
            System.err.println("cant locate registry");
            re.printStackTrace();
        }
    }
}
