package com.trunggame.repository;

import com.trunggame.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phoneNumber);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET status = 'DELETED' WHERE id IN (:ids) ", nativeQuery = true)
    void deleteUserByIds(List<Integer> ids);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET status = 'ACTIVE' WHERE id IN (:ids) ", nativeQuery = true)
    void activeUserByIds(List<Integer> ids);

}
