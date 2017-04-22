package org.miaowo.miaowo.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 用于反射的工具
 * Created by luqin on 17-4-22.
 */

public class ReflectUtil {
    private static ReflectUtil util;
    private ReflectUtil() {}
    public static ReflectUtil utils() {
        if (util == null) {
            synchronized (ReflectUtil.class) {
                if (util == null) {
                    util = new ReflectUtil();
                }
            }
        }
        return util;
    }

    /**
     * 通过反射执行方法
     * @param obj 执行方法的对象
     * @param method 方法名
     * @param args 方法接受的参数
     * @return 方法返回值
     * @throws NoSuchMethodException 找不到该方法
     * @throws InvocationTargetException 该方法抛出的异常
     */
    public Object invoke(Object obj, String method, Object[] args)
            throws NoSuchMethodException, InvocationTargetException {
        Class cls = obj.getClass();
        Class[] paramClasses = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            paramClasses[i] = args.getClass();
        }
        Method method1 = cls.getMethod(method, paramClasses);
        method1.setAccessible(true);
        try {
            return method1.invoke(obj, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过反射获取对象属性
     * @param obj 对象
     * @param property 属性名
     * @param isState 是否为私有对象
     * @throws NoSuchFieldException 找不到该属性
     * @return 对象值
     */
    public Object getProperty(Object obj, String property, boolean isState)
            throws NoSuchFieldException {

        try {
            Class cls = obj.getClass();
            Field field = cls.getField(property);
            field.setAccessible(true);
            return field.get(isState ? null : property);
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    /**
     * 通过反射获取对象属性
     * 优先获取非静态对象, 如果没有, 则查找静态对象, 还没有, 则直接返回 null
     * @param obj 对象
     * @param property 属性名
     * @return 对象值
     */
    public Object getProperty(Object obj, String property) {
        try {
            Class cls = obj.getClass();
            Field field = cls.getField(property);
            field.setAccessible(true);
            Object o = field.get(obj);
            o = o == null ? o : field.get(null);
            return o;
        } catch (Exception e) {
            return null;
        }
    }
}
