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
    public void updateAgeTest() {
        // użyj zdefiniowanego API
        try {
            Connection dbConnection = DbConnectionConfig.getInstance().getConnection();
            PersonDao personDao = new PersonDaoImpl(dbConnection);

            int numberOfChangedRecords = personDao.updatePersonAge(3, 31);
            // sprawdź czy rekord został zmieniony
            Assert.assertEquals("Something wrong has happended", 1, numberOfChangedRecords);
            numberOfChangedRecords = personDao.updatePersonAge(-1, 5);
            Assert.assertEquals(0, numberOfChangedRecords);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Test
    public void deletePersonBySurnameTest() {

    }

    @Test
    public void savePersonTest() {
        Person somebodyToSave = new Person("Helena", "J.", 15);

        Assert.assertTrue("Constructed object has wrong value of id", Person.ID_OF_NOT_PERSISTENT_PERSON == somebodyToSave.getId());

        try (Connection connection = DbConnectionConfig.getInstance().getConnection()) {
            PersonDao personDao = new PersonDaoImpl(connection);

            List<Person> peopleFromDbSoFar = personDao.readAllPersons();

            personDao.savePerson(somebodyToSave);
            Assert.assertTrue("Person wasn't save to db", somebodyToSave.getId() != Person.ID_OF_NOT_PERSISTENT_PERSON);
            // TODO:MP get all persons again and compare if returned list contains new added person
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkPersonByName() {
        try (Connection connection = DbConnectionConfig.getInstance().getConnection()) {
            PersonDao personDao = new PersonDaoImpl(connection);

            List<Person> persons = personDao.findBySurname("L.");
            System.out.println("found persons: " + persons);
            Assert.assertEquals("wrong data found!!!", 2, persons.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
