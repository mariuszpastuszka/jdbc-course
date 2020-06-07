package pl.mpas.jdbc_course.dao.impl;

import pl.mpas.jdbc_course.dao.DogDao;
import pl.mpas.jdbc_course.dao.OwnerDao;
import pl.mpas.jdbc_course.model.Dog;
import pl.mpas.jdbc_course.model.Owner;

import java.sql.*;

public class OwnerDaoImpl implements OwnerDao {

    private final Connection dbConnection;
    private final DogDao dogDao;

    public OwnerDaoImpl(final Connection dbConnection, final DogDao dogDao) {
        this.dbConnection = dbConnection;
        this.dogDao = dogDao;
    }

    @Override
    public Owner saveOwner(Owner toSave) {
        String dogInsertQuery = "" +
                "INSERT INTO DOG (NAME, BREED, OWNER_ID)                                \n" +
                "VALUES (?, ?, ?)                                                       \n";

        String dogUpdateQuery = "" +
                "UPDATE DOG                                                             \n" +
                "SET NAME = ?, BREED = ?, OWNER_ID = ?                                  \n" +
                "WHERE ID = ?                                                           \n";

        if (toSave.getId() == null) {
            // insert
            String ownerInsertQuery = "" +
                    "INSERT INTO OWNER (NAME, SEX, CITY, STREET, ZIPCODE)               \n" +
                    "VALUES (?, ?, ?, ?, ?)                                             \n";

            try {
                dbConnection.setAutoCommit(false);
                PreparedStatement insertStatement = dbConnection.prepareStatement(ownerInsertQuery, Statement.RETURN_GENERATED_KEYS);
                insertStatement.setString(1, toSave.getName());
                insertStatement.setString(2, String.valueOf(toSave.getSex().getSexMark()));
                insertStatement.setString(3, toSave.getCity());
                insertStatement.setString(4, toSave.getStreet());
                insertStatement.setString(5, toSave.getZipCode());

                insertStatement.executeUpdate();
                ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                generatedKeys.next();
                Long personId = generatedKeys.getLong(1);
                // now we can save dog:)

                Dog dogToSave = toSave.getDog();
                if (toSave.getDog().getId() == null) {
                    // save dog
                    PreparedStatement dogInsertStatement = dbConnection.prepareStatement(dogInsertQuery);
                    dogInsertStatement.setString(1, dogToSave.getName());
                    dogInsertStatement.setString(2, dogToSave.getBreed());
                    dogInsertStatement.setLong(3, personId);
                    dogInsertStatement.executeUpdate();

                } else {
                    // update dog
                    PreparedStatement dogUpdateStatement = dbConnection.prepareStatement(dogUpdateQuery);
                    dogUpdateStatement.setString(1, dogToSave.getName());
                    dogUpdateStatement.setString(2, dogToSave.getBreed());
                    dogUpdateStatement.setLong(3, personId);
                    dogUpdateStatement.setLong(4, dogToSave.getId());
                    dogUpdateStatement.executeUpdate();
                }
                dbConnection.commit();
                dbConnection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    dbConnection.rollback();
                    dbConnection.setAutoCommit(true);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }


        } else {
            // update
            String ownerUpdateQuery = "" +
                    "UPDATE OWNER                                                       \n" +
                    "SET NAME = ?, SEX = ?, CITY = ?, STREET = ?, ZIPCODE = ?           \n" +
                    "WHERE ID = ?                                                       \n";

            try {
                dbConnection.setAutoCommit(false);
                PreparedStatement updateOwnerStatement = dbConnection.prepareStatement(ownerUpdateQuery);
                updateOwnerStatement.setString(1, toSave.getName());
                updateOwnerStatement.setString(2, String.valueOf(toSave.getSex().getSexMark()));
                updateOwnerStatement.setString(3, toSave.getCity());
                updateOwnerStatement.setString(4, toSave.getStreet());
                updateOwnerStatement.setString(5, toSave.getZipCode());
                updateOwnerStatement.setLong(6, toSave.getId());

                updateOwnerStatement.executeUpdate(); // data saved into db

                // save dog
                Dog dogToSave = toSave.getDog();
                if (dogToSave.getId() == null) {
                    // save dog
                    PreparedStatement dogInsertStatement = dbConnection.prepareStatement(dogInsertQuery);
                    dogInsertStatement.setString(1, dogToSave.getName());
                    dogInsertStatement.setString(2, dogToSave.getBreed());
                    dogInsertStatement.setLong(3, toSave.getId());
                    dogInsertStatement.executeUpdate();

                } else {
                    // update dog
                    PreparedStatement dogUpdateStatement = dbConnection.prepareStatement(dogUpdateQuery);
                    dogUpdateStatement.setString(1, dogToSave.getName());
                    dogUpdateStatement.setString(2, dogToSave.getBreed());
                    dogUpdateStatement.setLong(3, toSave.getId());
                    dogUpdateStatement.setLong(4, dogToSave.getId());
                    dogUpdateStatement.executeUpdate();
                }
                dbConnection.commit();
                dbConnection.setAutoCommit(true);

            } catch (SQLException e) {
                try {
                    dbConnection.rollback();
                    dbConnection.setAutoCommit(true);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
        return toSave;
    }
}
