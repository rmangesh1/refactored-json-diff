package inspera.parser.builder;

import inspera.parser.domain.diff.MetaDiff;

import java.lang.reflect.Field;

public interface MetaDiffBuilder<T> {

    MetaDiff buildDiff(Field field, T beforeObj, T afterObj);

}
