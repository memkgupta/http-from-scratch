package com.mk.http;

import java.util.HashMap;

public
class RouteNode
{
    RequestHandler requestHandler;
    String key;
    HashMap<String,RouteNode> childrens;
    RouteNode(String key)
    {
        this.childrens = new HashMap<>();
        this.key = key;
    }

}