package com.mk.http.routers;

import com.mk.http.RequestHandler;
import com.mk.http.SubRouter;
import com.mk.http.controllers.UserRequestController;

import java.util.List;

public class UserRouter{


    public static SubRouter getSubRouter(){
        SubRouter subRouter = new SubRouter();
        subRouter.get("user", List.of(
                ((request, response) -> {
                    response.send("Hello from userRouter");
                    return request;
                })
        ));
        return subRouter;
    }
}



