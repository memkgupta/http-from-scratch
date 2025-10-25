package com.mk.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public
class RouteNode
{
    HashMap<HttpMethod,RequestContext> methods;
    List<Middleware> middlewares;
    String key;
    HashMap<String,RouteNode> childrens;
    RouteNode(String key)
    {
        this.childrens = new HashMap<>();
        this.key = key;
        this.methods = new HashMap<>();
        this.middlewares = new ArrayList<>();
    }

}