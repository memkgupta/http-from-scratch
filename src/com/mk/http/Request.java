package com.mk.http;

import java.util.HashMap;

public  class Request {
    private String method;
    private String uri;
    private HashMap<String,String> headers;
    private RequestBody body;
    private HashMap<String,String> query;
    private HashMap<String,String> cookies;
    private HashMap<String,String> params;
    private HashMap<String,MiddlewareExtra> extras;
    public Request(String method, String uri) {
        this.method = method;
        this.uri = uri;
        this.body = new RequestBody() {
            @Override
            public Object getBody(Class clazz) {
                return null;
            }

            @Override
            public Object setBody(Object body) {
                return null;
            }
        };
        this.headers = new HashMap<>();
    }

    public HashMap<String, String> getQuery() {
        return query;
    }

    public void setQuery(HashMap<String, String> query) {
        this.query = query;
    }

    public HashMap<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(HashMap<String, String> cookies) {
        this.cookies = cookies;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public RequestBody getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body.setBody(body);
    }

    public <T> void add(String key, T value , Class<T> clazz)
    {
        extras.put(key,new MiddlewareExtra(value,clazz));
    }
}
