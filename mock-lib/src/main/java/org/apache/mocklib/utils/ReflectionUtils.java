package org.apache.mocklib.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Map;

public class ReflectionUtils {
    private static final Map<String, Class<?>> referenceType;
    private static final String PREFIX_SET = "set";
    private static final String PREFIX_IS = "is";

    private ReflectionUtils() {
    }

    static {
        referenceType = Map.of("int", Integer.class,
                "boolean", Boolean.class,
                "byte", Byte.class,
                "char", Character.class,
                "double", Double.class,
                "float", Float.class,
                "long", Long.class,
                "short", Short.class);
    }

    public static Class<?> findReferenceType(String type, Class<?> clazz) {
        return referenceType.getOrDefault(type, clazz);
    }

    public static void setValue(Object object, Field field, Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (!isFinalOrStatic(field)) {
            var fieldName = removeBooleanPrefix(field.getName());
            var methodName = PREFIX_SET + StringUtils.capitalize(fieldName);
            var setter = object.getClass().getMethod(methodName, field.getType());
            setter.invoke(object, value);
        }
    }

    private static String removeBooleanPrefix(String fieldName) {
        if (fieldName.startsWith(PREFIX_IS)) {
            return fieldName.replaceFirst(PREFIX_IS, "");
        }

        return fieldName;
    }

    public static boolean isFinalOrStatic(Field field) {
        return Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers());
    }
}
