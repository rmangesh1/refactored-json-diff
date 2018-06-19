package inspera.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import inspera.parser.mapper.ExamDiffObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 */
public class ParserTest {

    private ExaminationDiffParser parser = new ExaminationDiffParser();

    ObjectMapper objectMapper = ExamDiffObjectMapper.getObjectMapper();

    private JsonNode beforeJsonNode;

    private JsonNode afterJsonNode;

    @Before
    public void setup() throws URISyntaxException, IOException {
        beforeJsonNode = objectMapper.readTree(new String(Files.readAllBytes(Paths.get(ParserTest.class.getClassLoader().getResource("before.json").toURI()))));
        afterJsonNode = objectMapper.readTree(new String(Files.readAllBytes(Paths.get(ParserTest.class.getClassLoader().getResource("after.json").toURI()))));
    }

    // TODO Define tests here
    @Test
    public void test() {
        System.out.println(beforeJsonNode);
    }

}
