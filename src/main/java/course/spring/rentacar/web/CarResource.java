package course.spring.rentacar.web;

import course.spring.rentacar.exception.InvalidEntityDataException;
import course.spring.rentacar.exception.ValidationException;
import course.spring.rentacar.model.entity.Car;
import course.spring.rentacar.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequestMapping("/rentacar/car/")
public class CarResource {

    private CarService carService;

    @Autowired
    public CarResource(CarService carService){this.carService = carService;}

    @GetMapping
    public List<Car> getAllCars(){
        return carService.getAllVehicles();
    }

    @GetMapping("{id}")
    public Car getCarById(@PathVariable Long id){
        return carService.getCarById(id);
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody @Valid Car car, HttpServletRequest request, Errors errors) {
        if(errors.hasErrors()){
            throw new ValidationException(errors);
        }
        //TODO check if the logged user is Admin
        Car created = carService.addCar(car);
        return ResponseEntity.created(
                MvcUriComponentsBuilder
                        .fromMethodCall(on(CarResource.class).createCar(car, request, errors))
                        .pathSegment("{id}").build(created.getId())
        ).body(created);
    }

    @PutMapping("{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody @Valid Car car, Errors errors) {
        if(errors.hasErrors()){
            throw new ValidationException(errors);
        }
        //TODO check for validation errors
        //TODO check if the logged user is Admin

        checkIfCarMatchId(id, car);
        return carService.updateCar(car);
    }

    @PutMapping("dirty/{id}")
    public Car updateCarForWash(@PathVariable Long id, @RequestBody @Valid Car car, Errors errors) {
        if(errors.hasErrors()){
            throw new ValidationException(errors);
        }
        //TODO check for validation errors
        //TODO check if the logged user is Admin
        checkIfCarMatchId(id, car);
        return carService.setCarForWash(car);
    }

    @PutMapping("broke/{id}")
    public Car updateCarForRepair(@PathVariable Long id, @RequestBody @Valid Car car, Errors errors) {
        if(errors.hasErrors()){
            throw new ValidationException(errors);
        }
        //TODO check for validation errors
        //TODO check if the logged user is Admin

        checkIfCarMatchId(id, car);
        return carService.setCarForRepair(car);
    }

    @PutMapping("wash/{id}")
    public Car washCar(@PathVariable Long id, @RequestBody @Valid Car car, Errors errors) {
        if(errors.hasErrors()){
            throw new ValidationException(errors);
        }
        //TODO check for validation errors
        //TODO check if the logged user is Employee
        checkIfCarMatchId(id, car);
        return carService.washCar(car);
    }

    @PutMapping("repair/{id}")
    public Car repairCar(@PathVariable Long id, @RequestBody @Valid Car car, Errors errors) {
        if(errors.hasErrors()){
            throw new ValidationException(errors);
        }
        //TODO check for validation errors
        //TODO check if the logged user is Employee

        checkIfCarMatchId(id, car);
        return carService.repairCar(car);
    }

    @PutMapping("rent/{carId}/user/{userId}")
    public Car rentACar(@PathVariable Long carId, @PathVariable Long userId, @RequestBody @Valid Car car, Errors errors) {
        if(errors.hasErrors()){
            throw new ValidationException(errors);
        }

        checkIfCarMatchId(carId, car);
        return carService.rentACar(car, userId);
    }

    @PutMapping("return/{id}/user/{userId}/rate/{rate}")
    public Car returnAndRateCar(@PathVariable Long id,
                                @PositiveOrZero @PathVariable Long rate,
                                @PathVariable Long userId,
                                @RequestBody @Valid Car car, Errors errors) {
        if(errors.hasErrors()){
            throw new ValidationException(errors);
        }
        //TODO check for validation errors

        checkIfCarMatchId(id, car);
        return carService.returnAndRateCar(car, userId, rate);
    }

    @PutMapping("return/{id}/user/{userId}")
    public Car returnCar(@PathVariable Long id, @PathVariable Long userId, @RequestBody @Valid  Car car, Errors errors) {
        if(errors.hasErrors()){
            throw new ValidationException(errors);
        }
        //TODO check for validation errors

        checkIfCarMatchId(id, car);
        return carService.returnAndRateCar(car, userId, null);
    }

    @DeleteMapping("{id}")
    public Car deleteCar(@PathVariable Long id){
        //TODO check if the logged user is Admin
        return carService.deleteCar(id);
    }

    private void checkIfCarMatchId(Long id, Car car){
        if(!id.equals(car.getId())) {
            throw new InvalidEntityDataException(
                    String.format("Url ID:%d differs from body entity ID:%d", id, car.getId()));
        }
    }
}
