import java.io.*;

import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Objects;

public class App {
    public static void main(String[] args) {
        try{
          Server server = new Server();
          server.get("/route",(req,res)->{

              res.status(200).send("Hello World");
          });
          server.get("/file",(req,res)->{
              res.status(200).file(new File("file.mp4"));
          });
            System.out.println("Server Started");
    while(true){
        Socket clientSocket = server.accept();
        System.out.println("Client Connected");
        clientSocket.setSoTimeout(10000);

        try( BufferedReader dis = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter dos = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {


            boolean keepAlive = true;
            while(keepAlive){
                String message = dis.readLine();
                if(Objects.isNull(message)){break;}
                RequestLine requestLine = new RequestLine(message);

                Request<String> request = new Request<String>(requestLine.getMethod(), requestLine.getUri());
                Response response = new Response(clientSocket.getOutputStream(),requestLine.getVersion());

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

                    int contentLength = Integer.parseInt( request.getHeaders().get("Content-Length"));
                    byte[] content = new byte[Integer.parseInt((String) request.getHeaders().get("Content-Length"))];
                    for (int totalRead = 0; totalRead < contentLength; ) {
                        int ch = dis.read();
                        if (ch == -1) break;
                        content[totalRead++] = (byte) ch;
                    }

                    request.setBody(new String(content));

                }

                String connectionHeader = request.getHeaders().getOrDefault("Connection", "keep-alive");
                keepAlive = connectionHeader.equalsIgnoreCase("keep-alive");
               Controller controller = server.getRoutes().getOrDefault(request.getUri(),null);
               if(controller==null){
                    // return not found;
                response.throwNotFound();
                break;
               }
               controller.handleRequest(request,response);

            }

        }
        catch (SocketTimeoutException e) {
            clientSocket.close();

        }
      finally {
            clientSocket.close();
        }

    }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
