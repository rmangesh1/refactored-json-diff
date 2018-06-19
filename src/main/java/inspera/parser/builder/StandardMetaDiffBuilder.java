package inspera.parser.builder;

import inspera.parser.domain.diff.MetaDiff;

import java.lang.reflect.Field;

/**
 * Created by rmang on 17-06-2018.
 */
public class StandardMetaDiffBuilder implements MetaDiffBuilder {

    @Override
    public MetaDiff buildDiff(Field field, Object beforeObj, Object afterObj) {
        return new MetaDiff(field.getName(), beforeObj, afterObj);
    }
}
