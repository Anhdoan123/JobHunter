package vn.anhdoan.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.anhdoan.jobhunter.domain.User;
import vn.anhdoan.jobhunter.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserbyId(long id) {
        Optional<User> user = this.userRepository.findById(id);
        if (user.isEmpty()) {
            return null;
        }
        return user.get();
    }

    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }

    public void handleDeleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    public User handleCreatUser(User user) {
        return this.userRepository.save(user);
    }

    public User handleUpdateUser(User user) {
        User optionalUser = this.getUserbyId(user.getId());
        if (optionalUser != null) {
            optionalUser.setEmail(user.getEmail());
            optionalUser.setName(user.getName());
            optionalUser.setPassword(user.getPassword());
            this.userRepository.save(optionalUser);
        }
        return optionalUser;
    }

    public User getUserByEmail(String email) {
        Optional<User> userOptional = this.userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else
            return null;
    }
}
