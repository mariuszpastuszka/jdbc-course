package pl.mpas.jdbc_course.model;

import java.util.Objects;

public class Owner {

    private Long id;
    private String name;
    private Sex sex;
    private String city;
    private String street;
    private String zipCode;
    private Dog dog;

    public Owner(Long id, String name, Sex sex, String city, String street, String zipCode, Dog dog) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
        this.dog = dog;
    }

    public Owner() {
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", dog=" + dog +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return Objects.equals(id, owner.id) &&
                Objects.equals(name, owner.name) &&
                sex == owner.sex &&
                Objects.equals(city, owner.city) &&
                Objects.equals(street, owner.street) &&
                Objects.equals(zipCode, owner.zipCode) &&
                Objects.equals(dog, owner.dog);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sex, city, street, zipCode, dog);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }
}
