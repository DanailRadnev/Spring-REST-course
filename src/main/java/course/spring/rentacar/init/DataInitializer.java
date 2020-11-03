package course.spring.rentacar.init;

import course.spring.rentacar.model.Role;
import course.spring.rentacar.model.entity.User;
import course.spring.rentacar.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static course.spring.rentacar.model.Role.ADMIN;
import static course.spring.rentacar.model.Role.USER;
@Slf4j
public class DataInitializer implements CommandLineRunner {
    @Autowired
    UserService userService;

    @Override
    public void run(String... args) throws Exception {
        List<User> defaultUsers = Arrays.asList(new User[]{
                new User( "Pencho", "Penchev", "pencho@google.com", "pencho", "pencho", new HashSet<Role>(Arrays.asList(new Role[]{ADMIN})), true),
                        new User( "Gosho", "Penchev", "pencho@google.com", "gosho", "gosho", new HashSet<Role>(Arrays.asList(new Role[]{USER})), true),
                        new User( "Tosho", "Penchev", "pencho@google.com", "tosho", "tosho", new HashSet<Role>(Arrays.asList(new Role[]{USER})), true)});

//        if(userService.count() == 0) {
            defaultUsers.stream().map(userService::createUser)
                    .map(user -> user.getId() + ": " + user.getUsername())
                    .forEach(log::info);
//        }
    }
}
