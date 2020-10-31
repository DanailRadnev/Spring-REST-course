package course.spring.rentacar.service;

import course.spring.rentacar.model.entity.Car;

import java.util.List;

public interface CarService {
    List<Car> getAllVehicles();
    Car getCarById(Long id);
    Car updateCar(Car car);
    Car addCar(Car car);
    Car deleteCar(Long id);
    Car setCarForWash(Car car);
    Car setCarForRepair(Car car);
    Car washCar(Car car);
    Car repairCar(Car car);
    Car rentACar(Car car, Long userId);
    Car returnAndRateCar(Car car, Long userId, Long rate);
}
