package pl.mpas.jdbc_course.dao.impl;

import pl.mpas.jdbc_course.dao.PersonDao;
import pl.mpas.jdbc_course.model.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonDaoImpl implements PersonDao {

    private Connection dbConnection;

    public PersonDaoImpl(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Person> readAllPersons() {
        List<Person> persons = new ArrayList<>();

        String query =
                "SELECT ID, NAME, SURNAME, AGE\n" +
                "FROM JDBC_SCHEMA.PERSONS";

        try {
            Statement statement = dbConnection.createStatement();

            int readId = Person.ID_OF_NOT_PERSISTENT_PERSON;
            String readName;
            String readSurname;
            int readAge = 0;
            Person readPerson;
            ResultSet resultFromDb = statement.executeQuery(query);
            while (resultFromDb.next()) {
                readId = resultFromDb.getInt(1);
                readName = resultFromDb.getString(2);
                readSurname = resultFromDb.getString(3);
                readAge = resultFromDb.getInt(4);
                readPerson = new Person(readId, readName, readSurname, readAge);
                System.out.println(String.format("Person read from db: [%s]", readPerson));
                persons.add(readPerson);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return persons;
    }

    @Override
    public List<Person> readOnlyAdult() {
        return null;
    }

    @Override
    public List<Person> readChildren() {
        return null;
    }

    // read id and set into Person object
    @Override
    public boolean savePerson(Person somebody) {
        return false;
    }
}
