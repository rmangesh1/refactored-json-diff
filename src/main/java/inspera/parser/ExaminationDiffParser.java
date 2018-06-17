package inspera.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import inspera.parser.domain.Examination;
import inspera.parser.domain.Metadata;
import inspera.parser.domain.diff.CandidateDifference;
import inspera.parser.domain.diff.ExaminationDifference;
import inspera.parser.domain.diff.MetaDiff;
import inspera.parser.handler.CandidateDiffHandler;
import inspera.parser.handler.MetaDiffHandler;
import inspera.parser.mapper.ExamDiffObjectMapper;
import org.apache.commons.lang3.builder.Diff;

import java.util.List;

/**
 * Created by rmang on 17-06-2018.
 */
public class ExaminationDiffParser implements DiffParser {

    private ObjectMapper objectMapper;

    private MetaDiffHandler metaDiffHandler;

    private CandidateDiffHandler candidateDiffHandler;

    public ExaminationDiffParser() {
        objectMapper = ExamDiffObjectMapper.getObjectMapper();
        metaDiffHandler = new MetaDiffHandler(Metadata.class);
        candidateDiffHandler = new CandidateDiffHandler();
    }

    @Override
    public JsonNode parse(JsonNode before, JsonNode after) {

        //Convert to POJO
        Examination beforeExaminationObj = objectMapper.convertValue(before, Examination.class);

        Examination afterExaminationObj = objectMapper.convertValue(after, Examination.class);

        List<MetaDiff> metaDiffList = metaDiffHandler.getMetaDifferences(beforeExaminationObj, afterExaminationObj);

        CandidateDifference candidateDifference = candidateDiffHandler.getCandidateDifferences(beforeExaminationObj, afterExaminationObj);

        ExaminationDifference examinationDifference = new ExaminationDifference(metaDiffList, candidateDifference);

        try {
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(examinationDifference));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
