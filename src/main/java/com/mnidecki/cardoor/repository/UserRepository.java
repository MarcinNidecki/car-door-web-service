package com.mnidecki.cardoor.repository;

import com.mnidecki.cardoor.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> getUserByEmail(String email);

    User findUserByEmail(String email);

    User save(User user);

    User findByEmailIgnoreCase(String emailId);

    Optional<User> getUserById(Long id);
}
