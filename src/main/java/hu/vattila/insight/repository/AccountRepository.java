package hu.vattila.insight.repository;

import hu.vattila.insight.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    Optional<Account> findByGoogleId(String id);
    List<Account> findAllByFirstNameStartingWithIgnoreCaseOrLastNameStartingWithIgnoreCase(String frag1, String frag2);
}
