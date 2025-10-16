package com.mk.http.routers;

import com.mk.http.RequestHandler;
import com.mk.http.Router;
import com.mk.http.controllers.UserRequestController;

public class UserRouter{
        public static Router getRouter()
        {
            Router router = new Router();
            router.get("/user",new RequestHandler(UserRequestController.getUser()));
            return router;
        }

    }



