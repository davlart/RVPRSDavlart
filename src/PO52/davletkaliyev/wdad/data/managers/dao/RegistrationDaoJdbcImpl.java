package PO52.davletkaliyev.wdad.data.managers.dao;

import com.sun.org.apache.regexp.internal.RE;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.PreparedStatement;
import java.util.Date;

public class RegistrationDaoJdbcImpl extends AbstractDAO implements RegistrationsDAO {
    private DataSource ds = null;
    private Connection con = null;
    private ResultSet rs = null;

    public RegistrationDaoJdbcImpl() throws Exception {
        super();
        this.ds = getDs();
        this.con = getCon();
    }

    @Override
    public boolean insertRegistration(Registration registration) throws SQLException {
        PreparedStatement ps = null;
        try {
            String query = "INSERT INTO housekeeper.registrations (id, date, flats_id) VALUES (?,?,?);";
            ps = getPrepareStatement(query);
            ps.setInt(1,registration.getId());
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            ps.setString(2,formatter.format(registration.getDate()));
            ps.setInt(3,registration.getFlat().getId());
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
    public boolean deleteRegistration(Registration registration) throws SQLException {
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM housekeeper.registrations WHERE ( id = ? AND date = ? AND flats_id = ? )";
            ps = getPrepareStatement(query);
            ps.setInt(1,registration.getId());
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            ps.setString(2,formatter.format(registration.getDate()));
            ps.setInt(3,registration.getFlat().getId());
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
    public Registration findRegistration(int id) throws SQLException {
        PreparedStatement ps = null;
        Registration result = new Registration();
        Flat flat;
        try {
            String query = "Select * FROM housekeeper.registrations WHERE id = ?";
            ps = getPrepareStatement(query);
            ps.setInt(1,id);
            ps.executeQuery();

            rs = ps.executeQuery();
            while (rs.next()) {
                result.setId(rs.getInt(1));
                result.setDate(rs.getDate(2));
                flat = new Flat();
                flat.setId(rs.getInt(3));
                result.setFlat(flat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            ps.close();
        }
        if(result.getId() != -1 && result.getFlat() != null)
            return result;
        return null;
    }

    @Override
    public boolean updateRegistration(Registration registration) throws SQLException {
        PreparedStatement ps = null;
        try {
            String query = "UPDATE housekeeper.registrations SET date = ?, flats_id = ? WHERE id = ?";
            ps = getPrepareStatement(query);
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            ps.setString(1,formatter.format(registration.getDate()));
            ps.setInt(2,registration.getFlat().getId());
            ps.setInt(3,registration.getId());
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
    public boolean saveOrUpdateRegistration(Registration registration) {
        Registration buf;
        try{
            buf = findRegistration(registration.getId());
            if(buf != null)
                updateRegistration(registration);
            else insertRegistration(registration);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Collection<Registration> findRegistrationsByDate(Date date) throws SQLException {
        PreparedStatement ps = null;
        ArrayList<Registration> result = new ArrayList<>();
        Registration buf = new Registration();
        Flat flat;
        try {
            String query = "Select * FROM housekeeper.registrations WHERE date = ?";
            ps = getPrepareStatement(query);
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            ps.setString(1,formatter.format(date));
            ps.executeQuery();

            rs = ps.executeQuery();
            while (rs.next()) {
                buf = new Registration();
                buf.setId(rs.getInt(1));
                buf.setDate(rs.getDate(2));
                flat = new Flat();
                flat.setId(rs.getInt(3));
                buf.setFlat(flat);
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

    @Override
    public Collection<Registration> findRegistrationsByFlat(Flat flat) throws SQLException {
        PreparedStatement ps = null;
        ArrayList<Registration> result = new ArrayList<>();
        Registration buf = new Registration();
        try {
            String query = "Select * FROM housekeeper.registrations WHERE flats_id = ?";
            ps = getPrepareStatement(query);
            ps.setInt(1,flat.getId());
            ps.executeQuery();

            rs = ps.executeQuery();
            while (rs.next()) {
                buf = new Registration();
                buf.setId(rs.getInt(1));
                buf.setDate(rs.getDate(2));
                buf.setFlat(flat);
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
