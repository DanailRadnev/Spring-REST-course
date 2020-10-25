package course.spring.rentacar.service.impl;

import course.spring.rentacar.dao.UserRepository;
import course.spring.rentacar.exception.NonexistingEntityException;
import course.spring.rentacar.model.entity.User;
import course.spring.rentacar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@Transactional(propagation=REQUIRED)
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){this.userRepository = userRepository;}

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NonexistingEntityException(String.format("User with ID:%d does not exist", id)));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User createUser(User user) {
        user.setId(null);
//        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        userRepository.findById(user.getId()).orElseThrow(
                () -> new NonexistingEntityException(String.format("User with ID:%d does not exist", user.getId())));
        return userRepository.save(user);
    }

    @Override
    public User deleteUser(Long id) {
        User result = this.getUserById(id);
        userRepository.delete(result);
        return result;
    }

    @Override
    public User addEmployee(User emp) {
        //TODO assign EMPLOYEE Role to the given User.
        return null;
    }

    @Override
    public User removeEmployee(User emp) {
        //TODO remove EMPLOYEE Role to the given User.
        return null;
    }
}
