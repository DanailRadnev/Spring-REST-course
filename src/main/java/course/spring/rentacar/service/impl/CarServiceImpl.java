package course.spring.rentacar.service.impl;

import course.spring.rentacar.dao.CarRepository;
import course.spring.rentacar.dao.RatingRepository;
import course.spring.rentacar.dao.UserRepository;
import course.spring.rentacar.exception.NonexistingEntityException;
import course.spring.rentacar.model.entity.Car;
import course.spring.rentacar.model.entity.Rating;
import course.spring.rentacar.model.entity.User;
import course.spring.rentacar.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Transactional(propagation=REQUIRED)
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;
    private UserRepository userRepository;
    private RatingRepository ratingRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, UserRepository userRepository, RatingRepository ratingRepository){
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Car> getAllVehicles() {
        List<Car> cars = carRepository.findAll();
        cars.forEach(car -> {car.setRating(ratingRepository.getCarRating(car.getId()));});
        return cars;
    }

    @Override
    public Car getCarById(Long id) {
        Car car = carRepository.findById(id).orElseThrow(
                () -> new NonexistingEntityException(String.format("Vehicle with ID:%d does not exist", id)));
        car.setRating(ratingRepository.getCarRating(id));
        return car;
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
        car.setAvailable(true);
        car.setBroke(false);
        car.setDirty(false);
        return carRepository.save(car);
    }

    @Override
    public Car deleteCar(Long id) {
        Car result = this.getCarById(id);
        carRepository.delete(result);
        return result;
    }

    @Override
    public Car setCarForWash(Car car) {
        car.setDirty(true);
        return carRepository.save(car);
    }

    @Override
    public Car setCarForRepair(Car car) {
        car.setBroke(true);
        return carRepository.save(car);
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
    public Car rentACar(Car car, Long userId) {
        Car result = this.getCarById(car.getId());

        if(!result.isAvailable()){
            throw new NonexistingEntityException("The car is already rented");
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new NonexistingEntityException(String.format("User with ID:%d does not exist", userId)));

        //TODO Use logged user !!!

        result.setUser(user);
        result.setAvailable(false);
        return carRepository.save(result);
    }

    @Override
    public Car returnAndRateCar(Car car, Long userId, Long rate) {
        Car result = this.getCarById(car.getId());

        if(result.getUser() != null && !result.getUser().getId().equals(userId)){
            throw new NonexistingEntityException("The cat is not rented by this user");
        }else if(result.isAvailable()){
            throw new NonexistingEntityException("The car is not rented");
        }



        //TODO Use logged user !!!
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NonexistingEntityException(String.format("User with ID:%d does not exist", userId)));



        if(rate != null){

            AtomicReference<Rating> tmpRating = new AtomicReference<>();

            List<Rating> carRatings = ratingRepository.findByCarId(car.getId());

            carRatings.forEach(rating -> {
                if(rating.getUser().getId().equals(user.getId())){
                    rating.setRating(rate);
                    tmpRating.set(rating);
                }
            });

            if(tmpRating.get() == null){
                //Refactor
                tmpRating.set(new Rating());
                tmpRating.get().setCarId(result.getId());
                tmpRating.get().setUser(user);
                tmpRating.get().setRating(rate);
            }
            ratingRepository.save(tmpRating.get());
        }

        result.setUser(null);
        result.setAvailable(true);

        return carRepository.save(result);
    }
}
