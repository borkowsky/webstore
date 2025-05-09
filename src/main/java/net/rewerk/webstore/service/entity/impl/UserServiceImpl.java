package net.rewerk.webstore.service.entity.impl;

import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.exception.EmailExistsException;
import net.rewerk.webstore.exception.RepositoryMethodDisabled;
import net.rewerk.webstore.exception.UserNotFoundException;
import net.rewerk.webstore.exception.UsernameExistsException;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.repository.UserRepository;
import net.rewerk.webstore.service.entity.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameExistsException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailExistsException("Email already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


    public User getByUsername(String username) {
        return userRepository.getByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    @Override
    public void delete(User user) {
        throw new RepositoryMethodDisabled("Delete method is not supported");
    }

    public User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }
}
