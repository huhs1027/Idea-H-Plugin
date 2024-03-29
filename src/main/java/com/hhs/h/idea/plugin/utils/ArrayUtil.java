package com.hhs.h.idea.plugin.utils;

import java.util.Collection;

/**
 * @author hutao.hhs
 * @since 2021/4/6 22:37
 */
public class ArrayUtil {

    /**
     * 判断不为空
     *
     * @param collection 集合
     * @return ture 空 false 不为空
     */
    public static <E> boolean isEmpty(Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断不为空
     *
     * @param e 集合
     * @return ture 空 false 不为空
     */
    public static <E> boolean isEmpty(E... e) {
        return e == null || e.length == 0;
    }

    /**
     * 判断数组舒服包含元素
     *
     * @param e     元素
     * @param array 数组
     * @return true包含 false不包含
     */
    public static <E> boolean contains(E[] array, E e) {
        if (isEmpty(array)) {
            return false;
        }
        for (E e1 : array) {
            if (e1.equals(e)) {
                return true;
            }
        }
        return false;
    }
}
