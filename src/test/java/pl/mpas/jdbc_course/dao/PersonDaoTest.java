package pl.mpas.jdbc_course.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.mpas.jdbc_course.config.DbConnectionConfig;
import pl.mpas.jdbc_course.dao.impl.PersonDaoImpl;
import pl.mpas.jdbc_course.model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PersonDaoTest {

    @Before
    public void initData() {
        String deleteQuery = "DELETE FROM PERSONS";

        // init db
        String initQuery = "" +
            "INSERT INTO Persons (NAME, SURNAME, AGE) VALUES ('Marcin', 'P.', 30);  \n" +
            "INSERT INTO Persons (NAME, SURNAME, AGE) VALUES ('Maria', 'W.', 18);   \n" +
            "INSERT INTO Persons (NAME, SURNAME, AGE) VALUES ('Eryk', 'S.', 10);    \n" +
            "INSERT INTO Persons (NAME, SURNAME, AGE) VALUES ('Ania', 'L.', 28);    \n" +
            "INSERT INTO Persons (NAME, SURNAME, AGE) VALUES ('Robert', 'L.', 30);  \n" +
            "";

        // remove all data
        Connection dbConnection = DbConnectionConfig.getInstance().getConnection();
        try {
            PreparedStatement deleteStatement = dbConnection.prepareStatement(deleteQuery);
            deleteStatement.executeUpdate();

            PreparedStatement insertStatement = dbConnection.prepareStatement(initQuery);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readAllPersonsFromDbTest() {
        Connection connection = DbConnectionConfig.getInstance().getConnection();
        PersonDao personDao = new PersonDaoImpl(connection);

        List<Person> peopleFromDb = personDao.readAllPersons();
        Assert.assertTrue("Didn't find any people inside db!", peopleFromDb.size() > 0);
    }

    @Test
    public void readOnlyAdultTest() {
        Connection connection = DbConnectionConfig.getInstance().getConnection();
        PersonDao personDao = new PersonDaoImpl(connection);

        List<Person> peopleFromDb = personDao.readOnlyAdult();
        Assert.assertTrue("Didn't find any adult inside db!", peopleFromDb.size() > 0);

        for (Person p : peopleFromDb) {
            Assert.assertTrue("This is not adult: " + p, p.getAge() >= 18);
        }
    }

    @Test
    public void updateAgeTest() {
        // użyj zdefiniowanego API
        try {
            Connection dbConnection = DbConnectionConfig.getInstance().getConnection();
            PersonDao personDao = new PersonDaoImpl(dbConnection);

            // max person id
            final int idOfUpdatedPerson = personDao.getMaxIdForPersons();
            int numberOfChangedRecords = personDao.updatePersonAge(idOfUpdatedPerson, 31);
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
    public void savePersonBetterWay() {
        Person beforeSave = new Person("John", "Doe", 15);
        System.out.println("Person before saving: " + beforeSave);
        Assert.assertEquals(Person.ID_OF_NOT_PERSISTENT_PERSON, beforeSave.getId());

        // save person in db
        Connection dbConnection = DbConnectionConfig.getInstance().getConnection();
        PersonDao personDao = new PersonDaoImpl(dbConnection);
        personDao.savePersonV2(beforeSave);
        System.out.println("Person after saving to db: " + beforeSave);

        Assert.assertTrue(beforeSave.getId() != Person.ID_OF_NOT_PERSISTENT_PERSON);
    }

    @Test
    public void deletePersonBySurnameTest() {
        final String surnameToDelete = "L.";

        try {
            Connection dbConnection = DbConnectionConfig.getInstance().getConnection();
            PersonDao personDao = new PersonDaoImpl(dbConnection);
            int numberOfDeletedRecords = personDao.deletePersonBySurname(surnameToDelete);

            Assert.assertEquals(2, numberOfDeletedRecords);
        } catch (Exception e) {
            Assert.fail("deletePersonBySurnameTest - failed: " + e.getMessage());
        }
    }

    @Test
    public void savePersonTest() {
        Person somebodyToSave = new Person("Helena", "J.", 15);

        Assert.assertTrue("Constructed object has wrong value of id", Person.ID_OF_NOT_PERSISTENT_PERSON == somebodyToSave.getId());

        // try with resources
//        Connection connection = null;
//        try {
//            connection = DbConnectionConfig.getInstance().getConnection();
//        } catch (Exception e) {
//
//        } finally {
//            if (null != connection) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                }
//            }
//        }
        Connection connection = DbConnectionConfig.getInstance().getConnection();
        PersonDao personDao = new PersonDaoImpl(connection);

        personDao.savePerson(somebodyToSave);
        Assert.assertTrue("Person wasn't save to db", somebodyToSave.getId() != Person.ID_OF_NOT_PERSISTENT_PERSON);
        // TODO:MP get all persons again and compare if returned list contains new added person
    }

    @Test
    public void checkPersonByName() {
        Connection connection = DbConnectionConfig.getInstance().getConnection();
        PersonDao personDao = new PersonDaoImpl(connection);

        List<Person> persons = personDao.findBySurname("L.");
        System.out.println("found persons: " + persons);
        Assert.assertEquals("wrong data found!!!", 2, persons.size());
    }
}
