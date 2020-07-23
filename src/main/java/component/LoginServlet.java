package component;

import org.apache.commons.lang.StringUtils;
import service.SecurityService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

        private SecurityService securityService;


    /**
     * Get the jsp page for the client.
     * @param request -
     * @param response -
     * @throws ServletException -
     * @throws IOException -
     */
    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response)
                throws ServletException, IOException {
        RequestDispatcher rd = request
                .getRequestDispatcher("WEB-INF/login.jsp");
        rd.include(request, response);
    }

    /**
     * Check if user input correct information.
     * @param request -
     * @param response -
     * @throws ServletException -
     * @throws IOException -
     */
    @Override
    protected void doPost(
            final HttpServletRequest request,
            final HttpServletResponse response)
                throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (!StringUtils.isBlank(username) && !StringUtils.isBlank(password)) {
            try {
                if (securityService.authenticate(username, password, request)) {
                    response.sendRedirect("/");
                } else {
                    String error = "Wrong username or password.";
                    request.setAttribute("error", error);
                    RequestDispatcher rd = request
                            .getRequestDispatcher("WEB-INF/login.jsp");
                    rd.include(request, response);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String error = "Username or password is missing.";
            request.setAttribute("error", error);
            RequestDispatcher rd = request
                    .getRequestDispatcher("WEB-INF/login.jsp");
            rd.include(request, response);
        }
    }

    /**
     * Map the sever component to the url.
     * @return -
     */
    @Override
    public String getMapping() {
        return "/login";
    }

    /**
     * Import security service.
     * @param securityServiceIn -
     */
    @Override
    public void setSecurityService(final SecurityService securityServiceIn) {
        this.securityService = securityServiceIn;
    }

}
