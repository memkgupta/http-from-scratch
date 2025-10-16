import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private ServerSocket serverSocket;
    private HashMap<String,Controller> routes;
    public Server() throws IOException {
        serverSocket = new ServerSocket(8080);
        routes = new HashMap<>();
    }
    public void get(String route , Controller controller) {
        routes.put(route, controller);
    }
    public Socket accept() throws IOException {
        return serverSocket.accept();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public HashMap<String, Controller> getRoutes() {
        return routes;
    }

    public void setRoutes(HashMap<String, Controller> routes) {
        this.routes = routes;
    }
}
