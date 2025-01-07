package org.highload.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) {
        if (user == null) {
            throw new ValidationException("User object cannot be null.");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new ValidationException("User email cannot be null or empty.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException("Email is already in use.");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email cannot be null or empty.");
        }
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    @Override
    public boolean userExistsByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email cannot be null or empty.");
        }
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> searchUsers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new ValidationException("Search keyword cannot be null or empty.");
        }
        return userRepository.searchUsers(keyword);
    }

    @Override
    public List<User> getUsersByStatus(User.UserStatus status) {
        if (status == null) {
            throw new ValidationException("User status cannot be null.");
        }
        return userRepository.findByStatus(status);
    }

    @Override
    public List<User> getUsersByRole(String roleName) {
        if (roleName == null || roleName.trim().isEmpty()) {
            throw new ValidationException("Role name cannot be null or empty.");
        }
        return userRepository.findByRoleName(roleName);
    }

    @Override
    public boolean hasUserRole(Long userId, String roleName) {
        if (userId == null || userId <= 0) {
            throw new ValidationException("User ID must be a positive number.");
        }
        if (roleName == null || roleName.trim().isEmpty()) {
            throw new ValidationException("Role name cannot be null or empty.");
        }
        return userRepository.hasRole(userId, roleName);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email cannot be null or empty.");
        }
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }
}