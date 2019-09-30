package com.mnidecki.cardoor.repository;

import com.mnidecki.cardoor.domain.booking.DiscountCode;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DiscountCodeRepository extends CrudRepository <DiscountCode, Integer> {

    @Override
    List<DiscountCode> findAll();

    Optional<DiscountCode> findById(Long id);

    @Override
    DiscountCode save(DiscountCode star);

    void deleteById(Long id);
}
