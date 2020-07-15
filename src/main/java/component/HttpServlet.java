package component;

import service.SecurityService;

public abstract class HttpServlet extends javax.servlet.http.HttpServlet
        implements Router
{


    protected SecurityService securityService;

    /**
     * Set up service for security.
     *
     * @param securityServiceIn -
     */
    @Override
    public void setSecurityService(final SecurityService securityServiceIn) {
        this.securityService = securityServiceIn;
    }
}
