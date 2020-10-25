package course.spring.rentacar.service.impl;

import course.spring.rentacar.dao.CarRepository;
import course.spring.rentacar.exception.NonexistingEntityException;
import course.spring.rentacar.model.entity.Car;
import course.spring.rentacar.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Transactional(propagation=REQUIRED)
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository){this.carRepository = carRepository;}

    @Override
    public List<Car> getAllVehicles() {
        return carRepository.findAll();
    }

    @Override
    public Car getCarById(Long id) {
        return carRepository.findById(id).orElseThrow(
                () -> new NonexistingEntityException(String.format("Vehicle with ID:%d does not exist", id)));
    }

    @Override
    public Car updateCar(Car car) {
        carRepository.findById(car.getId()).orElseThrow(
                () -> new NonexistingEntityException(String.format("Vehicle with ID:%d does not exist", car.getId()))
        );
        return carRepository.save(car);
    }

    @Override
    public Car addCar(Car car) {
        car.setId(null);
        car.setAvailable(false);
        return carRepository.save(car);
    }

    @Override
    public Car deleteCar(Long id) {
        Car result = this.getCarById(id);
        carRepository.delete(result);
        return result;
    }

    @Override
    public Car washCar(Car car) {
        Car result = this.getCarById(car.getId());
        result.setDirty(false);
        return carRepository.save(car);
    }

    @Override
    public Car repairCar(Car car) {
        Car result = this.getCarById(car.getId());
        result.setBroke(false);
        return carRepository.save(car);
    }

    @Override
    public Car rentACar(Car car) {
        Car result = this.getCarById(car.getId());
        result.setAvailable(false);
        return carRepository.save(car);
    }

    @Override
    public Car returnAndRateCar(Car car, int rate) {
        Car result = this.getCarById(car.getId());
        result.setAvailable(false);
        return null;
    }
}
