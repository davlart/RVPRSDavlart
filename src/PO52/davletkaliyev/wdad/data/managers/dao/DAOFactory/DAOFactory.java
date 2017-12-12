package PO52.davletkaliyev.wdad.data.managers.dao.DAOFactory;

import PO52.davletkaliyev.wdad.data.managers.dao.*;

public abstract class DAOFactory {
    protected DAOFactory(){}

    public static DAOFactory getDaoFactory(String type) throws Exception {
        switch (type){
            case "BuildingsDAO":
                return new BuildingsDAOFactory();
            case "TariffsDAO":
                return new TariffsDAOFactory();
            case "RegistrationsDAO":
                return new RegistrationsDAOFactory();
            case "FlatsDAO":
                return new FlatsDAOFactory();
            default:
                return null;
        }
    };

    public abstract BuildingsDAO getBuildingsDAO () throws Exception;
    public abstract TariffsDAO geTariffsDAO () throws Exception;
    public abstract RegistrationsDAO getRegistrationsDAO () throws Exception;
    public abstract FlatsDAO getFlatsDAO () throws Exception;
}
