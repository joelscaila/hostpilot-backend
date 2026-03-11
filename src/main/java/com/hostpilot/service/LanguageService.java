package com.hostpilot.service;

import org.springframework.stereotype.Service;

@Service
public class LanguageService {

    public String detectLanguage(String text) {
        if (text == null || text.isBlank()) return "es";

        text = text.trim().toLowerCase();

        // Chino
        if (text.matches(".*[\\u4e00-\\u9fff].*")) return "zh";

        // Japonés
        if (text.matches(".*[\\u3040-\\u309f\\u30a0-\\u30ff].*")) return "ja";

        // Coreano
        if (text.matches(".*[\\uac00-\\ud7af].*")) return "ko";

        // Francés (palabras muy distintivas)
        if (text.matches(".*\\b(bonjour|merci|supermarché|proximité|s'il vous plaît|adresse|réseau)\\b.*"))
            return "fr";

        // Alemán
        if (text.matches(".*\\b(hallo|danke|straße|bitte|netzwerk)\\b.*"))
            return "de";

        // Inglés
        if (text.matches(".*\\b(hello|please|thanks|wifi|address|network)\\b.*"))
            return "en";

        return "es";
    }
}
