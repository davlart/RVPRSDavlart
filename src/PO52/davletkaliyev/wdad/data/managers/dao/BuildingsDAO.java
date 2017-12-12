package PO52.davletkaliyev.wdad.data.managers.dao;
import java.sql.SQLException;
import java.util.Collection;

public interface BuildingsDAO {
    public boolean insertBuilding (Building building) throws SQLException;
    public boolean deleteAuthor (Building building) throws SQLException;
    public Building findBuilding (int id) throws SQLException;
    public boolean updateBuilding (Building building) throws SQLException;
    public boolean saveOrUpdateBuilding (Building building);
    public Collection<Building> findBuildings (String streetName, int number) throws SQLException;
}
