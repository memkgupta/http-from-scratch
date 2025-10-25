package com.mk.http;

import com.mk.http.routers.UserRouter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        App app = new App();
        try{
            app.get("user", List.of((request, response) -> {
                System.out.printf("%s %s\n",request.getMethod(),"Hello");
                response.status(201);
                response.send("Hello World");
                return request;
            }));
            app.post("new_user",List.of(((request, response) -> {
                response.status(201);
                response.send("User created");
                return request;
            })));
            app.use("auth",UserRouter.getSubRouter());
            app.start();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
