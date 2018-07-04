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

    @Test
    public void readOnlyAdultTest() {
        try (Connection connection = DbConnectionConfig.getInstance().getConnection()) {
            PersonDao personDao = new PersonDaoImpl(connection);

            List<Person> peopleFromDb = personDao.readOnlyAdult();
            Assert.assertTrue("Didn't find any adult inside db!", peopleFromDb.size() > 0);

            for (Person p : peopleFromDb) {
                Assert.assertTrue("This is not adult: " + p, p.getAge() >= 18);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void savePersonTest() {
        Person somebodyToSave = new Person("Helena", "J.", 15);

        Assert.assertTrue("Constructed object has wrong value of id", Person.ID_OF_NOT_PERSISTENT_PERSON == somebodyToSave.getId());

        try (Connection connection = DbConnectionConfig.getInstance().getConnection()) {
            PersonDao personDao = new PersonDaoImpl(connection);

            List<Person> peopleFromDbSoFar = personDao.readOnlyAdult();

            personDao.savePerson(somebodyToSave);
            Assert.assertTrue("Person wasn't save to db", somebodyToSave.getId() != Person.ID_OF_NOT_PERSISTENT_PERSON);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
