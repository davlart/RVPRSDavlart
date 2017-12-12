package PO52.davletkaliyev.wdad.data.managers.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

public interface RegistrationsDAO {
    public boolean insertRegistration (Registration registration) throws SQLException;
    public boolean deleteRegistration (Registration registration) throws SQLException;
    public Registration findRegistration (int id) throws SQLException;
    public boolean updateRegistration (Registration registration) throws SQLException;
    public boolean saveOrUpdateRegistration (Registration registration);
    public Collection<Registration> findRegistrationsByDate (Date date) throws SQLException;
    public Collection<Registration> findRegistrationsByFlat (Flat flat) throws SQLException;
}
