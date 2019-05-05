package hu.vattila.insight.repository;

import hu.vattila.insight.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    Optional<Account> findByGoogleId(String id);

    @Query(value = "SELECT * FROM ACCOUNT " +
            "WHERE LOWER(FIRST_NAME) LIKE CONCAT(LOWER(?1), '%')" +
            "OR LOWER(LAST_NAME) LIKE CONCAT(LOWER(?1), '%')" +
            "OR LOWER(FULL_NAME) LIKE CONCAT(LOWER(?1), '%')" +
            "OR LOWER(CONCAT(LAST_NAME, ' ', FIRST_NAME)) LIKE CONCAT(LOWER(?1), '%')",
            nativeQuery = true
    )
    List<Account> searchAccount(String fragment);
}
