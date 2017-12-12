package PO52.davletkaliyev.wdad.data.managers.dao;

import PO52.davletkaliyev.wdad.data.storage.DataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class  AbstractDAO {
    private DataSource ds = null;

    public DataSource getDs() {
        return ds;
    }

    public Connection getCon() {
        return con;
    }

    private Connection con = null;
    private ResultSet rs = null;

    public AbstractDAO() throws Exception {
        this.ds = DataSourceFactory.createDataSource();
        this.con = ds.getConnection();
    }

    public void closePrepareStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Получение экземпляра PrepareStatement
    public PreparedStatement getPrepareStatement(String sql) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    public void closeConnection() throws SQLException {
        try {
            con.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                con.close();
            }
        }
    }
}
