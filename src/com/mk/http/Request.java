package com.mk.http;

import java.util.HashMap;

public  class Request {
    private String method;
    private String uri;
    private HashMap<String,String> headers;
    private byte[] raw_body;
    private RequestBody requestBody;
    private Class<?> clazz;
    private HashMap<String,String> query;
    private HashMap<String,String> cookies;
    private HashMap<String,String> params;
    private HashMap<String,MiddlewareExtra> extras;
    public Request(String method, String uri) {
        this.method = method;
        this.uri = uri;
        this.extras = new HashMap<>();
        this.headers = new HashMap<>();
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
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

    public <T> T getBody(Class<T> clazz) {
        return requestBody.getBody(clazz);
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public <T> void add(String key, T value , Class<T> clazz)
    {
        extras.put(key,new MiddlewareExtra(value,clazz));
    }
    public <T> T get(String key, Class<T> clazz)
    {
        MiddlewareExtra extra =  extras.get(key);
        if(extra.getValue().getClass().equals(clazz))
        {
            return (T) extra.getValue();
        }
        throw new RuntimeException("Illegal arguments");
    }
    public void setRawBody(byte[] raw_body) {
        this.raw_body = raw_body;
    }
    public byte[] getRawBody() {
        return raw_body;
    }
}
