package PO52.davletkaliyev.wdad.data.storage;

import PO52.davletkaliyev.wdad.data.managers.PreferencesManager;
import PO52.davletkaliyev.wdad.utils.PreferencesConstantManager;
import com.mysql.fabric.jdbc.FabricMySQLDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import javax.xml.xpath.XPathExpressionException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSourceFactory {
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static javax.sql.DataSource createDataSource() throws Exception {
        PreferencesManager prefManager = PreferencesManager.getInstance();

        MysqlDataSource dataSource = null;
        try {
            dataSource = new MysqlDataSource();
            dataSource.setURL(prefManager.getDBUrl());
            dataSource.setUser(prefManager.getProperty(PreferencesConstantManager.USER));
            dataSource.setPassword(prefManager.getProperty(PreferencesConstantManager.PASS));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return dataSource;
    }

    public static javax.sql.DataSource createDataSource(String className, String
            driverType, String host, int port, String dbName, String user, String password) throws ClassNotFoundException, SQLException {
        //jdbc:mysql://localhost:3306/test
        MysqlDataSource dataSource = null;
        try {
            dataSource = new MysqlDataSource();
            dataSource.setURL(className+"://"+host+":"+port+"/"+dbName);
            dataSource.setUser(user);
            dataSource.setPassword(password);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return dataSource;

    }

}
