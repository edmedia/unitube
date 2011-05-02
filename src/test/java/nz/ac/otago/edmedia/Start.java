package nz.ac.otago.edmedia;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * Start Jetty server.
 *
 * @author Richard Zeng (richard.zeng@otago.ac.nz)
 *         Date: 24/09/2008
 *         Time: 15:54:13
 */
public class Start {

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        SocketConnector connector = new SocketConnector();
        // Set some timeout options to make debugging easier.
        connector.setMaxIdleTime(1000 * 60 * 60);
        connector.setSoLingerTime(-1);
        connector.setHost("localhost");
        connector.setPort(8010);
        server.setConnectors(new Connector[]{connector});

        WebAppContext bb = new WebAppContext();
        bb.setServer(server);
        bb.setContextPath("/unitube");
        bb.setWar("src/main/webapp");

        // START JMX SERVER
        // MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        // MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
        // server.getContainer().addEventListener(mBeanContainer);
        // mBeanContainer.start();

        server.addHandler(bb);

        try {
            System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
            server.start();
            while (System.in.available() == 0) {
                Thread.sleep(5000);
            }
            server.stop();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(100);
        }
    }
}