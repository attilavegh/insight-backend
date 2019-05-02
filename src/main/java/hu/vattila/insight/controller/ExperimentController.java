package hu.vattila.insight.controller;

import hu.vattila.insight.dto.splitter.AssignmentDetailDto;
import hu.vattila.insight.dto.splitter.AssignmentResultDto;
import hu.vattila.insight.service.experiment.SplitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"https://elte-insight.firebaseapp.com", "http://localhost:4200"})
@RestController
@RequestMapping("/api/experiment")
public class ExperimentController {

    private final SplitterService splitterService;

    @Autowired
    public ExperimentController(SplitterService splitterService) {
        this.splitterService = splitterService;
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity handleException(Exception exception) {
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/assign")
    public ResponseEntity<List<AssignmentResultDto>> login(@RequestBody AssignmentDetailDto assignmentDetailDto) {
        return ResponseEntity.ok(splitterService.assign(assignmentDetailDto));
    }
}
