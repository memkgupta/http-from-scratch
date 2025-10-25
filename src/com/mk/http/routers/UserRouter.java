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
        subRouter.post("create",List.of(
                ((request, response) -> {
                    request.add("extra","This is extra",String.class);
                    return request;
                }),
                ((request, response) -> {
                    response.status(200).send("Hola amigo , user created "+request.get("extra",String.class));
                    return request;
                })
                )
        );
        return subRouter;
    }
}



