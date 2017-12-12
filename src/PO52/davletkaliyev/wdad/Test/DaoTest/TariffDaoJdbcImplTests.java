package PO52.davletkaliyev.wdad.Test.DaoTest;

import PO52.davletkaliyev.wdad.data.managers.dao.Tariff;
import PO52.davletkaliyev.wdad.data.managers.dao.TariffDAOJdbcImpl;

public class TariffDaoJdbcImplTests {


    public static void main(String[] args) throws Exception {
        TariffDAOJdbcImpl tariffDaoJdbc = new TariffDAOJdbcImpl();
        Tariff tariff = new Tariff();
        tariff.setName("coldwaterTEST");
        tariff.setCost(1111.0);

        //tariffDaoJdbc.insertTariff(tariff);

       //tariffDaoJdbc.deleteTariff(tariff);

       //System.out.println(tariffDaoJdbc.findTariff("coldwater").toString());

//        tariffDaoJdbc.insertTariff(tariff);1
//        tariff.setCost(666.666);1
//       tariffDaoJdbc.updateTariff(tariff);1

//       ArrayList<Tariff> tariffs = (ArrayList<Tariff>) tariffDaoJdbc.findTariffs();
//        for (Tariff tarif: tariffs) {
//            System.out.println(tarif.toString());
//        }
        //tariffDaoJdbc.saveOrUpdateTariff(tariff);
        //tariffDaoJdbc.deleteTariff(tariff);
        tariffDaoJdbc.closeConnection();
    }

}
