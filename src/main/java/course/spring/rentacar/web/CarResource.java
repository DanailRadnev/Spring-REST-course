package course.spring.rentacar.web;

import course.spring.rentacar.exception.InvalidEntityDataException;
import course.spring.rentacar.model.entity.Car;
import course.spring.rentacar.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<Car> createCar(@RequestBody Car car, HttpServletRequest request) {
        //TODO check if the logged user is Admin
        Car created = carService.addCar(car);
        return ResponseEntity.created(
                MvcUriComponentsBuilder
                        .fromMethodCall(on(CarResource.class).createCar(car, request))
                        .pathSegment("{id}").build(created.getId())
        ).body(created);
    }

    @PutMapping("{id}")
    public Car updateCar(@PathVariable Long id, @RequestBody Car car) {
        //TODO check for validation errors
        //TODO check if the logged user is Admin

        checkIfCarMatchId(id, car);
        return carService.updateCar(car);
    }

    @PutMapping("dirty/{id}")
    public Car updateCarForWash(@PathVariable Long id, @RequestBody Car car) {
        //TODO check for validation errors
        //TODO check if the logged user is Admin
        checkIfCarMatchId(id, car);
        return carService.setCarForWash(car);
    }

    @PutMapping("broke/{id}")
    public Car updateCarForRepair(@PathVariable Long id, @RequestBody Car car) {
        //TODO check for validation errors
        //TODO check if the logged user is Admin

        checkIfCarMatchId(id, car);
        return carService.setCarForRepair(car);
    }

    @PutMapping("wash/{id}")
    public Car washCar(@PathVariable Long id, @RequestBody Car car) {
        //TODO check for validation errors
        //TODO check if the logged user is Employee
        checkIfCarMatchId(id, car);
        return carService.washCar(car);
    }

    @PutMapping("repair/{id}")
    public Car repairCar(@PathVariable Long id, @RequestBody Car car) {
        //TODO check for validation errors
        //TODO check if the logged user is Employee

        checkIfCarMatchId(id, car);
        return carService.repairCar(car);
    }

    @PutMapping("rent/{id}")
    public Car rentACar(@PathVariable Long id, @RequestBody Car car) {
        //TODO check for validation errors

        checkIfCarMatchId(id, car);
        return carService.rentACar(car);
    }

    @PutMapping("return/{id}/rate/{rate}")
    public Car returnAndRateCar(@PathVariable Long id, @PositiveOrZero @PathVariable Integer rate, @RequestBody Car car) {
        //TODO check for validation errors

        checkIfCarMatchId(id, car);
        return carService.returnAndRateCar(car, rate);
    }

    @PutMapping("return/{id}")
    public Car returnCar(@PathVariable Long id, @RequestBody Car car) {
        //TODO check for validation errors

        checkIfCarMatchId(id, car);
        return carService.returnAndRateCar(car, null);
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
