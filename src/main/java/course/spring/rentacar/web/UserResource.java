package course.spring.rentacar.web;

import course.spring.rentacar.exception.InvalidEntityDataException;
import course.spring.rentacar.exception.ValidationException;
import course.spring.rentacar.model.entity.User;
import course.spring.rentacar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequestMapping("/rentacar/user")
public class UserResource {

    private UserService userService;

    @Autowired
    public UserResource(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable long id){return userService.getUserById(id);}

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user, Errors errors, HttpServletRequest request) {
        if(errors.hasErrors()){
            throw new ValidationException(errors);
        }
        User created = userService.createUser(user);
        return ResponseEntity.created(
                MvcUriComponentsBuilder
                        .fromMethodCall(on(UserResource.class).createUser(user, errors, request))
                        .pathSegment("{id}").build(created.getId())
        ).body(created);
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable Long id, @RequestBody @Valid User user, Errors errors) {
        if(errors.hasErrors()){
            throw new ValidationException(errors);
        }
        //TODO check for validation errors

        checkIfUserMatchId(id, user);
        return userService.updateUser(user);
    }

    @DeleteMapping("{id}")
    public User deleteUser(@PathVariable long id) {
        return userService.deleteUser(id);
    }

    private void checkIfUserMatchId(Long id, User user){
        if(!id.equals(user.getId())) {
            throw new InvalidEntityDataException(
                    String.format("Url ID:%d differs from body entity ID:%d", id, user.getId()));
        }
    }
}
