package com.mk.http;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.LinkedHashMap;

public class JSONRequestBody implements RequestBody{
   LinkedHashMap<String, Object> body;

    @Override
    public <T> T getBody(Class<T> clazz) {
        if(body==null){
            return null;
        }
        try {
            T instance=getBody(clazz,body);
            return instance;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error parsing the body");
        }


    }

    @Override
    public  Object setBody(LinkedHashMap<String, Object> body) {
        this.body = body;

        return body;
    }

    @SuppressWarnings("unchecked")
    public  <T> T getBody(Class<T> clazz, LinkedHashMap<String, Object> body) {
        if (body == null) return null;

        try {
            // Create new instance using public no-arg constructor
            T instance = clazz.getDeclaredConstructor().newInstance();

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);  // safe for your own classes
                String fieldName = field.getName();

                if (!body.containsKey(fieldName)) continue;

                Object value = body.get(fieldName);
                Class<?> fieldType = field.getType();

                // Nested object (recursive)
                if (value instanceof LinkedHashMap && !fieldType.isPrimitive() && !fieldType.equals(String.class)) {
                    Object nested = getBody(fieldType, (LinkedHashMap<String, Object>) value);
                    field.set(instance, nested);
                }
                // String
                else if (fieldType.equals(String.class) && value instanceof String) {
                    field.set(instance, value);
                }
                // Primitive types
                else if (fieldType.isPrimitive() && value instanceof Number) {
                    setPrimitiveField(field, instance, (Number) value);
                }
                // Boxed numbers
                else if (Number.class.isAssignableFrom(fieldType) && value instanceof Number) {
                    setBoxedNumberField(field, instance, (Number) value);
                }
                // Boolean
                else if (fieldType.equals(boolean.class) && value instanceof Boolean) {
                    field.setBoolean(instance, (Boolean) value);
                } else if (fieldType.equals(Boolean.class) && value instanceof Boolean) {
                    field.set(instance, value);
                }
                // Generic object match
                else if (fieldType.isInstance(value)) {
                    field.set(instance, value);
                }
                // Collection (List/Set)
                else if (Collection.class.isAssignableFrom(fieldType) && value instanceof Collection) {
                    field.set(instance, value);
                }
                // Unknown type
                else {
                    throw new RuntimeException("Cannot map field '" + fieldName + "' with value type: " +
                            (value != null ? value.getClass() : "null"));
                }
            }

            return instance;

        } catch (Exception e) {
            throw new RuntimeException("Error creating instance of " + clazz.getName(), e);
        }
    }

    private static void setPrimitiveField(Field field, Object instance, Number value) throws IllegalAccessException {
        Class<?> type = field.getType();
        if (type.equals(int.class)) field.setInt(instance, value.intValue());
        else if (type.equals(long.class)) field.setLong(instance, value.longValue());
        else if (type.equals(double.class)) field.setDouble(instance, value.doubleValue());
        else if (type.equals(float.class)) field.setFloat(instance, value.floatValue());
        else if (type.equals(short.class)) field.setShort(instance, value.shortValue());
        else if (type.equals(byte.class)) field.setByte(instance, value.byteValue());
        else if (type.equals(char.class)) field.setChar(instance, (char) value.intValue());
    }

    private static void setBoxedNumberField(Field field, Object instance, Number value) throws IllegalAccessException {
        Class<?> type = field.getType();
        if (type.equals(Integer.class)) field.set(instance, value.intValue());
        else if (type.equals(Long.class)) field.set(instance, value.longValue());
        else if (type.equals(Double.class)) field.set(instance, value.doubleValue());
        else if (type.equals(Float.class)) field.set(instance, value.floatValue());
        else if (type.equals(Short.class)) field.set(instance, value.shortValue());
        else if (type.equals(Byte.class)) field.set(instance, value.byteValue());
        else if (type.equals(Character.class)) field.set(instance, (char) value.intValue());
    }
}
