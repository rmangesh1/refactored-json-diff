package inspera.parser.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrana on 19.06.2018.
 */
public class ListUtils {

    public static <T> List<T> removeAll(List<T> list, List<T> remove) {
        List<T> listCopy = new ArrayList<>(list);
        listCopy.removeAll(remove);
        return listCopy;
    }

    public static <T> List<T> retainAll(List<T> list, List<T> remove) {
        List<T> listCopy = new ArrayList<>(list);
        listCopy.retainAll(remove);
        return listCopy;
    }
}
