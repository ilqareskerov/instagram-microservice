package com.company.userservice.repo;
import com.company.userservice.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
   Optional<User> findByUsername(String username);
    User findByEmail(String email);
    @Query("select u from User u where u.id in :users")
    List<User> findAllUsersByIds(@Param("users") List<Long> userIds);
    @Query("select distinct u from User u where u.username like %:query% or u.email like %:query%")
    List<User> findByQuery(@Param("query") String query);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}
