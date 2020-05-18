package ru.liga.intership.badcode;

import org.apache.commons.math3.util.Precision;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.liga.intership.badcode.domain.Person;
import ru.liga.intership.badcode.service.PersonService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ru.liga.intership.badcode.service.PersonService.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BadcodeApplicationTests {
    private static ResultSet resultSet;
    private static Statement statement;
    private static List<Person> personList = new ArrayList<>();
    private String badRequest = "baaaaaaaaaad";
    private String goodRequest = "select * from person";

    @Before
    public void createConnection() throws SQLException {
        personList.add(new Person(1, "female", "Sveta", 50, 17, 170));
        personList.add(new Person(2, "male", "Andrey", 23, 26, 180));
        personList.add(new Person(3, "female", "Азалина", 100, 37, 165));
        personList.add(new Person(4, "male", "Канат", 74, 48, 139));
        Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:D:/soft/hsqldb-2.5.0/2", "user", "");
        statement = conn.createStatement();
        resultSet = getResultSet(statement, goodRequest);
    }

    @Test
    public void requestIsValid() throws SQLException {
        Assert.assertTrue(resultSet.next());
    }

    @Test(expected = SQLException.class)
    public void requestIsInvalid() throws SQLException {
        getResultSet(statement, badRequest);
    }

    @Test
    public void personListIsExists() throws SQLException {
        Assert.assertEquals(personList.size(), PersonService.getPersonsAsList(resultSet).size());
    }

    @Test
    public void isValidBMI() {
        double avgBodyIndex = Precision.round(getAvgBodyMassIndex(personList), 2);
        Assertions.assertThat(13.09).isEqualTo(avgBodyIndex);
    }
}
