package com.example.redissession.repository;

import com.example.redissession.domain.Account;
import com.example.redissession.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

}
