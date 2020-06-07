package pl.mpas.jdbc_course.dao.impl;

import pl.mpas.jdbc_course.dao.DogDao;
import pl.mpas.jdbc_course.model.Dog;

import java.sql.*;

public class DogDaoImpl implements DogDao {

    private static final String dogInsertQuery = "" +
            "INSERT INTO DOG (NAME, BREED, OWNER_ID)                                \n" +
            "VALUES (?, ?, ?)                                                       \n";

    private static final String dogUpdateQuery = "" +
            "UPDATE DOG                                                             \n" +
            "SET NAME = ?, BREED = ?, OWNER_ID = ?                                  \n" +
            "WHERE ID = ?                                                           \n";

    private final Connection dbConnection;

    public DogDaoImpl(final Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Dog save(Dog toSave, Long ownerId) {
        try {

            if (toSave.getId() == null) {
                // save dog
                PreparedStatement dogInsertStatement = dbConnection.prepareStatement(dogInsertQuery,
                        Statement.RETURN_GENERATED_KEYS);
                dogInsertStatement.setString(1, toSave.getName());
                dogInsertStatement.setString(2, toSave.getBreed());
                dogInsertStatement.setLong(3, ownerId);
                dogInsertStatement.executeUpdate();
                ResultSet generatedKeys = dogInsertStatement.getGeneratedKeys();
                generatedKeys.next();
                Long dogId = generatedKeys.getLong(1);
                toSave.setId(dogId);

            } else {
                // update dog
                PreparedStatement dogUpdateStatement = dbConnection.prepareStatement(dogUpdateQuery);
                dogUpdateStatement.setString(1, toSave.getName());
                dogUpdateStatement.setString(2, toSave.getBreed());
                dogUpdateStatement.setLong(3, ownerId);
                dogUpdateStatement.setLong(4, toSave.getId());
                dogUpdateStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toSave;
    }
}
