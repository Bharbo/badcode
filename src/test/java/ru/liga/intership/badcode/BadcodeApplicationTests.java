package ru.liga.intership.badcode;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.liga.intership.badcode.domain.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static ru.liga.intership.badcode.service.PersonService.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BadcodeApplicationTests {

    public BadcodeApplicationTests() throws SQLException {
    }

    Connection conn = connect("jdbc:hsqldb:mem:test", "sa", "");
    ResultSet requestRes = getAdultPersons(conn);
    List<Person> adultPersons = getAdultPersonsAsList(requestRes);

    @Test
    public void checkResultOfRequest() {

        Person p1 = new Person(5, "female", "Sveta", 50, 170, 18);
        Assertions.assertThat(p1).isEqualTo(adultPersons.get(0));
    }

    @Test
    public void checkIndex() {

        double avgBodyIndex = getAvgBodyMassIndex(adultPersons);
        Assertions.assertThat(20.76).isEqualTo(avgBodyIndex);
    }
}
