package PO52.davletkaliyev.wdad.data.managers.dao.DAOFactory;

import PO52.davletkaliyev.wdad.data.managers.dao.*;

public class sqlPerRequestDAOFactory extends DAOFactory {
    @Override
    public BuildingsDAO getBuildingsDAO() throws Exception {
        return new BuildingsDaoJdbcImpl();
    }

    @Override
    public TariffsDAO geTariffsDAO() throws Exception {
        return new TariffDAOJdbcImpl();
    }

    @Override
    public RegistrationsDAO getRegistrationsDAO() throws Exception {
        return new RegistrationDaoJdbcImpl();
    }

    @Override
    public FlatsDAO getFlatsDAO() throws Exception {
        return new FlatDaoJdbcImpl();
    }
}
