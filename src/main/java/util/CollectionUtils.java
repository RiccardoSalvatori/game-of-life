package util;

import java.util.Arrays;
import java.util.HashSet;

public class CollectionUtils {
    @SafeVarargs
    public static <T> java.util.List<T> listOf(T... elems) {
        return Arrays.asList(elems);
    }

    @SafeVarargs
    public static <T> java.util.Set<T> setOf(T... elems) {
        return new HashSet<>(Arrays.asList(elems));
    }
}
