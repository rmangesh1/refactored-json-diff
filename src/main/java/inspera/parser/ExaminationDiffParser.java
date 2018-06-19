package inspera.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import inspera.parser.domain.Examination;
import inspera.parser.domain.Metadata;
import inspera.parser.domain.diff.CandidateDifference;
import inspera.parser.domain.diff.ExaminationDifference;
import inspera.parser.domain.diff.MetaDiff;
import inspera.parser.exception.NonMatchingEntityException;
import inspera.parser.handler.CandidateDiffHandler;
import inspera.parser.handler.CandidateDiffHandlerImpl;
import inspera.parser.handler.MetaDiffHandler;
import inspera.parser.handler.MetaDiffHandlerImpl;
import inspera.parser.mapper.ExamDiffObjectMapper;

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
        metaDiffHandler = new MetaDiffHandlerImpl(Metadata.class);
        candidateDiffHandler = new CandidateDiffHandlerImpl();
    }

    public ExaminationDiffParser(ObjectMapper objectMapper, MetaDiffHandler metaDiffHandler, CandidateDiffHandler candidateDiffHandler) {
        this.objectMapper = objectMapper;
        this.metaDiffHandler = metaDiffHandler;
        this.candidateDiffHandler = candidateDiffHandler;
    }

    @Override
    public JsonNode parse(JsonNode before, JsonNode after) {

        //Convert to POJO
        Examination beforeExaminationObj = objectMapper.convertValue(before, Examination.class);
        Examination afterExaminationObj = objectMapper.convertValue(after, Examination.class);

        ExaminationDifference examinationDifference = null;

        if(beforeExaminationObj.getId().equals(afterExaminationObj.getId())) {
            List<MetaDiff> metaDiffList = metaDiffHandler.getMetaDifferences(beforeExaminationObj.getMeta(), afterExaminationObj.getMeta());
            CandidateDifference candidateDifference = candidateDiffHandler.getCandidateDifferences(beforeExaminationObj.getCandidates(), afterExaminationObj.getCandidates());
            examinationDifference = new ExaminationDifference(metaDiffList, candidateDifference);
        } else {
            throw new NonMatchingEntityException("Not comparing the same examinations!");
        }

        return objectMapper.valueToTree(examinationDifference);
    }
}
