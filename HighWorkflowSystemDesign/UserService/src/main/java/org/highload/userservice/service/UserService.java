package org.highload.userservice.service;

import com.highload.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    Optional<User> getUserByEmail(String email);
    boolean userExistsByEmail(String email);
    List<User> searchUsers(String keyword);
    List<User> getUsersByStatus(User.UserStatus status);
    List<User> getUsersByRole(String roleName);
    boolean hasUserRole(Long userId, String roleName);
}