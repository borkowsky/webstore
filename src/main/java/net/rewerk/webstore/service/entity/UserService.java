package net.rewerk.webstore.service.entity;

import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.transport.dto.request.user.SearchDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    User create(User user);

    List<User> findAll();

    List<User> searchByUsername(SearchDto searchDto);

    User getByUsername(String username);

    UserDetailsService userDetailsService();

    void delete(User user);
}
