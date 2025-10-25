package com.mk.http;

public interface RequestBody {
    public Object getBody(Class clazz);
    public Object setBody( Object body);
}
