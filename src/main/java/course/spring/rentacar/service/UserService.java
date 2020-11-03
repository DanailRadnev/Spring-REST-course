package course.spring.rentacar.service;

import course.spring.rentacar.model.entity.Car;
import course.spring.rentacar.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByUsername(String username);
    User createUser(User user);
    User updateUser(User user);
    User deleteUser(Long id);
    User addEmployee(User emp);
    User removeEmployee(User emp);
    public long count();


//    Car updateCar(Car car);
//    Car addCar(Car car);
//    Car deleteCar(Car car);
//    User addEmployee(User emp);
}
