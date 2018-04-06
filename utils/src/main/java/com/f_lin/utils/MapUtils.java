/*

 */
package com.f_lin.utils;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Map常用工具类
 */
public final class MapUtils {

    public static final int EIGHT = 8;
    public static final int SIXTEEN = 16;
    public static final int THIRTY_TWO = 32;
    public static final int SIXTY_FOUR = 64;

    private MapUtils() {
    }

    /**
     * @param m1  m1
     * @param m2  m2
     * @param <K> K
     * @param <V> V
     * @return
     */
    public static <K, V> Map<K, V> merge(Map<K, V> m1, Map<K, V> m2) {
        if (m1 == null) {
            return m2;
        }
        if (m2 == null) {
            return m1;
        }
        m1.putAll(m2);
        return m1;
    }

    /**
     * 安全的获取map的值
     *
     * @param map 可以为null
     * @param key 键
     * @return map为空返回null
     */
    public static Object safeGet(Map map, Object key) {
        return map == null ? null : map.get(key);
    }

    /**
     * 安全的获取map的值
     *
     * @param map  可以为null
     * @param key  键
     * @param type 结果的类型
     * @param <T>  T
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T safeGet(Map map, Object key, Class<T> type) {
        return map == null ? null : (T) map.get(key);
    }

    /**
     * 获取一个String
     *
     * @param map          可以为null
     * @param key          键
     * @param defaultValue 默认值
     * @return
     */
    public static String getString(Map map, Object key, String defaultValue) {
        Object val = safeGet(map, key);
        return val == null ? defaultValue : String.valueOf(val);
    }

    /**
     * 获取一个String
     *
     * @param map 可以为null
     * @param key 键
     * @return
     */
    public static String getString(Map map, Object key) {
        return getString(map, key, null);
    }

    /**
     * 获取一个int值
     *
     * @param map          可以为null
     * @param key          键
     * @param defaultValue defaultValue
     * @return
     */
    public static int getInt(Map map, Object key, int defaultValue) {
        Object val = safeGet(map, key);
        try {
            if (val instanceof Integer) {
                return (int) val;
            }
            return val == null ? defaultValue : Integer.parseInt(val.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 获取一个int值
     *
     * @param map 可以为null
     * @param key 键
     * @return
     */
    public static int getInt(Map map, Object key) {
        return getInt(map, key, 0);
    }

    /**
     * 获取double值
     *
     * @param map          map
     * @param key          key
     * @param defaultValue defaultValue
     * @return
     */
    public static double getDouble(Map map, Object key, double defaultValue) {
        Object val = safeGet(map, key);
        try {
            if (val instanceof Double) {
                return (double) val;
            }
            return val == null ? defaultValue : Double.parseDouble(val.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 获取double值
     *
     * @param map map
     * @param key key
     * @return
     */
    public static double getDouble(Map map, Object key) {
        return getDouble(map, key, 0);
    }

    /**
     * 获取一个boolean值
     * <p style="color:red;">
     * <b>"true"视为true， "false或0"视为false，其他不为空字符串则为true</b>
     * </p>
     *
     * @param map          可以为null
     * @param key          键
     * @param defaultValue defaultValue
     * @return
     */
    public static boolean getBoolean(Map map, Object key, boolean defaultValue) {
        Object val = safeGet(map, key);
        if (val == null) {
            return defaultValue;
        }
        if (val instanceof Boolean) {
            return (Boolean) val;
        }

        String valString = val.toString();
        return "true".equalsIgnoreCase(valString) || !("false".equalsIgnoreCase(valString)
                || "0".equalsIgnoreCase(valString)) && !valString.isEmpty();
    }

    /**
     * 获取一个boolean值
     * <p style="color:red;">
     * <b>"true"视为true， "false或0"视为false，其他不为空字符串则为true</b>
     * </p>
     *
     * @param map 可以为null
     * @param key 键
     * @return
     */
    public static boolean getBoolean(Map map, Object key) {
        return getBoolean(map, key, false);
    }

    /**
     * @param map          map
     * @param key          key
     * @param defaultValue defaultValue
     * @return
     */
    public static long getLong(Map map, Object key, long defaultValue) {
        Object val = safeGet(map, key);
        try {
            if (val == null) {
                return defaultValue;
            }
            if (val instanceof Long) {
                return (Long) val;
            }
            return Long.parseLong(val.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * @param params params
     * @return
     */
    public static Map<String, String> switchParamMap(Map<String, String[]> params) {
        if (params == null) {
            return Collections.EMPTY_MAP;
        }
        return params.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry ->
                entry.getValue() == null || entry.getValue().length == 0 ? null : entry.getValue()[0]
        ));
    }
}
