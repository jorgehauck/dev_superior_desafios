package com.devsuperior.bds02.services;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {
    @Autowired
    private EventRepository repository;

    @Transactional(rollbackFor = Exception.class)
    public EventDTO update(Long id, EventDTO dto) {
        try {
            Event eventEntity = repository.getReferenceById(id);
            eventEntity.setName(dto.getName());
            eventEntity.setDate(dto.getDate());
            eventEntity.setUrl(dto.getUrl());
            eventEntity.setCity(new City(dto.getCityId(), null));
            eventEntity = repository.save(eventEntity);
            return new EventDTO(eventEntity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado!");
        }
    }
}
