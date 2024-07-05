package app.conf;

import app.stages.AppStages;

import java.io.IOException;
import java.sql.*;

public class DbConfig {

    public DbConfig() {}
    private static final String USERNAME = "ktukeys";
    private static final String PASSWORD = "keys123";
    private static final String PATH = "jdbc:mysql://localhost:3308/key_manager";
    Connection connection;
    public Connection getConnection() {
        try {
           connection = DriverManager.getConnection(PATH, USERNAME, PASSWORD);
//            connection.setAutoCommit(false);
        }catch (SQLException e) {
            AppStages.databaseFailedStage();
        }
        return connection;
        // London Billionaire marketing Association certificate
    }

    public void commitTransaction() throws SQLException {
        connection.commit();
    }
    public void rollBack() {
        try {
            connection.rollback();
        }catch (Exception ignore){}
    }
    protected ResultSet resultSet;
    protected PreparedStatement preparedStatement;
    protected Statement statement;

}
