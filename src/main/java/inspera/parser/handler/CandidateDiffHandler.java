package inspera.parser.handler;

import inspera.parser.domain.Candidate;
import inspera.parser.domain.Examination;
import inspera.parser.domain.diff.CandidateDifference;
import inspera.parser.domain.diff.CandidateIdentifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by rmang on 18-06-2018.
 */
public class CandidateDiffHandler {

    public CandidateDifference getCandidateDifferences(Examination beforeExaminationObj, Examination afterExaminationObj) {
        CandidateDifference candidateDifference = new CandidateDifference();

        List<Candidate> beforeCandidates = beforeExaminationObj.getCandidates();
        List<Candidate> afterCandidates = afterExaminationObj.getCandidates();

        List<Long> beforeCandidateIds = getCandidateIds(beforeCandidates);
        List<Long> afterCandidateIds = getCandidateIds(afterCandidates);

        List<CandidateIdentifier> addedCandidateIdentifiers = getAddedCandidateIdentifiers(beforeCandidateIds, afterCandidateIds);

        List<CandidateIdentifier> removedCandidateIdentifiers = getRemovedCandidateIdentifiers(beforeCandidateIds, afterCandidateIds);

        List<CandidateIdentifier> editedCandidateIdentifiers = getEditedCandidateIdentifiers(beforeCandidates, afterCandidates);

        return new CandidateDifference(editedCandidateIdentifiers, addedCandidateIdentifiers, removedCandidateIdentifiers);
    }

    private List<Long> getCandidateIds(List<Candidate> candidates) {
        return candidates.stream().map(Candidate::getId).sorted().collect(Collectors.toList());
    }

    private List<CandidateIdentifier> getEditedCandidateIdentifiers(List<Candidate> beforeCandidates, List<Candidate> afterCandidates) {
        List<CandidateIdentifier> editedCandidateIdentifiers = new ArrayList<>();

        List<Long> beforeCandidateIds = getCandidateIds(beforeCandidates);
        List<Long> afterCandidateIds = getCandidateIds(afterCandidates);

        beforeCandidateIds.retainAll(afterCandidateIds);

        Map<Long, Candidate> beforeCandidateMap = beforeCandidates.stream().collect(Collectors.toMap(Candidate::getId, c -> c));
        Map<Long, Candidate> afterCandidateMap = afterCandidates.stream().collect(Collectors.toMap(Candidate::getId, c -> c));

        beforeCandidateIds.forEach(id -> {
            if(!beforeCandidateMap.get(id).equals(afterCandidateMap.get(id)))
                editedCandidateIdentifiers.add(new CandidateIdentifier(id));
        });

        return editedCandidateIdentifiers;
    }

    private <T> List<T> removeSecondListFromFirstAndReturnResultList(List<T> firstList, List<T> secondList) {
        List<T> firstListCopy = new ArrayList<>(firstList);
        firstListCopy.removeAll(secondList);
        return firstListCopy;
    }

    private List<CandidateIdentifier> getRemovedCandidateIdentifiers(List<Long> beforeCandidateIds, List<Long> afterCandidateIds) {
        return getCandidateIdentifiers(afterCandidateIds, beforeCandidateIds);
    }

    private List<CandidateIdentifier> getAddedCandidateIdentifiers(List<Long> beforeCandidateIds, List<Long> afterCandidateIds) {
        return getCandidateIdentifiers(beforeCandidateIds, afterCandidateIds);
    }

    private List<CandidateIdentifier> getCandidateIdentifiers(List<Long> beforeCandidateIds, List<Long> afterCandidateIds) {
        List<CandidateIdentifier> candidateIdentifiers = new ArrayList<>();

        List<Long> resultCandidateIds = removeSecondListFromFirstAndReturnResultList(afterCandidateIds, beforeCandidateIds);
        resultCandidateIds.forEach(id -> {
            candidateIdentifiers.add(new CandidateIdentifier(id));
        });

        return candidateIdentifiers;
    }
}
