package pl.mpas.jdbc_course.dao;

import pl.mpas.jdbc_course.model.Person;

import java.util.List;

public interface PersonDao {

    List<Person> readAllPersons();

    List<Person> readOnlyAdult();

    List<Person> readChildren();

    boolean savePerson(Person somebody);

    List<Person> findBySurname(String surname);

    int updatePerson(int personId, int newAge);
}
