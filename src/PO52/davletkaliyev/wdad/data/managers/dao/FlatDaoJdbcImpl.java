package PO52.davletkaliyev.wdad.data.managers.dao;

import java.sql.ResultSet;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class FlatDaoJdbcImpl extends AbstractDAO implements FlatsDAO {
    private DataSource ds = null;
    private Connection con = null;
    private ResultSet rs = null;

    public FlatDaoJdbcImpl() throws Exception {
        super();

    }

    @Override
    public boolean insertFlat(Flat flat) throws SQLException {
        PreparedStatement ps = null;
        try {
            String query = "INSERT INTO housekeeper.flats (id, number, buildings_id) VALUES (?,?,?);";
            ps = getPrepareStatement(query);
            ps.setInt(1,flat.getId());
            ps.setInt(2,flat.getNumber());
            ps.setInt(3,flat.getBuilding().getId());
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
    public boolean deleteFlat(Flat flat) throws SQLException {
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM housekeeper.flats WHERE (id = ? AND number = ? AND  buildings_id = ?)";
            ps = getPrepareStatement(query);
            ps.setInt(1,flat.getId());
            ps.setInt(2,flat.getNumber());
            ps.setInt(3,flat.getBuilding().getId());
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
    public Flat findFlat(int id) throws SQLException {
        PreparedStatement ps = null;
        Flat result = new Flat();
        Building bufBuilding;
        try {
            String query = "Select * FROM housekeeper.flats WHERE id = ?";
            ps = getPrepareStatement(query);
            ps.setInt(1,id);
            ps.executeQuery();

            rs = ps.executeQuery();
            while (rs.next()) {
                result.setId(rs.getInt(1));
                result.setNumber(rs.getInt(2));
                bufBuilding = new Building();
                bufBuilding.setId(rs.getInt(3));
                result.setBuilding(bufBuilding);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            ps.close();
        }
        if(result.getNumber() != -1 && result.getBuilding().getId() != -1)
            return result;
        return null;
    }

    @Override
    public boolean updateFlat(Flat flat) throws SQLException {
        PreparedStatement ps = null;
        try {
            String query = "UPDATE housekeeper.flats SET number = ?, flats_id = ? WHERE id = ?";
            ps = getPrepareStatement(query);
            ps.setInt(1,flat.getNumber());
            ps.setInt(2,flat.getBuilding().getId());
            ps.setInt(3,flat.getId());
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
    public boolean saveOrUpdateFlat(Flat flat) {
        Flat buf;
        try{
            buf = findFlat(flat.getId());
            if(buf != null)
                updateFlat(flat);
            else insertFlat(flat);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Collection<Flat> findFlatsByLastRegistrationDate(Date regDate) throws SQLException {
        PreparedStatement ps = null;
        ArrayList<Flat> result = new ArrayList<>();
        Flat buf = new Flat();
        Building building;
        try {
            String query = "Select flats.id, flats.number, flats.buildings_id FROM flats JOIN (registrations) ON (registrations.flats_id = flats.id AND registrations.date = ?) ";
            ps = getPrepareStatement(query);
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            ps.setString(1,formatter.format(regDate));
            ps.executeQuery();

            rs = ps.executeQuery();
            while (rs.next()) {
                buf = new Flat();
                buf.setId(rs.getInt(1));
                buf.setNumber(rs.getInt(2));
                building = new Building();
                building.setId(rs.getInt(3));
                buf.setBuilding(building);
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
