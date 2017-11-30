package PO52.davletkaliyev.wdad.utils;

/**
 * Created by ArthurArt on 06.11.2017.
 */
public interface PreferencesConstantManager {
        //rmi
        String CREATEREGISTRY="appconfig.rmi.server.registry.createregistry";
        String REGISTRYADDRESS="appconfig.rmi.server.registry.registryaddress";
        String REGISTRYPORT="appconfig.rmi.server.registry.registryport";
        String POLICYPATH="appconfig.rmi.client.policypath";
        String USECODEBASEONLY="appconfig.rmi.client.usecodebaseonly";
        String CLASSPROVIDER="appconfig.rmi.classprovider";
        //JDBC
        String CLASSNAME = "appconfig.datasource.classname";
        String DRIVERTYPE = "appconfig.datasource.drivertype";
        String HOSTNAME = "appconfig.datasource.hostName";
        String PORT = "appconfig.datasource.port";
        String DBNAME = "appconfig.datasource.DBName";
        String USER = "appconfig.datasource.user";
        String PASS = "appconfig.datasource.pass";
}