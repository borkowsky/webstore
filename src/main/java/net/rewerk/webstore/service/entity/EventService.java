package net.rewerk.webstore.service.entity;

import net.rewerk.webstore.model.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {
    Page<Event> findAll(Pageable pageable);

    Event findById(Integer id);

    Event create(Event event);

    Event update(Event event);

    void delete(Event event);
}
