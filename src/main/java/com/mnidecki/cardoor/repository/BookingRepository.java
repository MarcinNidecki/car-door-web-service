package com.mnidecki.cardoor.repository;


import com.mnidecki.cardoor.domain.booking.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {

    @Override
    List<Booking> findAll();

    @Override
    Booking save(Booking booking);

    Optional<Booking> findById(Long id);

    void deleteById(Long id);

    List<Booking> findAllByCar_Id(Long id);


}
