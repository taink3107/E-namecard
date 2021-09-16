package com.example.redissession.repository;

import com.example.redissession.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query(value = "SELECT acc.* FROM account as acc WHERE acc.user_name = :name", nativeQuery = true)
    Account findByName(@Param("name") String user_name);
}
