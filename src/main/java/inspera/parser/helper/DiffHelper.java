package inspera.parser.helper;

import inspera.parser.domain.diff.MetaDiff;

import java.lang.reflect.Field;

/**
 * Created by rmang on 17-06-2018.
 */
public class DiffHelper {

    public static MetaDiff buildMetaDiff(Field field, Object beforeObj, Object afterObj) {
        MetaDiff metaDiff = new MetaDiff();
        metaDiff.setField(field.getName());
        metaDiff.setBeforeValue(beforeObj);
        metaDiff.setAfterValue(afterObj);
        return metaDiff;
    }
}
