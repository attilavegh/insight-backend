package hu.vattila.insight.repository;

import hu.vattila.insight.entity.Experiment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExperimentRepository extends CrudRepository<Experiment, Integer> {
    List<Experiment> findAll();
}
