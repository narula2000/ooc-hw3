package component;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import service.SecurityService;

import java.util.ArrayList;
import java.util.List;

public class ServletRouter {

    /**
     * Keep track of route to their classes.
     */
    private static final List<Class<? extends HttpServlet>> ROUTER =
            new ArrayList<>();

    private SecurityService securityService;

    static {
        ROUTER.add(HomeServlet.class);
        ROUTER.add(LoginServlet.class);
        ROUTER.add(EditServlet.class);
    }

    /**
     * Allow the class to have security.
     * @param securityServiceIn -
     */
    public void setSecurityService(final SecurityService securityServiceIn) {
        this.securityService = securityServiceIn;
    }

    /**
     * Initialise the route to each classes and it's jsp.
     * @param ctx - Page context.
     */
    public void init(final Context ctx) {
        for (Class<? extends Route> jspClass : ROUTER) {
            try {
                Route servlet = jspClass.newInstance();
                servlet.setSecurityService(securityService);
                String servletName = servlet.getClass().getSimpleName();
                Tomcat.addServlet(ctx, servletName, (HttpServlet) servlet);
                ctx.addServletMappingDecoded(servlet.getMapping(), servletName);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }
}
