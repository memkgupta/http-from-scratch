package com.mk.http;

import java.io.IOException;

public class App extends Router{
    private  Server server;
   public App()
   {
       try{
           this.server = new Server();

       }
       catch(Exception e){
           e.printStackTrace();
       }
   }
 public void start() throws IOException {
     server.start(this.getRouteTree());
 }

}
