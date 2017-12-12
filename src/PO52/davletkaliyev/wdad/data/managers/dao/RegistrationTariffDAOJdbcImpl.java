package PO52.davletkaliyev.wdad.data.managers.dao;

import PO52.davletkaliyev.wdad.data.storage.DataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class RegistrationTariffDAOJdbcImpl extends AbstractDAO implements RegistrationTariffDAO {
    private DataSource ds = null;
    private Connection con = null;
    private ResultSet rs = null;

    public RegistrationTariffDAOJdbcImpl() throws Exception {
        super();
        this.ds = getDs();
        this.con = getCon();
    }

    @Override
    public boolean insertRegistrationTariff(RegistrationTariff registrationTariff) throws SQLException {
        PreparedStatement ps = null;
        try {
            String query = "INSERT INTO housekeeper.registrations_tariffs (id,amount,registration_id,tarrifs_name) VALUES (?,?,?,?);";
            ps = getPrepareStatement(query);
            ps.setInt(1, registrationTariff.getId());
            ps.setDouble(2,registrationTariff.getAmount());
            ps.setInt(3,registrationTariff.getRegistration().getId());
            ps.setString(4,registrationTariff.getTariff().getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            ps.close();
        }
        return true;
    }

    @Override
    public boolean deleteRegistrationTariff(RegistrationTariff registrationTariff) throws SQLException {
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM housekeeper.registrations_tariffs WHERE (id = ? AND amount = ? AND registration_id = ? AND tarrifs_name = ?)";
            ps = getPrepareStatement(query);
            ps.setInt(1, registrationTariff.getId());
            ps.setDouble(2,registrationTariff.getAmount());
            ps.setInt(3,registrationTariff.getRegistration().getId());
            ps.setString(4,registrationTariff.getTariff().getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            ps.close();
        }
        return true;
    }

    @Override
    public RegistrationTariff findRegistrationTariff(int id) throws SQLException {
        PreparedStatement ps = null;
        RegistrationTariff result = new RegistrationTariff();
        try {
            String query = "Select * FROM housekeeper.registrations_tariffs WHERE id = ?";
            ps = getPrepareStatement(query);
            ps.setInt(1,id);
            ps.executeQuery();

            rs = ps.executeQuery();
            while (rs.next()) {
                result.setId(rs.getInt(1));
                result.setAmount(rs.getDouble(2));
                result.setRegistration(new Registration());
                result.getRegistration().setId(rs.getInt(3));
                result.setTariff(new Tariff());
                result.getTariff().setName(rs.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            ps.close();
        }
        if(result.getId() != -1 && result.getAmount() != 0 && result.getRegistration().getId() != -1 && result.getTariff() != null)
            return result;
        return null;
    }

    @Override
    public boolean updateRegistrationTariff(RegistrationTariff registrationTariff) throws SQLException {
        PreparedStatement ps = null;
        try {
            //(id = ? AND amount = ? AND registration_id = ? AND tarrifs_name = ?)";
            String query = "UPDATE housekeeper.registrations_tariffs SET id = ?, amount = ?, registration_id = ?, tarrifs_name = ? WHERE id = ?";
            ps = getPrepareStatement(query);
            ps.setInt(1, registrationTariff.getId());
            ps.setDouble(2,registrationTariff.getAmount());
            ps.setInt(3,registrationTariff.getRegistration().getId());
            ps.setString(4,registrationTariff.getTariff().getName());
            ps.setInt(5,registrationTariff.getRegistration().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            ps.close();
        }
        return true;
    }

    @Override
    public boolean saveOrUpdateRegistrationTariff(RegistrationTariff registrationTariff) {
        RegistrationTariff buf;
        try{
            buf = findRegistrationTariff(registrationTariff.getId());
            if(buf != null)
                updateRegistrationTariff(registrationTariff);
            else insertRegistrationTariff(registrationTariff);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Collection<RegistrationTariff> findRegistrationsTariffsByRegistration(RegistrationTariff registrationTariff) throws SQLException {
        PreparedStatement ps = null;
        ArrayList<RegistrationTariff> result = new ArrayList<>();
        RegistrationTariff buf = new RegistrationTariff();
        try {
            String query = "Select * FROM housekeeper.registrations_tariffs ";
            ps = getPrepareStatement(query);
            ps.executeQuery();

            rs = ps.executeQuery();
            while (rs.next()) {
                buf = new RegistrationTariff();
                buf.setId(rs.getInt(1));
                buf.setAmount(rs.getDouble(2));
                buf.setRegistration(new Registration());
                buf.getRegistration().setId(rs.getInt(3));
                buf.setTariff(new Tariff());
                buf.getTariff().setName(rs.getString(4));
                result.add(buf);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            ps.close();
        }
        return result;
    }
}
