package hu.vattila.insight.controller;

import hu.vattila.insight.entity.Insight;
import hu.vattila.insight.entity.User;
import hu.vattila.insight.repository.InsightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/insight")
public class InsightController {

    @Autowired
    InsightRepository insightRepository;

    @GetMapping("/sent")
    public ResponseEntity<List<Insight>> getSentInsights() {
        List<Insight> insights = insightRepository.findAllBySenderId(1);
        return ResponseEntity.ok(insights);
    }

    @GetMapping("/received")
    public ResponseEntity<List<Insight>> getReceivedInsights() {
        List<Insight> insights = insightRepository.findAllByReceiverId(1);
        return ResponseEntity.ok(insights);
    }

    @PostMapping("/send")
    public ResponseEntity<Insight> saveInsight(@RequestBody Insight insight) {
        return ResponseEntity.ok(insightRepository.save(insight));
    }
}
