package com.mk.http;

@FunctionalInterface
public interface Middleware {
    // this will return a new Decorated com.mk.http.Request which will replace original request object
   Request process(Request request,Response response);
}
