package net.rewerk.webstore.service.entity.impl;

import lombok.RequiredArgsConstructor;
import net.rewerk.webstore.model.entity.Event;
import net.rewerk.webstore.repository.EventRepository;
import net.rewerk.webstore.service.entity.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public Page<Event> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Event create(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event update(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void delete(Event event) {
        eventRepository.delete(event);
    }

    @Override
    public Event findById(Integer id) {
        return eventRepository.findById(id).orElse(null);
    }
}
