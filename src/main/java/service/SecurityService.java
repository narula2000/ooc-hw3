package service;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class SecurityService {
    private DBService dbService;

    /**
     * Create service to talk with database.
     * @throws SQLException -
     * @throws ClassNotFoundException -
     */
    public SecurityService() throws SQLException, ClassNotFoundException {
        dbService = new DBService();
    }

    /**
     * Check if user exist.
     * @param request - User request
     * @return - boolean
     * @throws SQLException -
     */
    public boolean isAuthorized(final HttpServletRequest request)
            throws SQLException {
        String username = (String) request.getSession()
                .getAttribute("username");
        return (username != null && dbService.containUser(username));
    }

    /**
     * Check if user input correct information.
     * @param username - String
     * @param password - String
     * @param request - User request
     * @return - boolean
     * @throws SQLException -
     */
    public boolean authenticate(
            final String username,
            final String password,
            final HttpServletRequest request) throws SQLException {

        boolean isMatched = dbService.authenticateUser(username, password);
        if (isMatched) {
            request.getSession().setAttribute("username", username);
            return true;
        } else {
            return false;
        }
    }
}
