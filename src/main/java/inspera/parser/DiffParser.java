package inspera.parser;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by rmang on 17-06-2018.
 */
public interface DiffParser {

    JsonNode parse(JsonNode before, JsonNode after);
}
