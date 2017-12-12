package PO52.davletkaliyev.wdad.data.managers.dao;
import java.util.Collection;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BuildingsDaoJdbcImpl extends AbstractDAO implements BuildingsDAO {
    private DataSource ds = null;
    private Connection con = null;
    private ResultSet rs = null;

    public BuildingsDaoJdbcImpl() throws Exception {
        super();
        this.ds = getDs();
        this.con = getCon();
    }

    @Override
    public boolean insertBuilding(Building building) throws SQLException {
        PreparedStatement ps = null;
        try {
            String query = "INSERT INTO housekeeper.buildings (id, number, street_id) VALUES (?,?,?);";
            ps = getPrepareStatement(query);
            ps.setInt(1,building.getId());
            ps.setInt(2,building.getNumber());
            ps.setInt(3,building.getStreet().getId());
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
    public boolean deleteAuthor(Building building) throws SQLException {
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM housekeeper.buildings WHERE (id = ? AND number = ? AND street_id = ?)";
            ps = getPrepareStatement(query);
            ps.setInt(1,building.getId());
            ps.setInt(2,building.getNumber());
            ps.setInt(3,building.getStreet().getId());
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
    public Building findBuilding(int id) throws SQLException {
        PreparedStatement ps = null;
        Building result = new Building();
        Street street;
        try {
            String query = "Select * FROM buildings WHERE id = ?";
            ps = getPrepareStatement(query);
            ps.setInt(1,id);
            ps.executeQuery();

            rs = ps.executeQuery();
            while (rs.next()) {
                result.setId(rs.getInt(1));
                result.setNumber(rs.getInt(2));
                street = new Street();
                street.setId(rs.getInt(3));
                result.setStreet(street);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            ps.close();
        }
        if(result.getNumber() != -1 && result.getStreet().getId() != -1)
            return result;
        return null;
    }

    @Override
    public boolean updateBuilding(Building building) throws SQLException {
        PreparedStatement ps = null;
        try {
            String query = "UPDATE buildings SET number = ?, street_id = ? WHERE id = ?";
            ps = getPrepareStatement(query);
            ps.setInt(1,building.getNumber());
            ps.setInt(2,building.getStreet().getId());
            ps.setInt(1,building.getId());
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
    public boolean saveOrUpdateBuilding(Building building) {
        Building buf;
        try{
            buf = findBuilding(building.getId());
            if(buf != null)
                updateBuilding(building);
            else insertBuilding(building);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Collection<Building> findBuildings(String streetName, int number) throws SQLException {
        PreparedStatement ps = null;
        ArrayList<Building> result = new ArrayList<>();
        Building buf = new Building();
        Street street;
        try {
            String query = "Select buildings.id, buildings.number, buildings.street_id FROM buildings JOIN (street) " +
                    "ON ( buildings.street_id = street.id AND buildings.number = ? AND street.name = ?)";
            ps = getPrepareStatement(query);
            ps.setInt(1,number);
            ps.setString(2,streetName);
            ps.executeQuery();

            rs = ps.executeQuery();
            while (rs.next()) {
                buf = new Building();
                buf.setId(rs.getInt(1));
                buf.setNumber(rs.getInt(2));
                street = new Street();
                street.setId(rs.getInt(3));
                buf.setStreet(street);
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
