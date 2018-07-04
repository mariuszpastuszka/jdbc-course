package pl.mpas.jdbc_course.model;

public class Person {

    public static final int ID_OF_NOT_PERSISTENT_PERSON = -1;

    private int id;
    private String name;
    private String surname;
    private int salary;

    public Person(String name, String surname, int salary) {
        this.id = ID_OF_NOT_PERSISTENT_PERSON;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
    }

    public Person(int id, String name, String surname, int salary) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", salary=" + salary +
                '}';
    }
}
