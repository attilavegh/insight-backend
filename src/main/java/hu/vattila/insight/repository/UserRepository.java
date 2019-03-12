package hu.vattila.insight.repository;

import hu.vattila.insight.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByName(String username);
    List<User> findAllByNameContaining(String username);
}