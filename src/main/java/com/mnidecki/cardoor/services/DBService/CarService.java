package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.booking.Booking;
import com.mnidecki.cardoor.domain.car.Car;
import com.mnidecki.cardoor.domain.car.CarParameters;
import com.mnidecki.cardoor.repository.CarCustomRepository;
import com.mnidecki.cardoor.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarCustomRepository carCustomRepository;

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car findById(final Long id) {
        return carRepository.findById(id).orElse(new Car());
    }

    public Car save(final Car car) {
        return carRepository.save(car);
    }

    public Car save(final Car car, final CarParameters carParameters) {
        car.setCarParameters(carParameters);
        return carRepository.save(car);
    }

    public void deleteByID(final Long id) {
        carRepository.deleteById(id);
    }

    public List<Car> getAllAvailableCar(Specification<Car> carSpecification, String startDate, String startTime, String endDate, String endTime, Long cityId) {
        Timestamp receiptDate = Timestamp.valueOf(startDate + " " + startTime + ":00");
        Timestamp returnDate = Timestamp.valueOf(endDate + " " + endTime + ":00");
        List<Car> availableCar = new ArrayList<>();
        List<Car> carsInLocation = carCustomRepository.findAll(carSpecification).stream()
                .filter(car -> car.getLocation().getId().equals(cityId))
                .collect(Collectors.toList());

        for (Car car : carsInLocation) {
            List<Booking> bookingList = car.getBookingsList();
            List<Booking> conflictBooking = bookingList.stream()
                    .filter(booking -> booking.getStartDate().before(returnDate) && receiptDate.before(booking.getReturnDate()))
                    .collect(Collectors.toList());
            if (conflictBooking.size() == 0) {
                availableCar.add(car);
            }
        }
        return availableCar;
    }
}
