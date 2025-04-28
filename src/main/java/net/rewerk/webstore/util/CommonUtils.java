package net.rewerk.webstore.util;
import java.util.*;

public abstract class CommonUtils {
    public static <T> List<T> newItemsInList(List<T> oldList, List<T> newList) {
        return newList.stream()
                .filter(i -> !oldList.contains(i))
                .toList();
    }

    public static <T> List<T> lostItemsInList(List<T> oldList, List<T> newList) {
        return oldList.stream()
                .filter(i -> !newList.contains(i))
                .toList();
    }
}
