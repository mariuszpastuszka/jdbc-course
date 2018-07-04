package pl.mpas.jdbc_course.dao;

import org.junit.Assert;
import org.junit.Test;
import pl.mpas.jdbc_course.config.DbConnectionConfig;
import pl.mpas.jdbc_course.dao.impl.PersonDaoImpl;
import pl.mpas.jdbc_course.model.Person;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PersonDaoTest {

    @Test
    public void readAllPersonsFromDbTest() {
        try (Connection connection = DbConnectionConfig.getInstance().getConnection()) {
            PersonDao personDao = new PersonDaoImpl(connection);

            List<Person> peopleFromDb = personDao.readAllPersons();
            Assert.assertTrue("Didn't find any people inside db!", peopleFromDb.size() > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
