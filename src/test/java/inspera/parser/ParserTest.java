package inspera.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import inspera.parser.domain.Examination;
import inspera.parser.exception.NonMatchingEntityException;
import inspera.parser.mapper.ExamDiffObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 *
 */
public class ParserTest {

    private ExaminationDiffParser parser = new ExaminationDiffParser();

    private ObjectMapper objectMapper = ExamDiffObjectMapper.getObjectMapper();

    @Test
    public void testParserToGiveCorrectDifference() throws URISyntaxException, IOException {
        //Given
        JsonNode beforeJsonNode = objectMapper.readTree(new String(Files.readAllBytes(Paths.get(ParserTest.class.getClassLoader().getResource("testdata/validtest-1/before.json").toURI()))));
        JsonNode afterJsonNode = objectMapper.readTree(new String(Files.readAllBytes(Paths.get(ParserTest.class.getClassLoader().getResource("testdata/validtest-1/after.json").toURI()))));

        //Expected
        JsonNode expectedDiffJsonNode = objectMapper.readTree(new String(Files.readAllBytes(Paths.get(ParserTest.class.getClassLoader().getResource("testdata/validtest-1/diff.json").toURI()))));

        //When
        JsonNode actualDiffJsonNode = parser.parse(beforeJsonNode, afterJsonNode);

        //Then
        assertEquals(expectedDiffJsonNode.asText(), actualDiffJsonNode.asText());
    }

    @Test(expected = NonMatchingEntityException.class)
    public void testParserToGiveExceptionWhenExaminationIdsAreDifferent() throws IOException, URISyntaxException {
        //Given
        JsonNode beforeJsonNode = objectMapper.readTree(new String(Files.readAllBytes(Paths.get(ParserTest.class.getClassLoader().getResource("testdata/invalidtest-1/before.json").toURI()))));
        JsonNode afterJsonNode = objectMapper.readTree(new String(Files.readAllBytes(Paths.get(ParserTest.class.getClassLoader().getResource("testdata/invalidtest-1/after.json").toURI()))));

        //Convert to POJO
        Examination beforeExaminationObj = objectMapper.convertValue(beforeJsonNode, Examination.class);
        Examination afterExaminationObj = objectMapper.convertValue(afterJsonNode, Examination.class);

        assertNotEquals(beforeExaminationObj.getId(), afterExaminationObj.getId());

        parser.parse(beforeJsonNode, afterJsonNode);
    }

}
