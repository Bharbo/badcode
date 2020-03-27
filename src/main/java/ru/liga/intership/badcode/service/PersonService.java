package ru.liga.intership.badcode.service;

import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.domain.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonService {

    final static String requestSql = "SELECT * FROM person WHERE sex = 'female' AND age >= 18";

    private static final Logger log = LoggerFactory.getLogger(PersonService.class);

    public static void getBMI() {
        List<Person> adultPersons = new ArrayList<>();
        try {
            Connection conn = connect("jdbc:hsqldb:mem:test", "sa", "");//Подключение к серверу базы данных выполнено
            ResultSet requestRes = getAdultPersons(conn);//SQL запрос выполнен. Полученые данные о взрослых
            adultPersons = getAdultPersonsAsList(requestRes);//Получен список взрослых
        } catch (Exception e) {
            e.printStackTrace();
        }
        double avgBodyIndex = Precision.round(getAvgBodyMassIndex(adultPersons), 2);//расчет ИМТ
        log.info("Средний индекс массы тела - {}", avgBodyIndex);
        System.out.println("Средний индекс массы тела - " + avgBodyIndex);
    }


    public static Connection connect(String url, String login, String pass) throws SQLException {
        return DriverManager.getConnection(url, login, pass);
    }

    public static ResultSet getAdultPersons(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();//Объект Statement для исполнения запросов SQL
        return statement.executeQuery(requestSql);
    }

    public static List<Person> getAdultPersonsAsList(ResultSet requestRes) throws SQLException {
        List<Person> adultPersons = new ArrayList<>();
        while (requestRes.next()) {
            Person p = new Person();
            p.setId(requestRes.getInt("id"));
            p.setSex(requestRes.getString("sex"));
            p.setName(requestRes.getString("name"));
            p.setAge(requestRes.getInt("age"));
            p.setWeight(requestRes.getInt("weight"));
            p.setHeight(requestRes.getInt("height"));
            adultPersons.add(p);
        }
        return adultPersons;
    }

    public static double getAvgBodyMassIndex(List<Person> adultPersons) {
        if (adultPersons.isEmpty()) {
            return 0;
        }
        double totalImt = 0.0;
        for (Person p : adultPersons) {
            double heightInMeters = p.getHeight() / 100d;
            double imt = p.getWeight() / (Double) (heightInMeters * heightInMeters);
            totalImt += imt;
        }
        long countOfPerson;
        countOfPerson = adultPersons.size();
        return totalImt / countOfPerson;
    }
}
