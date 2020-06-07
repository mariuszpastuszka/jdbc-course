package pl.mpas.jdbc_course.dao.impl;

import pl.mpas.jdbc_course.dao.DogDao;
import pl.mpas.jdbc_course.dao.OwnerDao;
import pl.mpas.jdbc_course.model.Dog;
import pl.mpas.jdbc_course.model.Owner;

import java.sql.*;

public class OwnerDaoImpl implements OwnerDao {

    private static final String ownerInsertQuery = "" +
            "INSERT INTO OWNER (NAME, SEX, CITY, STREET, ZIPCODE)               \n" +
            "VALUES (?, ?, ?, ?, ?)                                             \n";

    private static final String ownerUpdateQuery = "" +
            "UPDATE OWNER                                                       \n" +
            "SET NAME = ?, SEX = ?, CITY = ?, STREET = ?, ZIPCODE = ?           \n" +
            "WHERE ID = ?                                                       \n";

    private final Connection dbConnection;
    private final DogDao dogDao;

    public OwnerDaoImpl(final Connection dbConnection, final DogDao dogDao) {
        this.dbConnection = dbConnection;
        this.dogDao = dogDao;
    }

    @Override
    public Owner saveOwner(Owner toSave) {

        try {
            if (toSave.getId() == null) {
                // insert

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
                dogDao.save(dogToSave, personId);

                dbConnection.commit();
                dbConnection.setAutoCommit(true);


            } else {
                // update

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
                dogDao.save(dogToSave, toSave.getId());

                dbConnection.commit();
                dbConnection.setAutoCommit(true);

            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                dbConnection.rollback();
                dbConnection.setAutoCommit(true);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        return toSave;
    }
}
