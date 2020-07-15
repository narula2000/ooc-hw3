package service;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DBService {

    /**
     * Data table of the user.
     */
    enum UserTable {
        USERNAME, PASSWORD, FIRSTNAME, LASTNAME, DOB;
    }

    private final String jdbcDriverStr;
    private final String jdbcURL;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    /**
     * Connect with the database.
     * @throws SQLException -
     * @throws ClassNotFoundException -
     */
    public DBService() throws SQLException, ClassNotFoundException {
        this.jdbcDriverStr = "com.mysql.cj.jdbc.Driver";
        this.jdbcURL = "jdbc:mysql://localhost/oochw3"
                + "user=vic&password=1234";
        Class.forName(jdbcDriverStr);
        connection = DriverManager.getConnection(jdbcURL);
        statement = connection.createStatement();
        createDatabase();
    }

    /**
     * If the table doesn't exist create it for the user.
     * @throws SQLException -
     */
    public void createDatabase() throws SQLException {

        statement
                .execute("create table if not exists user_table("
                        + "username varchar(255) not null , "
                        + "password varchar(255) not null , "
                        + "firstname varchar(255) , "
                        + "lastname varchar(200), DOB date)");

        resultSet = statement
                .executeQuery("select * from oochw3webapp.user_table;");

        if (!resultSet.next()) {
            String hashed = BCrypt.hashpw("admin", BCrypt.gensalt());
            preparedStatement = connection
                    .prepareStatement("insert into oochw3webapp.user_table "
                            + "values ('admin','"
                            + hashed
                            + "','admin','admin','2020/07/17')");
            preparedStatement.execute();
        }
    }

    /**
     * Read everything in the database.
     * @throws Exception -
     */
    public void readData() throws Exception {
        try {
            resultSet = statement
                    .executeQuery("select * from oochw3webapp.user_table;");
            getResultSet(resultSet);
        } finally {
            close();
        }
    }

    /**
     * Query for the username and password.
     * @param resultSetIn - ResultSet
     * @throws Exception -
     */
    private void getResultSet(final ResultSet resultSetIn) throws Exception {
        while (resultSetIn.next()) {
            String username = resultSetIn
                    .getString(UserTable.USERNAME.toString());
            String password = resultSetIn
                    .getString(UserTable.PASSWORD.toString());
        }
    }

    /**
     * Close the connection.
     */
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * Add new user to the database.
     * @param username - String
     * @param password - String
     * @return - boolean
     * @throws SQLException -
     */
    public boolean createUser(final String username, final String password)
            throws SQLException {
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        if (!containUser(username)) {
            statement
                    .execute("insert into user_table(username, password) "
                            + "values ( '" + username + "', '" + hashed + "')");
            return true;
        }
        return false;
    }

    /**
     * Check if the user is in the database.
     * @param username - String
     * @return - boolean
     * @throws SQLException -
     */
    public boolean containUser(final String username) throws  SQLException {
        ArrayList<String> nameList = getAllUser();
        return nameList.contains(username);
    }

    /**
     * Delete user from the database.
     * @param username - String
     * @return - boolean
     * @throws SQLException -
     */
    public boolean deleteUser(final String username) throws SQLException {
        if (containUser(username)) {
            statement.execute("delete from user_table "
                    + "where username ='" + username + "';");
            return true;
        }
        return false;
    }

    /**
     * Check if user login correctly.
     * @param username - String
     * @param password - String
     * @return - boolean
     * @throws SQLException -
     */
    public boolean authenticateUser(
            final String username, final String password)
            throws SQLException {
        if (containUser(username)) {
            resultSet = statement.executeQuery("select * from user_table "
                    + "where(username = '" + username + "')");
            if (resultSet.next()) {
                return BCrypt.checkpw(password, resultSet
                        .getString(UserTable.PASSWORD.toString()));
            }
        }
        return false;
    }

    /**
     * Return list of all user.
     * @return - ArrayList
     * @throws SQLException -
     */
    public ArrayList<String> getAllUser() throws SQLException {
        resultSet = statement.executeQuery("select  * from user_table");
        ArrayList<String> nameList = new ArrayList<>();
        while (resultSet.next()) {
            nameList.add(resultSet.getString(UserTable.USERNAME.toString()));
        }
        return nameList;
    }

    /**
     * Get user information.
     * @param username - String
     * @return - User object
     * @throws SQLException -
     * @throws ClassNotFoundException -
     */
    public UserObj getUser(final String username)
            throws SQLException, ClassNotFoundException {

        UserObj user = new UserObj(username);
        resultSet = statement
                .executeQuery("select firstname "
                        + "from user_table where username = '"
                        +  username + "';");
        resultSet.next();

        String firstName = resultSet.getString(UserTable.FIRSTNAME.toString());
        user.setFirstName(firstName);
        resultSet = statement
                .executeQuery("select lastname "
                + "from user_table where username = '" +  username + "';");
        resultSet.next();
        String lastName = resultSet.getString(UserTable.LASTNAME.toString());
        user.setLastName(lastName);
        resultSet = statement
                .executeQuery("select dob "
                        + "from user_table where username = '"
                        +  username + "';");
        resultSet.next();
        String dob = resultSet.getString(UserTable.DOB.toString());
        user.setDob(dob);

        return user;
    }

    /**
     * Update user username.
     * @param newUsername - String
     * @param oldUsername - String
     * @throws SQLException -
     */
    public void updateUsername(
            final String newUsername, final String oldUsername)
            throws SQLException {
        statement
                .execute("update user_table set username = '"
                        + newUsername
                        + "' where username = '"
                        + oldUsername + "';");
    }

    /**
     * Update user password.
     * @param newPassword - String
     * @param username - String
     * @throws SQLException -
     */
    public void updatePassword(final String newPassword, final String username)
            throws SQLException {
        String hased = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        statement
                .execute("update user_table set password = '"
                        + hased
                        + "' where username = '"
                        + username + "';");
    }

    /**
     * Update user first name.
     * @param newFirstName - String
     * @param username - String
     * @throws SQLException -
     */
    public void updateFirstName(
            final String newFirstName, final String username)
            throws SQLException {
        statement
                .execute("update user_table set firstname = '"
                        + newFirstName
                        + "' where username = '"
                        + username + "';");
    }

    /**
     * Update user last name.
     * @param newLastName - String
     * @param username -String
     * @throws SQLException -
     */
    public void updateLastName(
            final String newLastName, final String username)
            throws SQLException {
        statement
                .execute("update user_table set lastname = '"
                        + newLastName
                        + "' where username = '"
                        + username + "';");
    }

    /**
     * Update user date of birth.
     * @param newDob - String
     * @param username - String
     * @throws SQLException -
     */
    public void updateDob(final String newDob, final String username)
            throws SQLException {
        statement
                .execute("update user_table set DOB = '"
                        + newDob
                        + "' where username = '"
                        + username + "';");
    }
}
