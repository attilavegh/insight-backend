package hu.vattila.insight.controller;

import hu.vattila.insight.entity.Insight;
import hu.vattila.insight.repository.InsightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://vattila-insight.herokuapp.com", "http://localhost:4200"})
@RestController
@RequestMapping("/api/insight")
public class InsightController {

    @Autowired
    InsightRepository insightRepository;

    @GetMapping("/sent/{senderId}")
    public ResponseEntity<List<Insight>> getSentInsights(@PathVariable("senderId") Integer senderId) {
        List<Insight> insights = insightRepository.findAllBySenderId(senderId);
        return ResponseEntity.ok(insights);
    }

    @GetMapping("/received/{receiverId}")
    public ResponseEntity<List<Insight>> getReceivedInsights(@PathVariable("receiverId") Integer receiverId) {
        List<Insight> insights = insightRepository.findAllByReceiverId(receiverId);
        return ResponseEntity.ok(insights);
    }

    @PostMapping("/send")
    public ResponseEntity<Insight> saveInsight(@RequestBody Insight insight) {
        return ResponseEntity.ok(insightRepository.save(insight));
    }
}
