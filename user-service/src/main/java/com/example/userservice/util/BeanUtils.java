package com.example.userservice.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BeanUtils {
    public BeanUtils() {
    }

    public static <T> T copy(Object c, Class<T> c2) {
        if (c2 == null) {
            return null;
        } else {
            Object c2Obj;
            try {
                c2Obj = c2.newInstance();
            } catch (InstantiationException var4) {
                var4.printStackTrace();
                return null;
            } catch (IllegalAccessException var5) {
                var5.printStackTrace();
                return null;
            }

            if (c == null) {
                return (T) c2Obj;
            } else {
                copy(c2Obj, c);
                return (T) c2Obj;
            }
        }
    }
    /**
    * @Description:  c目标对象，c2源对象
    * @Param: [c, c2]
    * @Date: 2019/11/14
    */
    public static void copy(Object c, Object c2) {
        if (c != null && c2 != null) {
            List<Field> fieldList = ReflectionUtils.getDeclaredFields(c);
            Iterator var3 = fieldList.iterator();

            while(var3.hasNext()) {
                Field field = (Field)var3.next();
                Object value = ReflectionUtils.getFieldValue(c2, field.getName());
                if (value != null) {
                    ReflectionUtils.setFieldValue(c, field.getName(), value);
                }
            }

        }
    }

    public static <T> T copyAll(Class<T> c2, Object... c) {
        try {
            Object object2 = Class.forName(c2.getName()).newInstance();
            List<Field> fieldList = ReflectionUtils.getDeclaredFields(object2);

            for(int x = 0; x < c.length; ++x) {
                c[x] = c[x] == null ? (c[x] = c2.newInstance()) : c[x];
                Iterator var5 = fieldList.iterator();

                while(var5.hasNext()) {
                    Field field = (Field)var5.next();
                    Object value = ReflectionUtils.getFieldValue(c[x], field.getName());
                    if (value != null) {
                        ReflectionUtils.setFieldValue(object2, field.getName(), value);
                    }
                }
            }

            return (T) object2;
        } catch (IllegalAccessException var8) {
            var8.printStackTrace();
        } catch (ClassNotFoundException var9) {
            var9.printStackTrace();
        } catch (InstantiationException var10) {
            var10.printStackTrace();
        } catch (SecurityException var11) {
            var11.printStackTrace();
        } catch (IllegalArgumentException var12) {
            var12.printStackTrace();
        }

        return null;
    }

    public static List<?> getArrayInstance(List<?> list, Class<?> c) {
        ArrayList resultArray = new ArrayList();

        try {
            Iterator var4 = list.iterator();

            while(var4.hasNext()) {
                Object obj = var4.next();
                Object resultObject = copy(obj, c);
                resultArray.add(resultObject);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return resultArray;
    }
}
