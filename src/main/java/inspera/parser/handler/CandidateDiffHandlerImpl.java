package inspera.parser.handler;

import inspera.parser.domain.Candidate;
import inspera.parser.domain.diff.CandidateDifference;
import inspera.parser.domain.diff.CandidateIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static inspera.parser.util.ListUtils.removeAll;
import static inspera.parser.util.ListUtils.retainAll;

public class CandidateDiffHandlerImpl implements CandidateDiffHandler {

    private static final Logger logger = LoggerFactory.getLogger(CandidateDiffHandler.class);

    /**
     * Returns CandidateDifference between before and after candidates
     * @param beforeCandidates
     * @param afterCandidates
     * @return
     */
    @Override
    public CandidateDifference getCandidateDifferences(List<Candidate> beforeCandidates, List<Candidate> afterCandidates) {

        List<Long> beforeCandidateIds = getCandidateIds(beforeCandidates);
        List<Long> afterCandidateIds = getCandidateIds(afterCandidates);

        List<CandidateIdentifier> addedCandidateIdentifiers = getAddedCandidateIdentifiers(beforeCandidateIds, afterCandidateIds);

        List<CandidateIdentifier> removedCandidateIdentifiers = getRemovedCandidateIdentifiers(beforeCandidateIds, afterCandidateIds);

        List<CandidateIdentifier> editedCandidateIdentifiers = getEditedCandidateIdentifiers(beforeCandidates, afterCandidates, beforeCandidateIds, afterCandidateIds);

        return new CandidateDifference(editedCandidateIdentifiers, addedCandidateIdentifiers, removedCandidateIdentifiers);
    }

    /**
     * Returns list of added candidate identifiers
     * @param beforeCandidateIds
     * @param afterCandidateIds
     * @return
     */
    protected List<CandidateIdentifier> getAddedCandidateIdentifiers(List<Long> beforeCandidateIds, List<Long> afterCandidateIds) {
        return getCandidateIdentifiers(afterCandidateIds, beforeCandidateIds);
    }

    /**
     * Returns list of removed candidate identifiers
     * @param beforeCandidateIds
     * @param afterCandidateIds
     * @return
     */
    protected List<CandidateIdentifier> getRemovedCandidateIdentifiers(List<Long> beforeCandidateIds, List<Long> afterCandidateIds) {
        return getCandidateIdentifiers(beforeCandidateIds, afterCandidateIds);
    }

    /**
     * Returns list of edited candidate identifiers
     * @param beforeCandidates
     * @param afterCandidates
     * @param beforeCandidateIds
     * @param afterCandidateIds
     * @return
     */
    protected List<CandidateIdentifier> getEditedCandidateIdentifiers(List<Candidate> beforeCandidates, List<Candidate> afterCandidates,
                                                                    List<Long> beforeCandidateIds, List<Long> afterCandidateIds) {
        List<CandidateIdentifier> editedCandidateIdentifiers = new ArrayList<>();

        List<Long> commonCandidateIds = retainAll(beforeCandidateIds, afterCandidateIds);

        Map<Long, Candidate> beforeCandidateMap = getCandidateMap(beforeCandidates);
        Map<Long, Candidate> afterCandidateMap = getCandidateMap(afterCandidates);

        commonCandidateIds.forEach(id -> {
            if(!beforeCandidateMap.get(id).equals(afterCandidateMap.get(id)))
                editedCandidateIdentifiers.add(new CandidateIdentifier(id));
        });

        return editedCandidateIdentifiers;
    }

    /**
     * Returns list of candidate IDs
     * @param candidates
     * @return
     */
    private List<Long> getCandidateIds(List<Candidate> candidates) {
        return candidates.stream().map(Candidate::getId).sorted().collect(Collectors.toList());
    }

    /**
     * Returns a map of candidate id to candidate
     * @param candidates
     * @return
     */
    private Map<Long, Candidate> getCandidateMap(List<Candidate> candidates) {
        return candidates.stream().collect(Collectors.toMap(Candidate::getId, c -> c));
    }

    /**
     * Returns a list of added/removed CandidateIdentifiers
     * @param candidateIds
     * @param candidateIdsToBeRemoved
     * @return
     */
    private List<CandidateIdentifier> getCandidateIdentifiers(List<Long> candidateIds, List<Long> candidateIdsToBeRemoved) {
        List<CandidateIdentifier> candidateIdentifiers = new ArrayList<>();

        List<Long> resultCandidateIds = removeAll(candidateIds, candidateIdsToBeRemoved);
        resultCandidateIds.forEach(id -> {
            candidateIdentifiers.add(new CandidateIdentifier(id));
        });

        return candidateIdentifiers;
    }
}
