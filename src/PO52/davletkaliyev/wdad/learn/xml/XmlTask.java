package PO52.davletkaliyev.wdad.learn.xml;

/**
 * Created by ArthurArt on 09.10.2017.
 */
public class XmlTask {

//    возвращающий сумму, которую необходимо оплатить владельцам заданной квартиры.
//    Счет определяется разницей показаний счетчиков за текущий месяц и предыдущий. Тарифы определены так же в xml-документе
    public double getBill (String street, int buildingNumber, int flatNumber)
    {
        return 1;
    }

//    изменяющий стоимость заданной  единицы показания счетчика (ХВС, ГВС, электроэнергия, газ).
    public void setTariff (String tariffName, double newValue)
    {

    }

//    добавляющий показания счетчиков к заданной квартире в заданный период.
    public void addRegistration (String street, int buildingNumber, int flatNumber, int year, int month, double coldWater,
                                 double hotWater, double electricity, double gas)
    {

    }
}
