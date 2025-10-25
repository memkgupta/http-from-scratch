package com.mk.http;

public class MiddlewareExtra {
    private Object value;
    private Class clazz;

    public MiddlewareExtra(Object value, Class clazz) {
        this.value = value;
        this.clazz = clazz;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
