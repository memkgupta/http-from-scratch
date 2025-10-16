package com.mk.http;

import com.mk.http.controllers.UserRequestController;
import com.mk.http.routers.UserRouter;

import java.io.*;

public class App {
    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.use("/",UserRouter.getRouter());
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
