package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.booking.Booking;
import com.mnidecki.cardoor.domain.car.Car;
import com.mnidecki.cardoor.repository.CarCustomRepository;
import com.mnidecki.cardoor.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DBCarService {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private DBBookingService bookingService;
    @Autowired
    private CarCustomRepository carCustomRepository;

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car findById(final Long id) {
        return carRepository.findById(id).orElseGet(null);
    }

    public Car save(final Car car) {
        return carRepository.save(car);
    }

    public void deleteByID(final Long id) {
        carRepository.deleteById(id);
    }

    public List<Car> findCarByCityId(Long id) {
        return carRepository.findCarByLocation_Id(id);
    }

    public List<Car> getAllAvailableCar(Specification<Car> carSpecification, String startDate, String startTime, String endDate, String endTime, Long cityId) {
        Timestamp receiptDate = Timestamp.valueOf(startDate + " " + startTime + ":00");
        Timestamp returnDate = Timestamp.valueOf(endDate + " " + endTime + ":00");
        List<Car> availableCar = new ArrayList<>();
        List<Car> carsInLocation = carCustomRepository.findAll(carSpecification).stream()
                .filter(car -> car.getLocation().getId().equals(cityId))
                .collect(Collectors.toList());

        for (Car car : carsInLocation) {
            Set<Booking> bookingList = car.getBookingsList();
            Set<Booking> conflictBooking = bookingList.stream()
                    .filter(booking -> booking.getStartDate().before(returnDate) && receiptDate.before(booking.getReturnDate()))
                    .collect(Collectors.toSet());
            if (conflictBooking.size() == 0) {
                availableCar.add(car);
            }
        }
        return availableCar;
    }
}
