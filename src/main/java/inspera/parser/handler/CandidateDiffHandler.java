package inspera.parser.handler;

import inspera.parser.domain.Candidate;
import inspera.parser.domain.diff.CandidateDifference;

import java.util.List;

/**
 * Created by mrana on 19.06.2018.
 */
public interface CandidateDiffHandler {

    CandidateDifference getCandidateDifferences(List<Candidate> beforeCandidates, List<Candidate> afterCandidates);
}
