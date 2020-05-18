package ru.liga.intership.badcode.service;

import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.intership.badcode.domain.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonService {
    final static String requestSql = "SELECT * FROM person";
    private static final Logger log = LoggerFactory.getLogger(PersonService.class);

    public static void getBMI() {
        List<Person> personList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:D:/soft/hsqldb-2.5.0/2", "user", "")) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = getResultSet(statement, requestSql);
            personList = getPersonsAsList(resultSet);
        } catch (SQLException e) {
            e.getMessage();
        }
        double avgBodyIndex = Precision.round(getAvgBodyMassIndex(personList), 2);//расчет ИМТ
        log.info("Средний индекс массы тела - {}", avgBodyIndex);
    }

    public static ResultSet getResultSet(Statement statement, String requestSql) throws SQLException {
        try {
            return statement.executeQuery(requestSql);
        } catch (SQLException e) {
            e.getMessage();
            throw new SQLException();
        }
    }

    public static List<Person> getPersonsAsList(ResultSet resultSet) throws SQLException {
        List<Person> adultPersons = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Person p = new Person();
                p.setId(resultSet.getInt("id"));
                p.setSex(resultSet.getString("sex"));
                p.setName(resultSet.getString("name"));
                p.setAge(resultSet.getInt("age"));
                p.setWeight(resultSet.getInt("weight"));
                p.setHeight(resultSet.getInt("height"));
                adultPersons.add(p);
            }
            return adultPersons;
        } catch (SQLException e) {
            e.getMessage();
            throw new SQLException();
        }
    }

    public static double getAvgBodyMassIndex(List<Person> adultPersons) {
        if (adultPersons.isEmpty()) {
            return 0;
        }
        double totalImt = 0;
        for (Person p : adultPersons) {
            double heightInMeters = p.getHeight() / 100d;
            double imt = p.getWeight() / (Double)(heightInMeters * heightInMeters);
            totalImt += imt;
        }
        int countOfPerson = adultPersons.size();
        return totalImt / countOfPerson;
    }
}

//        personList.add(new Person(1, "female", "Sveta", 50, 17, 170));
//        personList.add(new Person(2, "male", "Andrey", 23, 26, 180));
//        personList.add(new Person(3, "female", "Азалина", 100, 37, 165));
//        personList.add(new Person(4, "male", "Канат", 74, 48, 139));

//17 / (1,7 * 1,7) = 5,882352941176471‬
//8,024691358024691‬
//13,59044995408632‬
//24,84343460483412‬


//    //5.88
////        8.02
////        13.59
////        24.84
////
////        51,63‬/4 = 12,9075‬