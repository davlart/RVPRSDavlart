package PO52.davletkaliyev.wdad.data.managers;

import PO52.davletkaliyev.wdad.data.storage.DataSourceFactory;
import PO52.davletkaliyev.wdad.learn.xml.Housekeeper;
import PO52.davletkaliyev.wdad.learn.xml.Tariffs;
import PO52.davletkaliyev.wdad.utils.date.Building;
import PO52.davletkaliyev.wdad.utils.date.Flat;
import PO52.davletkaliyev.wdad.utils.date.Registration;

import javax.sql.DataSource;
import javax.xml.crypto.dsig.TransformException;
import java.sql.*;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class JDBCDataManager implements DataManager {

    private DataSource ds = null;
    private Connection con = null;
    private PreparedStatement  stmt = null;
    private ResultSet rs = null;

    public  JDBCDataManager() throws Exception {
        ds = DataSourceFactory.createDataSource();
    }

    public void openConnection() throws SQLException {
        try {
            this.con = ds.getConnection();
        }
        catch (Exception e){
            closeConnection();
        }

    }

    public void closeConnection() throws SQLException{
        try {
            this.con.close();
        }finally {
            this.con.close();
        }

    }

//    public void test() throws Exception {
//        Calendar calendar = new GregorianCalendar();
//        int numBuild = 43;
//        int numFlat = 64;
//        //todo: preparedStatement
//        String query = "select registrations_tariffs.amount from registrations" +
//                " JOIN (street, buildings, flats, registrations)" +
//                " ON (street.name = \"N.Panova\"" +
//                " AND street.id = buildings.street_id" +
//                " AND buildings.number = "+ numBuild +
//                " AND flats.number = " + numFlat +
//                " AND flats.buildings_id = buildings.id" +
//                " AND registrations.flats_id = flats.id" +
//                " AND registrations_tariffs.registrations_id = registrations.id)";
//
//        DataSource ds = null;
//        ds = DataSourceFactory.createDataSource();
//        //todo: Conection в отдельный метод.
//        Connection con = null;
//        Statement stmt = null;
//        ResultSet rs = null;
//        try {
//              con = ds.getConnection();
//              stmt = con.createStatement();
//              rs = stmt.executeQuery(query);
//            while (rs.next()) {
//                float amount = rs.getFloat(1);
//                System.out.println("amount: " + amount);
//            }
//
//        }
//        catch (Exception e){
//            System.out.println("Error");
//            e.getStackTrace();
//        }
//        finally {
//            con.close();
//        }
//    }

    public Tariffs getTariffs() throws SQLException {
        Tariffs tariffs = new Tariffs();
        String query =  "SELECT tarrifs.name, tarrifs.cost FROM tarrifs";
        String tarrifsName = "";
        Double tarrifsCost = 0.0;
        try {
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                tarrifsName = rs.getString(1);
                tarrifsCost = rs.getDouble(2);
                if(tarrifsName.equals("coldwater"))tariffs.coldwater = tarrifsCost;
                if(tarrifsName.equals("hotwater"))tariffs.hotwater = tarrifsCost;
                if(tarrifsName.equals("electricity"))tariffs.electricity = tarrifsCost;
                if(tarrifsName.equals("gas"))tariffs.gas = tarrifsCost;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            stmt.close();
            closeConnection();
        }
        return tariffs;
    }

    @Override
    public double getBill(Building building, int flatNumber) throws ParseException, RemoteException, SQLException {
        Flat flat = getFlat(building,flatNumber);
        Housekeeper housekeeper = new Housekeeper();
        housekeeper.setTariffs(getTariffs());
        return housekeeper.getBill(flat);
    }

    public Date getDataRegistration(int registrations_id)throws SQLException{
        String query = "select registrations.date from registrations where registrations.id = "+registrations_id+";";
        Statement stmt = con.createStatement();;
        try{

            ResultSet rs = stmt.executeQuery(query);
            if(rs!=null) {
                rs.next();
                return rs.getDate(1);
            }
        }catch (Exception e){
            closeConnection();
        }finally {
            stmt.close();
        }

        return null;
    }

    @Override
    public Flat getFlat(Building building, int flatNumber) throws ParseException, RemoteException, SQLException {
        Calendar calendar = new GregorianCalendar();
        Flat resultFlat = new Flat();
        ArrayList<Registration> registrationsList = new ArrayList<>();
        Registration registration = null;
        resultFlat.setNumber(flatNumber);
        int buf_registration_id = 0;
        StringBuilder querySB = new StringBuilder();
        querySB.append("select registrations_tariffs.registrations_id, registrations_tariffs.amount, tarrifs.name from registrations_tariffs")
                .append(" JOIN (street, buildings, flats, registrations, tarrifs)")
                .append(" ON (street.name = ?")
                .append(" AND street.id = buildings.street_id")
                .append(" AND buildings.number = ?")
                .append(" AND flats.number = ?")
                .append(" AND flats.buildings_id = buildings.id")
                .append(" AND registrations.flats_id = flats.id" )
                .append(" AND registrations_tariffs.registrations_id = registrations.id")
                .append(" AND registrations_tariffs.tariffs_name = tarrifs.name)")
                .append(" ORDER BY registrations_tariffs.registrations_id");

//        String query = "select registrations_tariffs.registrations_id, registrations_tariffs.amount, tarrifs.name from registrations_tariffs" +
//                " JOIN (street, buildings, flats, registrations, tarrifs)" +
//                " ON (street.name = \""+building.getStreet() +"\"" +
//                " AND street.id = buildings.street_id" +
//                " AND buildings.number = "+ building.getNumber() +
//                " AND flats.number = " + flatNumber +
//                " AND flats.buildings_id = buildings.id" +
//                " AND registrations.flats_id = flats.id"  +
//                " AND registrations_tariffs.registrations_id = registrations.id" +
//                " AND registrations_tariffs.tariffs_name = tarrifs.name)" +
//                " ORDER BY registrations_tariffs.registrations_id";

        try {
            String query = querySB.toString();
            stmt = con.prepareStatement(querySB.toString());
            stmt.setString(1,building.getStreet());
            stmt.setInt(2,building.getNumber());
            stmt.setInt(3,flatNumber);

            rs = stmt.executeQuery();

            while (rs.next()) {
                int registrations_id = rs.getInt(1);
                double amount = rs.getFloat(2);
                String tarifsName = rs.getString(3);
                //Проверяю сменился ли ID, если да, то создаю новую регистрацию
                if(buf_registration_id != registrations_id){
                    registration = new Registration();
                    registrationsList.add(registration);
                    registration.setDate(getDataRegistration(registrations_id));
                    buf_registration_id = registrations_id;
                }
                if(tarifsName.equals("coldwater")) registration.setColdwater(amount);
                if(tarifsName.equals("hotwater")) registration.setHotwater(amount);
                if(tarifsName.equals("electricity")) registration.setElectricity(amount);
                if(tarifsName.equals("gas")) registration.setGas(amount);
                //System.out.println("reg_id: "+registrations_id+" amount: " + amount + " tarifsName: " + tarifsName);
            }
            if(registrationsList.isEmpty()) return null;
            resultFlat.setRegistrations(registrationsList);
            return resultFlat;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            closeConnection();
        }
        finally {
            stmt.close();
        }
        return null;
    }

    @Override
    public void setTariff(String tariffName, double newValue) throws TransformException, RemoteException, SQLException {
        //String query = "UPDATE tarrifs SET tarrifs.cost = " + newValue + " WHERE tarrifs.name = "+"\""+tariffName+"\";";
        String query = "UPDATE tarrifs SET cost = ? WHERE name = ?";
        try {
            stmt = con.prepareStatement(query);
            stmt.setDouble(1,newValue);
            stmt.setString(2,tariffName);
            stmt.executeUpdate();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            closeConnection();
            //e.getStackTrace();
        }
        finally {
            stmt.close();
        }
    }

    private void addRegistrationTariffs(double amount, int registrations_id, String tarriffsName) throws SQLException {
//        String query = "INSERT INTO housekeeper.registrations_tariffs ( amount, registrations_id, tariffs_name) \n" +
//            " VALUES ("+ amount +", " +
//                    " " + registrations_id+", " +
//                    "\'"+ tarriffsName +"\');";
        StringBuilder querySB = new StringBuilder("INSERT INTO housekeeper.registrations_tariffs ( amount, registrations_id, tariffs_name)  VALUES  ( ?,?,?);\n");

            try {
            String query = querySB.toString();
            stmt = con.prepareStatement(querySB.toString());
            stmt.setDouble(1,amount);
            stmt.setInt(2,registrations_id);
            stmt.setString(3,tarriffsName);
            stmt.executeUpdate();
        }catch (Exception e){
            closeConnection();
            stmt.close();
        }

    }

    @Override
    public void addRegistration(Building building, int flatNumber, Registration registration) throws TransformException, RemoteException, SQLException {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(registration.getDate());
        StringBuilder querySB = new StringBuilder();
        querySB.append("SELECT flats.id from flats JOIN (buildings,street) ON ( street.name = ?")
                .append(" AND buildings.street_id = street.id AND buildings.number = ?")
                .append(" AND flats.buildings_id = buildings.id AND flats.number = ?)");
//        String query = "SELECT flats.id " +
//                "from flats " +
//                "JOIN (buildings,street) " +
//                "ON ( street.name = \"" + building.getStreet() +"\""+
//                " AND buildings.street_id = street.id " +
//                " AND buildings.number = " + building.getNumber() +
//                " AND flats.buildings_id = buildings.id " +
//                " AND flats.number = " + flatNumber +
//                " )";
        try {
            stmt = con.prepareStatement(querySB.toString());
            stmt.setString(1,building.getStreet());
            stmt.setInt(2,building.getNumber());
            stmt.setInt(3,flatNumber);
            rs = stmt.executeQuery();

            rs.next();
            int id_flat = rs.getInt(1);

            String queryGetMaxId = "Select MAX(id) from registrations;";
            stmt = con.prepareStatement(queryGetMaxId);
            rs = stmt.executeQuery();

            rs.next();
            int newMaxIdRegistration = rs.getInt(1)+1;

            querySB = new StringBuilder();
            querySB.append("INSERT INTO housekeeper.registrations (id, date, flats_id)  VALUES (?,?,?);");

//            query = "INSERT INTO housekeeper.registrations (id, date, flats_id) \n" +
//                    " VALUES ("+ newMaxIdRegistration +", " +
//                    "\'"+calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH) + 1)+"-01\' " + ", " +
//                     id_flat+ ");";
            stmt = con.prepareStatement(querySB.toString());
            stmt.setInt(1,newMaxIdRegistration);
            stmt.setString(2,+calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH) + 1)+"-01");
            stmt.setInt(3,id_flat);
            stmt.executeUpdate();

            if(registration.getColdwater() != 0.0) addRegistrationTariffs(registration.getColdwater(),
                    newMaxIdRegistration,"coldwater");
            if(registration.getHotwater() != 0.0) addRegistrationTariffs(registration.getHotwater(),
                    newMaxIdRegistration,"hotwater");
            if(registration.getElectricity()!= 0.0) addRegistrationTariffs(registration.getElectricity(),
                    newMaxIdRegistration,"electricity");
            if(registration.getGas()!= 0.0) addRegistrationTariffs(registration.getGas(),
                    newMaxIdRegistration,"gas");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            closeConnection();
            //e.getStackTrace();
        }
        finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                closeConnection();
                e.printStackTrace();
            }
        }
    }
}
