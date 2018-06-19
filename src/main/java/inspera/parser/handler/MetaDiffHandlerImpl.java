package inspera.parser.handler;

import inspera.parser.builder.DateTimeMetaDiffBuilder;
import inspera.parser.builder.MetaDiffBuilder;
import inspera.parser.builder.StandardMetaDiffBuilder;
import inspera.parser.domain.Metadata;
import inspera.parser.domain.diff.MetaDiff;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rmang on 17-06-2018.
 */
public class MetaDiffHandlerImpl implements MetaDiffHandler {

    private Class metaClass;

    private Map<Class, MetaDiffBuilder> builderStrategyMap;

    public MetaDiffHandlerImpl(Class metaClass) {
        this.metaClass = metaClass;
        this.builderStrategyMap = initStrategy();
    }

    private Map<Class, MetaDiffBuilder> initStrategy() {
        Map<Class, MetaDiffBuilder> builderStrategyMap = new HashMap<>();
        builderStrategyMap.put(LocalDateTime.class, new DateTimeMetaDiffBuilder());
        return builderStrategyMap;
    }

    public MetaDiffHandlerImpl(Class metaClass, Map<Class, MetaDiffBuilder> builderStrategyMap) {
        this.metaClass = metaClass;
        this.builderStrategyMap = builderStrategyMap;
    }

    @Override
    public List<MetaDiff> getMetaDifferences(Metadata beforeMetadataObj, Metadata afterMetadataObj) {

        List<MetaDiff> metaDiffs = new ArrayList<>();
        MetaDiffBuilder standardMetaDiffBuilder = new StandardMetaDiffBuilder();
        MetaDiffBuilder metaDiffBuilder = null;
        Object beforeMetaVariableValue = null;
        Object afterMetaVariableValue = null;
        Class dataType = null;

        Field[] metaFields = metaClass.getDeclaredFields();

        try {
            for (Field field : metaFields) {
                field.setAccessible(true);
                beforeMetaVariableValue = field.get(beforeMetadataObj);
                afterMetaVariableValue = field.get(afterMetadataObj);
                if (!beforeMetaVariableValue.equals(afterMetaVariableValue)) {
                    dataType = Class.forName(field.getType().getName());

                    metaDiffBuilder = builderStrategyMap.getOrDefault(dataType, standardMetaDiffBuilder);
                    metaDiffs.add(metaDiffBuilder.buildDiff(field, beforeMetaVariableValue, afterMetaVariableValue));
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return metaDiffs;
    }
}
