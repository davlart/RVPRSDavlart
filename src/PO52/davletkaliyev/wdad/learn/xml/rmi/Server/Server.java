package PO52.davletkaliyev.wdad.learn.xml.rmi.Server;

import PO52.davletkaliyev.wdad.data.managers.PreferencesManager;
import PO52.davletkaliyev.wdad.learn.xml.rmi.Client.XmlDataManager;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by ArthurArt on 06.11.2017.
 */
public class Server {
    /*путь к файлу с политиками безопасности. В примере указан относительный путь - относительно текущей директории.
        Чтобы файл был найден без проблем, он должен находиться рядом с файлом Server.class (именно *.class, а не *.java). Но можете использовать и абсолютный путь.
   */
    static final private String SECURITY_POLICY_PATH = "D:\\Work\\РВПРС\\Exmple2\\exm1\\src\\rmi\\security.policy";
    /*путь к *.class файлам, участвующим в RMI-взаимодействии. Отсюда, при необходимости, они будут подгружаться. Есть пара вариантов -
1) Загружаемся с локальной директории, тогда используется протокол file://, указывающий либо на файл с расширением *.jar, либо на директорию содержащую библиотеку *.class файлов.
 Если указываем директорию, то последний символ в пути должен быть '/' (либо "\\", если Windows)
2) Загружаемся по http - тогда URL должна указывать на *.jar файл. Например, http://mastefanov.com/test/codebase/base.jar
*/
    //static final private String CODEBASE_URL = "file:\\Work\\РВПРС\\laba1\\src\\PO52\\davletkaliyev\\wdad\\learn\\xml\\rmi\\security.policy";
    static final private int REGISTRY_PORT = 1099;
    static final private String EXECUTOR_NAME = "mySuperPuperExecutor";

    public static void main(String[] args) throws Exception {
        PreferencesManager prefManager = PreferencesManager.getInstance();

        System.out.println("preparing");
        // устанавливаем свойства, необходимые для работы.
       // System.setProperty("java.rmi.server.codebase", CODEBASE_URL); // адрес codebase
       // System.setProperty("java.rmi.server.useCodebaseOnly", "true"); // искать классы в codebase, а не в class path
        System.setProperty("java.rmi.server.logCalls", "true"); // чтоб сервер в консоль выводил инку по коннекта - запросы на поиск объекта
        System.setProperty("java.security.policy", SECURITY_POLICY_PATH); // путь к файлу с правами доступа
        System.setSecurityManager(new SecurityManager());
        System.out.println("prepared");
        Registry registry = null;
        try {
            XmlDataManagerImpl xdmi = new XmlDataManagerImpl(); // создаем удаленный объект

            //если реестр запущен
            registry = LocateRegistry.createRegistry(REGISTRY_PORT);
            // Remote stub =  UnicastRemoteObject.exportObject(xdmi, 0);
            registry.rebind(EXECUTOR_NAME, new XmlDataManagerImpl());
            prefManager.addBindedObject("XmlDataManager", XmlDataManager.class.getCanonicalName());
            //если Сервер должен создать реестр, то можно использовать следующую строку
            //registry = LocateRegistry.createRegistry(REGISTRY_PORT);
            //но в этом случае необходимо, чтобы в директории, из которой запускался Сервер были определения всех *.class файлов


        } catch (RemoteException re) {
            System.err.println("cant locate registry");
            re.printStackTrace();
        }
            System.out.println("Running");
            /*while (true) {
                Thread.sleep(Integer.MAX_VALUE);
            }*/

    }

}
