package com.mk.http;

import java.util.LinkedHashMap;

public interface RequestBody {
    public <T> T getBody(Class<T> clazz);
    public  Object setBody(LinkedHashMap<String, Object> body);
}
