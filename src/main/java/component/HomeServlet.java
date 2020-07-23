package component;

import service.DBService;
import service.SecurityService;
import service.UserObj;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class HomeServlet extends HttpServlet {


    private SecurityService securityService;
    private DBService dbService;

    /**
     * Create database.
     */
    public HomeServlet() throws SQLException, ClassNotFoundException {
        this.dbService = new DBService();
    }

    /**
     * Get all user from the table.
     * @param request - Client request.
     * @param response - Sever response.
     * @throws SQLException -
     * @throws IOException -
     * @throws ServletException -
     */
    public void refreshUser(final HttpServletRequest request
                            , final HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        ArrayList<String> userList = dbService.getAllUser();

        request.setAttribute("userList", userList);
        RequestDispatcher requestDispatcher = request
                .getRequestDispatcher("WEB-INF/home.jsp");
        requestDispatcher.include(request, response);
    }

    /**
     * Map the route.
     * @return - String.
     */
    @Override
    public String getMapping() {
        return "/index.jsp";
    }

    /**
     * Set up service for security anywhere.
     *
     * @param securityServiceIn -
     */
    @Override
    public void setSecurityService(final SecurityService securityServiceIn) {
        this.securityService = securityServiceIn;
    }

    /**
     * Check if request is authorised if it is then allow them to home page.
     * @param req - Client request
     * @param resp - Sever response
     * @throws ServletException -
     * @throws IOException -
     */
    @Override
    protected void doGet(final HttpServletRequest req
                        , final HttpServletResponse resp)
            throws ServletException, IOException {

        boolean authorized = false;
        try {
            authorized = securityService.isAuthorized(req);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (authorized) {
            String username = (String) req
                    .getSession().getAttribute("username");
            try {
                UserObj user = dbService.getUser(username);
                req.getSession().setAttribute("user", user);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                refreshUser(req, resp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            resp.sendRedirect("/login");
        }
    }

    /**
     * Register, remove , edit users.
     * @param req - Client request.
     * @param resp - Sever response.
     * @throws ServletException -
     * @throws IOException -
     */
    @Override
    protected void doPost(final HttpServletRequest req
                        , final HttpServletResponse resp)
            throws ServletException, IOException {
        // Add new user
        if (req.getParameter("add_user") != null) {
            String newUsername = req.getParameter("adding_username");
            try {
                if (dbService.containUser(newUsername)) {
                    String error = "Username exist in database";
                    req.setAttribute("adding_error", error);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                String error = e.getMessage();
                req.setAttribute("adding_error", error);
            }
            // Add new password
            String newPassword = req.getParameter("adding_password");
            String confirmPassword = req.getParameter("confirm_password");
            if (newPassword.compareTo(confirmPassword) == 0) {
                try {
                    dbService.createUser(newUsername, newPassword);
                    refreshUser(req, resp);
                } catch (SQLException e) {
                    e.printStackTrace();
                    String error = e.getMessage();
                    req.setAttribute("adding_error", error);
                }
            } else {
                String error = "Password doesn't match";
                req.setAttribute("adding_error", error);
            }
        } else
            // Remove user
            if (req.getParameter("removing_user") != null) {
            String user = req.getParameter("user_to_use");
            try {
                dbService.deleteUser(user);
                refreshUser(req, resp);
            } catch (SQLException e) {
                e.printStackTrace();
                String error = e.getMessage();
                req.setAttribute("removing_error", error);
            }
        } else
            // Edit user information
            if (req.getParameter("do_edit") != null) {
            String user = req.getParameter("user_to_use");
            req.getSession().setAttribute("editing_user", user);
            resp.sendRedirect("/edit");
        } else if (req.getParameter("logout") != null) {
            req.getSession().invalidate();
            resp.sendRedirect("/login");
        }
    }
}
