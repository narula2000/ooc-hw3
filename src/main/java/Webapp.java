import service.DBService;
import org.apache.catalina.startup.Tomcat;

import java.io.File;


public final class Webapp {
    /**
     * Runs the webapp.
     * @param args - Empty
     */
    public static void main(final String[] args) throws Exception {

        final int port = 80;
        // Create webapp(dir) if it doesnt exist.
        File dirBase = new File("src/main/webapp/");
        dirBase.mkdir();
        // Configure tomcat port and initiate it.
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);

        // Connect with the data base and read it's data.
        DBService dbService = new DBService();
        dbService.readData();

    }

    /**
     * Empty constructor, to unable class instantiation.
     */
    private Webapp() {
    }
}
