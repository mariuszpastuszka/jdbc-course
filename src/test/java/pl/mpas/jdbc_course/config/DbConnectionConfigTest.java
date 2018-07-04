package pl.mpas.jdbc_course.config;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class DbConnectionConfigTest {

    @Test
    public void checkConnection() {

        try (Connection connection = DbConnectionConfig.getInstance().getConnection()) {
            Assert.assertNotNull("Couldn't get connection to db!", connection);
        } catch (SQLException e) {
            Assert.fail("Something wrong happened during obtaining db connection!");
            e.printStackTrace();
        }
    }
}
