package inspera.parser.builder;

import inspera.parser.domain.diff.MetaDiff;

import java.lang.reflect.Field;

/**
 * Created by rmang on 17-06-2018.
 */
public interface MetaDiffBuilder<T> {

    MetaDiff buildDiff(Field field, T beforeObj, T afterObj);

}
