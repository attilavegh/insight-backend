package hu.vattila.insight.repository;

import hu.vattila.insight.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<Account, Integer> {
    Optional<Account> findByEmail(String email);
    List<Account> findAllByFirstNameStartingWithIgnoreCaseOrLastNameStartingWithIgnoreCase(String frag1, String frag2);
}