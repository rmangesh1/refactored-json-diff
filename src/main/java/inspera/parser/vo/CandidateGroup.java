package inspera.parser.vo;

import inspera.parser.domain.Candidate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rmang on 19-06-2018.
 */
public class CandidateGroup {

    private List<Candidate> beforeCandidates;

    private List<Candidate> afterCandidates;

    private List<Long> beforeCandidateIds;

    private List<Long> afterCandidateIds;

    public CandidateGroup(List<Candidate> beforeCandidates, List<Candidate> afterCandidates) {
        this.beforeCandidates = beforeCandidates;
        this.afterCandidates = afterCandidates;
        this.beforeCandidateIds = getCandidateIds(beforeCandidates);
        this.afterCandidateIds = getCandidateIds(afterCandidates);
    }

    public List<Candidate> getBeforeCandidates() {
        return beforeCandidates;
    }

    public List<Candidate> getAfterCandidates() {
        return afterCandidates;
    }

    public List<Long> getBeforeCandidateIds() {
        return beforeCandidateIds;
    }

    public List<Long> getAfterCandidateIds() {
        return afterCandidateIds;
    }

    private List<Long> getCandidateIds(List<Candidate> candidates) {
        return candidates.stream().map(Candidate::getId).sorted().collect(Collectors.toList());
    }
}
