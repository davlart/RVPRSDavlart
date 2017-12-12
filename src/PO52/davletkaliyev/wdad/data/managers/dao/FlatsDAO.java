package PO52.davletkaliyev.wdad.data.managers.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

public interface FlatsDAO {
    public boolean insertFlat (Flat flat) throws SQLException;
    public boolean deleteFlat (Flat flat) throws SQLException;
    public Flat findFlat (int id) throws SQLException;
    public boolean updateFlat (Flat flat) throws SQLException;
    public boolean saveOrUpdateFlat (Flat flat);
    public Collection<Flat>
    findFlatsByLastRegistrationDate (Date regDate) throws SQLException;
}
