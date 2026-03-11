package com.hostpilot.mapper;

import com.hostpilot.dto.PropertyDto;
import com.hostpilot.model.Property;
import org.springframework.stereotype.Component;

@Component
public class PropertyMapper {

    public Property toEntity(PropertyDto dto) {
        Property p = new Property();
        copy(dto, p);
        return p;
    }

    public void updateEntity(Property p, PropertyDto dto) {
        copy(dto, p);
    }

    private void copy(PropertyDto dto, Property p) {
        p.setName(dto.getName());
        p.setAddress(dto.getAddress());
        p.setCheckIn(dto.getCheckIn());
        p.setCheckOut(dto.getCheckOut());
        p.setWifiName(dto.getWifiName());
        p.setWifiPassword(dto.getWifiPassword());
        p.setRules(dto.getRules());
        p.setDescription(dto.getDescription());
    }
}
