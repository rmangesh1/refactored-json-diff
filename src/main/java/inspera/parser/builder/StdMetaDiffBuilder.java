package inspera.parser.builder;

import inspera.parser.domain.diff.MetaDiff;

import java.lang.reflect.Field;

import static inspera.parser.helper.DiffHelper.buildMetaDiff;

/**
 * Created by rmang on 17-06-2018.
 */
public class StdMetaDiffBuilder implements MetaDiffBuilder {

    @Override
    public MetaDiff buildDiff(Field field, Object beforeObj, Object afterObj) {
        return buildMetaDiff(field, beforeObj, afterObj);
    }
}
