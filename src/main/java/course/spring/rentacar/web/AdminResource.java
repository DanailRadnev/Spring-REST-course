package course.spring.rentacar.web;

import course.spring.rentacar.exception.InvalidEntityDataException;
import course.spring.rentacar.model.entity.User;
import course.spring.rentacar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rentacar/admin/")
public class AdminResource {
    private UserService userService;

    @Autowired
    public AdminResource(UserService userService){
        this.userService = userService;
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
