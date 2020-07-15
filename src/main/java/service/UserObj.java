package service;

import java.sql.SQLException;

public class UserObj {

    private String username;
    private String firstName;
    private String lastName;
    private String dob;

    private DBService dbService;

    /**
     * Assign username.
     * @param usernameIn - String
     * @throws SQLException -
     * @throws ClassNotFoundException -
     */
    public UserObj(final String usernameIn)
            throws SQLException, ClassNotFoundException {
        this.username = usernameIn;
        this.dbService = new DBService();
    }

    /**
     * Set user date of birth.
     * @param dobIn - String
     */
    public void setDob(final String dobIn) {
        this.dob = dobIn;
    }

    /**
     * Set user first name.
     * @param firstNameIn - String
     */
    public void setFirstName(final String firstNameIn) {
        this.firstName = firstNameIn;
    }

    /**
     * Set user last name.
     * @param lastNameIn - String
     */
    public void setLastName(final String lastNameIn) {
        this.lastName = lastNameIn;
    }

    /**
     * Get user username.
     * @return - String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get user first name.
     * @return - String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get user last name.
     * @return - String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get user date of birth.
     * @return - String
     */
    public String getDob() {
        return dob;
    }
}
