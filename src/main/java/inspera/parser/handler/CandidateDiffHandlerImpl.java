package inspera.parser.handler;

import inspera.parser.domain.Candidate;
import inspera.parser.domain.Examination;
import inspera.parser.domain.diff.CandidateDifference;
import inspera.parser.domain.diff.CandidateIdentifier;
import inspera.parser.util.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static inspera.parser.util.ListUtils.removeAll;
import static inspera.parser.util.ListUtils.retainAll;

/**
 * Created by rmang on 18-06-2018.
 */
public class CandidateDiffHandlerImpl implements CandidateDiffHandler {

    @Override
    public CandidateDifference getCandidateDifferences(List<Candidate> beforeCandidates, List<Candidate> afterCandidates) {

        List<Long> beforeCandidateIds = getCandidateIds(beforeCandidates);
        List<Long> afterCandidateIds = getCandidateIds(afterCandidates);

        List<CandidateIdentifier> addedCandidateIdentifiers = getAddedCandidateIdentifiers(beforeCandidateIds, afterCandidateIds);

        List<CandidateIdentifier> removedCandidateIdentifiers = getRemovedCandidateIdentifiers(beforeCandidateIds, afterCandidateIds);

        List<CandidateIdentifier> editedCandidateIdentifiers = getEditedCandidateIdentifiers(beforeCandidates, afterCandidates);

        return new CandidateDifference(editedCandidateIdentifiers, addedCandidateIdentifiers, removedCandidateIdentifiers);
    }

    private List<CandidateIdentifier> getAddedCandidateIdentifiers(List<Long> beforeCandidateIds, List<Long> afterCandidateIds) {
        return getCandidateIdentifiers(afterCandidateIds, beforeCandidateIds);
    }

    private List<CandidateIdentifier> getRemovedCandidateIdentifiers(List<Long> beforeCandidateIds, List<Long> afterCandidateIds) {
        return getCandidateIdentifiers(beforeCandidateIds, afterCandidateIds);
    }

    private List<CandidateIdentifier> getEditedCandidateIdentifiers(List<Candidate> beforeCandidates, List<Candidate> afterCandidates) {
        List<CandidateIdentifier> editedCandidateIdentifiers = new ArrayList<>();

        List<Long> beforeCandidateIds = getCandidateIds(beforeCandidates);
        List<Long> afterCandidateIds = getCandidateIds(afterCandidates);

        List<Long> commonCandidateIds = retainAll(beforeCandidateIds, afterCandidateIds);

        Map<Long, Candidate> beforeCandidateMap = getCandidateMap(beforeCandidates);
        Map<Long, Candidate> afterCandidateMap = getCandidateMap(afterCandidates);

        commonCandidateIds.forEach(id -> {
            if(!beforeCandidateMap.get(id).equals(afterCandidateMap.get(id)))
                editedCandidateIdentifiers.add(new CandidateIdentifier(id));
        });

        return editedCandidateIdentifiers;
    }

    private List<Long> getCandidateIds(List<Candidate> candidates) {
        return candidates.stream().map(Candidate::getId).sorted().collect(Collectors.toList());
    }

    private Map<Long, Candidate> getCandidateMap(List<Candidate> candidates) {
        return candidates.stream().collect(Collectors.toMap(Candidate::getId, c -> c));
    }

    private List<CandidateIdentifier> getCandidateIdentifiers(List<Long> candidateIds, List<Long> candidateIdsToBeRemoved) {
        List<CandidateIdentifier> candidateIdentifiers = new ArrayList<>();

        List<Long> resultCandidateIds = removeAll(candidateIds, candidateIdsToBeRemoved);
        resultCandidateIds.forEach(id -> {
            candidateIdentifiers.add(new CandidateIdentifier(id));
        });

        return candidateIdentifiers;
    }
}
