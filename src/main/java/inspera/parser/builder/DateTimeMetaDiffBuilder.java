package inspera.parser.builder;

import inspera.parser.domain.diff.MetaDiff;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static inspera.parser.helper.DiffHelper.buildMetaDiff;

/**
 * Created by rmang on 17-06-2018.
 */
public class DateTimeMetaDiffBuilder implements MetaDiffBuilder<LocalDateTime> {

    private ZoneId zoneId;

    public DateTimeMetaDiffBuilder() {
        zoneId = ZoneId.of("Europe/Oslo");
    }

    public DateTimeMetaDiffBuilder(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public MetaDiff buildDiff(Field metaField, LocalDateTime beforeLocalDateTime, LocalDateTime afterLocalDateTime) {
        ZonedDateTime beforeZonedDateTime = beforeLocalDateTime.atZone(zoneId);
        ZonedDateTime afterZonedDateTime = afterLocalDateTime.atZone(zoneId);

        return buildMetaDiff(metaField, beforeZonedDateTime, afterZonedDateTime);
    }
}
