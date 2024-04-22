package org.example.spring_aop_example.repository;

import org.example.spring_aop_example.model.entity.AccountEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity,Long> {

    @EntityGraph(attributePaths = "roles")
    Optional<AccountEntity> findByName(String name);
}
