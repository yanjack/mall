package com.example.userservice.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {
    public ReflectionUtils() {
    }

    public static Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        Class clazz = object.getClass();

        while(clazz != Object.class) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception var6) {
                clazz = clazz.getSuperclass();
            }
        }

        return null;
    }

    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
        Method method = getDeclaredMethod(object, methodName, parameterTypes);
        if (method == null) {
            return null;
        } else {
            method.setAccessible(true);

            try {
                if (null != method) {
                    return method.invoke(object, parameters);
                }
            } catch (IllegalArgumentException var6) {
                var6.printStackTrace();
            } catch (IllegalAccessException var7) {
                var7.printStackTrace();
            } catch (InvocationTargetException var8) {
                var8.printStackTrace();
            }

            return null;
        }
    }

    public static Field getDeclaredField(Object object, String fieldName) {
        Field field = null;
        Class clazz = object.getClass();

        while(clazz != Object.class) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception var5) {
                clazz = clazz.getSuperclass();
            }
        }

        return null;
    }

    public static List<Field> getDeclaredFields(Object object) {
        List<Field> fieldList = new ArrayList();

        for(Class clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                fieldList.addAll(new ArrayList(Arrays.asList(clazz.getDeclaredFields())));
            } catch (Exception var4) {
            }
        }

        return fieldList;
    }

    public static void setFieldValue(Object object, String fieldName, Object value) {
        Field field = getDeclaredField(object, fieldName);
        if (field != null) {
            field.setAccessible(true);

            try {
                if (field.getType().isPrimitive()) {
                    if (field.getType().equals(value.getClass())) {
                        field.set(object, value);
                    }
                } else if (field.getType().isAssignableFrom(value.getClass())) {
                    field.set(object, value);
                }
            } catch (IllegalArgumentException var5) {
                var5.printStackTrace();
            } catch (IllegalAccessException var6) {
                var6.printStackTrace();
            }

        }
    }

    public static Object getFieldValue(Object object, String fieldName) {
        Field field = getDeclaredField(object, fieldName);
        if (field == null) {
            return null;
        } else {
            field.setAccessible(true);

            try {
                return field.get(object);
            } catch (Exception var4) {
                var4.printStackTrace();
                return null;
            }
        }
    }
}
