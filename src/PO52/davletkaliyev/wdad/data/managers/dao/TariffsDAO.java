package PO52.davletkaliyev.wdad.data.managers.dao;

import java.sql.SQLException;
import java.util.Collection;

public interface TariffsDAO{
    public boolean insertTariff (Tariff tariff) throws SQLException;
    public boolean deleteTariff (Tariff tariff) throws SQLException;
    public Tariff findTariff (String name) throws SQLException;
    public boolean updateTariff (Tariff tariff) throws SQLException;
    public boolean saveOrUpdateTariff (Tariff tariff) throws SQLException;
    public Collection<Tariff> findTariffs () throws SQLException;
}
