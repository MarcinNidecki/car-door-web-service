package com.mnidecki.cardoor.repository;

import com.mnidecki.cardoor.domain.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {

    ConfirmationToken findByToken(String confirmationToken);
}