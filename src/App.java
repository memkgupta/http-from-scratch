import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Objects;

public class App {
    public static void main(String[] args) {
        try{
            ServerSocket socket = new ServerSocket(8080);
            System.out.println("Server Started");
    while(true){
        Socket clientSocket = socket.accept();
        System.out.println("Client Connected");

        String m = "";
        BufferedReader dis = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedWriter dos = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        String message = dis.readLine();
        RequestLine requestLine = new RequestLine(message);
        System.out.println("Request Received");
        System.out.println("Method: " + requestLine.getMethod());
        System.out.println("URI: " + requestLine.getUri());
        System.out.println("Version: " + requestLine.getVersion());
        Request request = new Request(requestLine.getMethod(), requestLine.getUri());

        while ((message = dis.readLine()) != null && !message.isEmpty())
        {

            int idx = message.indexOf(":");
            if (idx != -1) {
                String key = message.substring(0, idx).trim();
                String value = message.substring(idx + 1).trim();
                Header header = new Header(key, value);
                request.getHeaders().put(header.getKey(), header.getValue());
            }

        }
        if(!request.getMethod().equals("GET") && request.getHeaders().containsKey("Content-Length") && Integer.parseInt(request.getHeaders().get("Content-Length")) >= 0)
        {
            // there is body in the request

            int contentLength = Integer.parseInt(request.getHeaders().get("Content-Length"));
            byte[] content = new byte[Integer.parseInt(request.getHeaders().get("Content-Length"))];
            for (int totalRead = 0; totalRead < contentLength; ) {
                int ch = dis.read();
                if (ch == -1) break;
                content[totalRead++] = (byte) ch;
            }

                request.setBody(new String(content));

        }
        System.out.println(request.getHeaders());
        System.out.println(request.getBody());
        System.out.println("Closing connection");

        // Close connection
        clientSocket.close();
        dis.close();
        dos.close();
    }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
