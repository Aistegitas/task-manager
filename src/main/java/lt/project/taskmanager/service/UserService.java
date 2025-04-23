package lt.project.taskmanager.service;

import lombok.RequiredArgsConstructor;
import lt.project.taskmanager.entity.User;
import lt.project.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public void addUser(User user) {
        userRepository.saveAndFlush(user);
    };


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    public void addTestUsers() {
        // User 1
        User user1 = new User();
        user1.setFirstName("Alice");
        user1.setLastName("Anderson");
        user1.setEmail("alice@example.com");
        addUser(user1);

        // User 2
        User user2 = new User();
        user2.setFirstName("Bob");
        user2.setLastName("Brown");
        user2.setEmail("bob@example.com");
        addUser(user2);

        // User 3
        User user3 = new User();
        user3.setFirstName("Charlie");
        user3.setLastName("Clark");
        user3.setEmail("charlie@example.com");
        addUser(user3);

        // User 4
        User user4 = new User();
        user4.setFirstName("Diana");
        user4.setLastName("Davis");
        user4.setEmail("diana@example.com");
        addUser(user4);

        // User 5
        User user5 = new User();
        user5.setFirstName("Evan");
        user5.setLastName("Evans");
        user5.setEmail("evan@example.com");
        addUser(user5);
    }
}

