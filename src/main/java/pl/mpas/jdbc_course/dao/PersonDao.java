package pl.mpas.jdbc_course.dao;

import pl.mpas.jdbc_course.model.Person;

import java.util.List;

public interface PersonDao {

    List<Person> readAllPersons();

    List<Person> readOnlyAdult();

    List<Person> readChildren();

    boolean savePerson(Person somebody);

    boolean savePersonV2(Person person);

    List<Person> findBySurname(String surname);

    int updatePersonAge(int personId, int newAge);

    int deletePersonBySurname(String personSurname);

    int getMaxIdForPersons();
}
