package PO52.davletkaliyev.wdad.data.managers.dao;

import java.sql.SQLException;
import java.util.Collection;

public interface RegistrationTariffDAO {
    public boolean insertRegistrationTariff (RegistrationTariff registrationTariff) throws SQLException;
    public boolean deleteRegistrationTariff (RegistrationTariff registrationTariff) throws SQLException;
    public RegistrationTariff findRegistrationTariff (int id) throws SQLException;
    public boolean updateRegistrationTariff (RegistrationTariff registrationTariff) throws SQLException;
    public boolean saveOrUpdateRegistrationTariff (RegistrationTariff registrationTariff);
    public Collection<RegistrationTariff> findRegistrationsTariffsByRegistration (RegistrationTariff registrationTariff) throws SQLException;
}
