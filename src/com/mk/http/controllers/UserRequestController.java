package com.mk.http.controllers;

import com.mk.http.Controller;

public class UserRequestController {
    public static Controller getUser()
    {
        return ((request, response) -> {
            response.send("Hello User");
        });
    }
}
