package hu.vattila.insight.repository;

import hu.vattila.insight.entity.Insight;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InsightRepository extends CrudRepository<Insight, Integer> {
    List<Insight> findAllBySenderIdOrderByDateDesc(Integer senderId);
    List<Insight> findAllByReceiverIdOrderByDateDesc(Integer receiverId);
}
