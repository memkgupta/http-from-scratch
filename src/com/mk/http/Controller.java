package com.mk.http;

import java.io.IOException;

@FunctionalInterface
public interface Controller {
    public void handleRequest(Request request , Response response) throws IOException;
}
