package db;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lipez
 */
public class DBConnection {

    private static final Logger logger = Logger.getLogger(DBConnection.class.getName());

    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String HOST = "localhost:3306";
    private final String DB = "coma_introduction";
    private final String USER = "root";
    private final String PASS = "1234";

    private Connection con;

    public DBConnection() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            logger.severe("DB driver not found");
        }

        String url = String.format("jdbc:mysql://%s/%s?serverTimezone=UTC", HOST, DB);

        try {
            con = DriverManager.getConnection(url, USER, PASS);
        } catch (SQLException e) {
            logger.severe("Cannot connect to the database");
        }
    }

    public Connection getConnection() {
        return con;
    }

    public void close() {
        try {
            con.close();
        } catch (SQLException e) {
            logger.severe("Cannot close the connection");
        }
    }

    public ResultSet query(String statement) {
        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            return stmt.executeQuery(statement);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Cannot execute the query", e);
        }

        return null;
    }

    public boolean insert(String statement) {
        try (Statement stmt = con.createStatement()) {
            int affected = stmt.executeUpdate(statement);
            return affected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Cannot execute the query", e);
        }

        return false;
    }

    public boolean delete(String statement) {
        try (Statement stmt = con.createStatement()) {
            int affected = stmt.executeUpdate(statement);
            return affected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Cannot execute the query", e);
        }

        return false;
    }

    public boolean update(String statement) {
        try (Statement stmt = con.createStatement()) {
            int affected = stmt.executeUpdate(statement);
            return affected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Cannot execute the query", e);
        }

        return false;
    }

    public boolean setAutoCommit(boolean parametro) {
        try {
            con.setAutoCommit(parametro);
        } catch (SQLException sqlex) {
            logger.severe("Cannot set autocommit");
            return false;
        }
        return true;
    }

    public boolean commit() {
        try {
            con.commit();
            return true;
        } catch (SQLException sqlex) {
            logger.severe("Cannot commit");
            return false;
        }
    }

    public boolean rollbackBD() {
        try {
            con.rollback();
            return true;
        } catch (SQLException sqlex) {
            logger.severe("Error doing rollback");
            return false;
        }
    }
}
