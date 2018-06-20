package inspera.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import inspera.parser.domain.Examination;
import inspera.parser.mapper.ExamDiffObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {
        ObjectMapper objectMapper = ExamDiffObjectMapper.getObjectMapper();

        String beforeJsonString = new String(Files.readAllBytes(Paths.get(Main.class.getClassLoader().getResource("before.json").toURI())));

        String afterJsonString = new String(Files.readAllBytes(Paths.get(Main.class.getClassLoader().getResource("after.json").toURI())));

        JsonNode beforeJsonObject = objectMapper.readTree(beforeJsonString);

        JsonNode afterJsonObject = objectMapper.readTree(afterJsonString);

        ExaminationDiffParser examinationDiffParser = new ExaminationDiffParser();

        examinationDiffParser.parse(beforeJsonObject, afterJsonObject);
    }
}
