package com.trunggame.repository;

import java.util.Optional;

import com.trunggame.enums.ERole;
import com.trunggame.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
