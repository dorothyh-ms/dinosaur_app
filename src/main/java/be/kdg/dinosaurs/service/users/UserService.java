package be.kdg.dinosaurs.service.users;

import be.kdg.dinosaurs.domain.User;
import be.kdg.dinosaurs.repository.users.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository userRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByName(String username) {
        LOGGER.info("UserService is running getUserByName");
        User user = userRepository.findByUsername(username);
        if (user != null) {
            LOGGER.debug("Returning user with name {}", user.getUsername());
        } else {
            LOGGER.debug("User with name {} not found", username);
        }
        return user;

    }


}
