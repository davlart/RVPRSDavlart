package PO52.davletkaliyev.wdad.data.managers.dao;

import PO52.davletkaliyev.wdad.data.managers.dao.DAOFactory.DAOFactory;
import PO52.davletkaliyev.wdad.data.storage.DataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class TariffDAOJdbcImpl extends AbstractDAO implements TariffsDAO{
    private DataSource ds = null;
    private Connection con = null;
    private ResultSet rs = null;

    public TariffDAOJdbcImpl() throws Exception {
        super();
        this.ds = getDs();
        this.con = getCon();
    }

    @Override
    public boolean insertTariff(Tariff tariff) throws SQLException {
        PreparedStatement ps = null;
        try {
            String query = "INSERT INTO housekeeper.tarrifs (name,cost) VALUES (?,?);";
            ps = getPrepareStatement(query);
            ps.setString(1,tariff.getName());
            ps.setDouble(2,tariff.getCost());
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
    public boolean deleteTariff(Tariff tariff) throws SQLException {
        PreparedStatement ps = null;
        try {
            String query = "DELETE FROM housekeeper.tarrifs WHERE ( name = ? AND cost = ? )";
            ps = getPrepareStatement(query);
            ps.setString(1,tariff.getName());
            ps.setDouble(2,tariff.getCost());
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
    public Tariff findTariff(String name) throws SQLException {
        PreparedStatement ps = null;
        Tariff result = new Tariff();
        try {
            String query = "Select * FROM tarrifs WHERE name = ?";
            ps = getPrepareStatement(query);
            ps.setString(1,name);
            ps.executeQuery();

            rs = ps.executeQuery();
            while (rs.next()) {
                result.setName(rs.getString(1));
                result.setCost(rs.getDouble(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            ps.close();
        }
        if(result.getName() != null && result.getCost() != 0)
        return result;
        return null;
    }

    @Override
    public boolean updateTariff(Tariff tariff) throws SQLException {
        PreparedStatement ps = null;
        try {
            String query = "UPDATE tarrifs SET cost = ? WHERE name = ?";
            ps = getPrepareStatement(query);
            ps.setDouble(1,tariff.getCost());
            ps.setString(2,tariff.getName());
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
    public boolean saveOrUpdateTariff(Tariff tariff) throws SQLException {
        Tariff buf;
        try{
            buf = findTariff(tariff.getName());
            if(buf != null)
                updateTariff(tariff);
            else insertTariff(tariff);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Collection<Tariff> findTariffs() throws SQLException {
        PreparedStatement ps = null;
        ArrayList<Tariff> result = new ArrayList<>();
        Tariff buf = new Tariff();
        try {
            String query = "Select * FROM tarrifs ";
            ps = getPrepareStatement(query);
            ps.executeQuery();

            rs = ps.executeQuery();
            while (rs.next()) {
                buf = new Tariff();
                buf.setName(rs.getString(1));
                buf.setCost(rs.getDouble(2));
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
