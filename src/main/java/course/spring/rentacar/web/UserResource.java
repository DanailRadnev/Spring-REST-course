package course.spring.rentacar.web;

import course.spring.rentacar.exception.InvalidEntityDataException;
import course.spring.rentacar.model.entity.User;
import course.spring.rentacar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<User> createUser(@RequestBody User user, HttpServletRequest request) {
        User created = userService.createUser(user);
        return ResponseEntity.created(
                MvcUriComponentsBuilder
                        .fromMethodCall(on(UserResource.class).createUser(user, request))
                        .pathSegment("{id}").build(created.getId())
        ).body(created);
    }

    @PutMapping("{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        //TODO check for validation errors

        checkIfUserMatchId(id, user);
        return userService.updateUser(user);
    }

    @DeleteMapping("{id}")
    public User deleteUser(@PathVariable long id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/ban/{id}")
    public User banUser(@PathVariable Long id, @RequestBody User user){
        checkIfUserMatchId(id, user);

        //TODO check if the logged user is Admin
        user.setActive(false);
        return userService.updateUser(user);
    }

    @PutMapping("/unban/{id}")
    public User unbanUser(@PathVariable Long id, @RequestBody User user){
        checkIfUserMatchId(id, user);

        //TODO check if the logged user is Admin
        user.setActive(true);
        return userService.updateUser(user);
    }

    private void checkIfUserMatchId(Long id, User user){
        if(!id.equals(user.getId())) {
            throw new InvalidEntityDataException(
                    String.format("Url ID:%d differs from body entity ID:%d", id, user.getId()));
        }
    }
}
