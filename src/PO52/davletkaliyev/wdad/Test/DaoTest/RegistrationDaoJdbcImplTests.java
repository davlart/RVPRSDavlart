package PO52.davletkaliyev.wdad.Test.DaoTest;

import PO52.davletkaliyev.wdad.data.managers.dao.Flat;
import PO52.davletkaliyev.wdad.data.managers.dao.Registration;
import PO52.davletkaliyev.wdad.data.managers.dao.RegistrationDaoJdbcImpl;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class RegistrationDaoJdbcImplTests {
    public static void main(String[] args) throws Exception {
        RegistrationDaoJdbcImpl registrationDaoJdbc = new RegistrationDaoJdbcImpl();

//        registrationDaoJdbc.insertRegistration(getRegistration());
//        System.out.println(registrationDaoJdbc.findRegistration(12));
        //registrationDaoJdbc.deleteRegistration(getRegistration());

        //registrationDaoJdbc.saveOrUpdateRegistration(getRegistration());
        //ArrayList<Registration> registrations = (ArrayList<Registration>) registrationDaoJdbc.findRegistrationsByDate(new SimpleDateFormat( "yyyy-MM-dd" ).parse( "2012-12-9" ));
//        Flat flat = new Flat();
//        flat.setId(2);
//        ArrayList<Registration> registrations = (ArrayList<Registration>) registrationDaoJdbc.findRegistrationsByFlat(flat);
//        for (Registration registration: registrations) {
//            System.out.println(registration.toString());
//        }
        //registrationDaoJdbc.deleteRegistration(getRegistration());

        registrationDaoJdbc.closeConnection();
    }

    private static Registration getRegistration() throws ParseException {
        Registration registration = new Registration();
        registration.setId(12);
        registration.setDate(new SimpleDateFormat( "yyyy-MM-dd" ).parse( "2012-12-9" ));
        Flat flat = new Flat();
        flat.setId(1);
        registration.setFlat(flat);

        return registration;
    }



}
