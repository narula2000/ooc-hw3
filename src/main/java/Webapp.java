import component.ServletRouter;
import org.apache.catalina.Context;
import service.DBService;
import org.apache.catalina.startup.Tomcat;
import service.SecurityService;

import java.io.File;


public final class Webapp {
    /**
     * Runs the webapp.
     * @param args - Empty
     */
    public static void main(String[] args) throws Exception {


        final int port = 80;
        // FInd base dir base of web app
        File docBase = new File("src/main/webapp/");
        docBase.mkdirs();

        // Set port for sever
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);

        // Create DB
        DBService dbService = new DBService();
        dbService.readData();

        // Set up security
        SecurityService securityService = new SecurityService();
        ServletRouter servletRouter = new ServletRouter();
        servletRouter.setSecurityService(securityService);

        // Initialise router.
        Context ctx;
        ctx = tomcat.addWebapp("", docBase.getAbsolutePath());
        servletRouter.init(ctx);

        // Start sever.
        tomcat.start();
        tomcat.getServer().await();
    }

    /**
     * Empty constructor, to disable class instantiation.
     */
    private Webapp() {
    }
}
