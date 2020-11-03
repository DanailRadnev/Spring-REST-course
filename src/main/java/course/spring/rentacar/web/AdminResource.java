package course.spring.rentacar.web;

import course.spring.rentacar.exception.InvalidEntityDataException;
import course.spring.rentacar.exception.ValidationException;
import course.spring.rentacar.model.Role;
import course.spring.rentacar.model.entity.User;
import course.spring.rentacar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;

import static course.spring.rentacar.model.Role.USER;

@RestController
@RequestMapping("/rentacar/admin/")
public class AdminResource {
    private UserService userService;

    @Autowired
    public AdminResource(UserService userService){
        this.userService = userService;
    }

    @PutMapping("/ban/{id}")
    public User banUser(@PathVariable Long id, @RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            throw new ValidationException(errors);
        }
        checkIfUserMatchId(id, user);

        //TODO check if the logged user is Admin
        user.setActive(false);
        return userService.updateUser(user);
    }

    @PutMapping("/unban/{id}")
    public User unbanUser(@PathVariable Long id, @RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            throw new ValidationException(errors);
        }
        checkIfUserMatchId(id, user);

        //TODO check if the logged user is Admin
        user.setActive(true);
        user.getRoles().add(USER);
        return userService.updateUser(user);
    }

    @PutMapping("/hire/{id}")
    public User hireUser(@PathVariable Long id, @RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            throw new ValidationException(errors);
        }
        checkIfUserMatchId(id, user);
        return userService.addEmployee(user);
    }

    @PutMapping("/fire/{id}")
    public User fireUser(@PathVariable Long id, @RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            throw new ValidationException(errors);
        }
        checkIfUserMatchId(id, user);
        return userService.removeEmployee(user);
    }

    private void checkIfUserMatchId(Long id, User user){
        if(!id.equals(user.getId())) {
            throw new InvalidEntityDataException(
                    String.format("Url ID:%d differs from body entity ID:%d", id, user.getId()));
        }
    }
}
