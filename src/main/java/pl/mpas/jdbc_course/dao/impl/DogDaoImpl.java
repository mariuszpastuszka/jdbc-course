package pl.mpas.jdbc_course.dao.impl;

import pl.mpas.jdbc_course.dao.DogDao;
import pl.mpas.jdbc_course.model.Dog;

import java.sql.Connection;

public class DogDaoImpl implements DogDao {

    private final Connection dbConnection;

    public DogDaoImpl(final Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Dog save(Dog toSave, Long ownerId) {
        return null;
    }
}
