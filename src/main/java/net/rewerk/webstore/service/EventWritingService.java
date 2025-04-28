package net.rewerk.webstore.service;

import net.rewerk.webstore.model.entity.Event;
import net.rewerk.webstore.model.entity.User;
import net.rewerk.webstore.repository.EventRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class EventWritingService {
    protected void writeEvent(EventRepository eventRepository, String text) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if (user != null) {
            eventRepository.save(Event.builder()
                    .user(user).text(text)
                    .build());
        }
    }
}
