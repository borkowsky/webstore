package net.rewerk.webstore.service.entity;

import net.rewerk.webstore.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    User create(User user);

    void save(User user);

    List<User> findAll();

    User getByEmail(String email);

    User getByUsername(String username);

    UserDetailsService userDetailsService();

    void delete(User user);
}
