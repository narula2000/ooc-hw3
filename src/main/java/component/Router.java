package component;

import service.SecurityService;

public interface Router {

    /**
     * Get map of the route,
     * @return - String.
     */
    String getMapping();

    /**
     * Set up service for security.
     * @param securityServiceIn -
     */
    void setSecurityService(SecurityService securityServiceIn);
}
