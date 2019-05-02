package hu.vattila.insight.service.experiment;

import hu.vattila.insight.dto.splitter.AssignmentDetailDto;
import hu.vattila.insight.dto.splitter.AssignmentResultDto;
import hu.vattila.insight.entity.Bucket;
import hu.vattila.insight.entity.Experiment;
import hu.vattila.insight.repository.ExperimentRepository;
import hu.vattila.insight.service.hash.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class SplitterService {

    private static final int HUNDRED_PERCENT = 100;

    private final ExperimentRepository experimentRepository;
    private final HashService hashService;

    @Autowired
    public SplitterService(ExperimentRepository experimentRepository, HashService hashService) {
        this.experimentRepository = experimentRepository;
        this.hashService = hashService;
    }

    public List<AssignmentResultDto> assign(AssignmentDetailDto assignmentDetail) {
        List<Experiment> experiments = experimentRepository.findAll();

        return experiments.stream()
                .filter(experiment -> hasRelevantDeviceType(experiment, assignmentDetail))
                .map(experiment -> createAssignmentResult(experiment, assignmentDetail))
                .collect(toList());
    }

    private boolean hasRelevantDeviceType(Experiment experiment, AssignmentDetailDto assignmentDetail) {
        return experiment.getDeviceType().equals(assignmentDetail.getDeviceType());
    }

    private AssignmentResultDto createAssignmentResult(Experiment experiment, AssignmentDetailDto assignmentDetail) {
        return new AssignmentResultDto(
                experiment.getName(),
                assignUserToBucket(experiment, assignmentDetail).orElse(null)
        );
    }

    private Optional<String> assignUserToBucket(Experiment experiment, AssignmentDetailDto assignmentDetail) {
        String saltedUserId = createSaltedUserId(experiment, assignmentDetail);
        long assignmentValue = Math.abs(hashService.hash(saltedUserId.getBytes())) % HUNDRED_PERCENT;

        for (Bucket bucket : experiment.getBuckets()) {
            assignmentValue -= bucket.getPercentage();
            if (assignmentValue < 0) {
                return Optional.of(bucket.getName());
            }
        }

        return Optional.empty();
    }

    private String createSaltedUserId(Experiment experiment, AssignmentDetailDto assignmentDetail) {
        return assignmentDetail.getGoogleId() + experiment.getId();
    }
}
