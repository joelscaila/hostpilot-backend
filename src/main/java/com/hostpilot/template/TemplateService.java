package com.hostpilot.template;

import com.hostpilot.intent.Intent;
import com.hostpilot.model.Property;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    public String generateTemplate(Intent intent, Property p) {
        return switch (intent) {
            case CHECK_IN -> "El check-in es a partir de " + p.getCheckIn() + ".";
            case CHECK_OUT -> "El check-out es hasta " + p.getCheckOut() + ".";
            case WIFI -> "La red wifi es " + p.getWifiName() + " y la contraseña es " + p.getWifiPassword() + ".";
            case RULES -> "Las normas principales son: " + p.getRules() + ".";
            case ADDRESS -> "La dirección es: " + p.getAddress() + ".";
            default -> null;
        };
    }
}
