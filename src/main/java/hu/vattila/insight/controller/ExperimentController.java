package hu.vattila.insight.controller;

import hu.vattila.insight.dto.experiment.AssignmentDetailDto;
import hu.vattila.insight.dto.experiment.AssignmentResultDto;
import hu.vattila.insight.service.experiment.ExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"https://elte-insight.firebaseapp.com", "http://localhost:4200"})
@RestController
@RequestMapping("/api/experiment")
public class ExperimentController {

    private final ExperimentService experimentService;

    @Autowired
    public ExperimentController(ExperimentService experimentService) {
        this.experimentService = experimentService;
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity handleException(Exception exception) {
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/assign")
    public ResponseEntity<List<AssignmentResultDto>> assign(@RequestBody AssignmentDetailDto assignmentDetailDto) {
        return ResponseEntity.ok(experimentService.assign(assignmentDetailDto));
    }
}
