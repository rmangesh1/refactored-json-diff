package inspera.parser.handler;

import inspera.parser.domain.Metadata;
import inspera.parser.domain.diff.MetaDiff;

import java.util.List;

/**
 * Created by mrana on 19.06.2018.
 */
public interface MetaDiffHandler {

    List<MetaDiff> getMetaDifferences(Metadata beforeMetadataObj, Metadata afterMetadataObj);
}
