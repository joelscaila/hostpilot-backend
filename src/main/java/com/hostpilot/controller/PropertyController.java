package com.hostpilot.controller;

import com.hostpilot.dto.PropertyDto;
import com.hostpilot.mapper.PropertyMapper;
import com.hostpilot.model.Property;
import com.hostpilot.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyRepository repository;
    private final PropertyMapper mapper;

    @GetMapping
    public List<Property> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Property getById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));
    }

    @PostMapping
    public Property create(@RequestBody PropertyDto dto) {
        Property p = mapper.toEntity(dto);
        return repository.save(p);
    }

    @PutMapping("/{id}")
    public Property update(@PathVariable Long id, @RequestBody PropertyDto dto) {
        Property p = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        mapper.updateEntity(p, dto);
        return repository.save(p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
