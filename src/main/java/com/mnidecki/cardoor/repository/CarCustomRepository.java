package com.mnidecki.cardoor.repository;


import com.mnidecki.cardoor.domain.car.Car;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarCustomRepository extends PagingAndSortingRepository<Car, Long>, JpaSpecificationExecutor<Car> {


}