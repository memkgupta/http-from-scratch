package com.mk.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Server {
    private final ServerSocket serverSocket;
    private final HashMap<String,RequestHandler> routes;
    private final ArrayList<Middleware> globalMiddlewares;

    public Server() throws IOException {
        serverSocket = new ServerSocket(8080);
        routes = new HashMap<>();
        globalMiddlewares = new ArrayList<>();

    }
    public void start(RouteTree appRouter) throws IOException {
        while(true){
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client Connected");
            clientSocket.setSoTimeout(10000);

            try(BufferedReader dis = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {


                boolean keepAlive = true;
                while(keepAlive){
                    String message = dis.readLine();
                    if(Objects.isNull(message)){break;}
                    RequestLine requestLine = new RequestLine(message);

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
                    Response response = new Response(clientSocket.getOutputStream(),requestLine.getVersion(),request.getHeaders().get("Content-Type"));

                    if(!request.getMethod().equals("GET") && request.getHeaders().containsKey("Content-Length") && Integer.parseInt(request.getHeaders().get("Content-Length")) >= 0)
                    {


                        int contentLength = Integer.parseInt( request.getHeaders().get("Content-Length"));
                        byte[] content = new byte[Integer.parseInt((String) request.getHeaders().get("Content-Length"))];
                        for (int totalRead = 0; totalRead < contentLength; ) {
                            int ch = dis.read();
                            if (ch == -1) break;
                            content[totalRead++] = (byte) ch;
                        }
                        if(content.length == contentLength)
                        {
                            request.setRawBody(content);

                        }

                    }

                    String connectionHeader = request.getHeaders().getOrDefault("Connection", "keep-alive");
                    keepAlive = connectionHeader.equalsIgnoreCase("keep-alive");
                    for(Middleware middleware : globalMiddlewares){
                        request = middleware.process(request,response);

                    }
                    if(request!=null){
                        RequestHandler handler = appRouter.getMatchingRoute(HttpMethod.valueOf(request.getMethod()),request.getUri().split("/"));
                        if(handler==null){
                            System.out.printf("No route found for %s\n",request.getMethod());
                            // return not found;
                            response.throwNotFound();
                            break;
                        }

                        handler.handle(request,response);
                    }


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

}
