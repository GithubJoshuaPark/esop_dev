package com.soro.esop.utils;

import java.io.*;
import static java.time.LocalDateTime.now;

/**
 * packageName : com.soro.esop.utils
 * fileName    : EsopUtil
 * author      : soromiso
 * date        : 9/28/24
 * description :
 * ===========================================================
 * DATE                 AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/28/24             soromiso             new
 */
public class EsopUtil{

    public static boolean isBlank(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotBlank(String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isEmpty(Object obj) {
        if (obj instanceof String) {
            return isBlank((String) obj);
        } else {
            return isNull(obj);
        }
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * copy the object into a new instance and return it
     * Usage: copy(obj)
     *     : ex: copy(new ArrayList<>(Arrays.asList(1, 2, 3)))
     * @param obj
     * @return
     * @param <T>
     */
    public static <T> T copy(T obj) {
        return obj;
    }

    /**
     * deep copy the object into a new instance and return it
     * Usage: deepCopy(obj)
     *      : ex: deepCopy(new ArrayList<>(Arrays.asList(1, 2, 3)))
     *      : ex: deepCopy(new HashMap<>(Map.of("key1", "value1", "key2", "value2")))
     *      : ex: deepCopy(new HashSet<>(Set.of("a", "b", "c")))
     * @param obj
     * @return: copied
     * @param <T>
     */
    public static <T extends Serializable> T deepCopy(T obj) {
        if (obj == null) {
            return null;
        }

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            out.close();

            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
            T copied = (T) in.readObject();
            in.close();

            return copied;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Get the current date and time
     * @return
     */
    public static String getCurrentDateTime() {
        return now().toString();
    }
}
