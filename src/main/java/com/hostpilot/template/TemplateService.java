package com.hostpilot.intent;

import com.hostpilot.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntentService {

    private final AiService aiService;

    public Intent detectIntent(String message) {
        String m = message.toLowerCase();

        // 1. Reglas rápidas (es/en)
        if (m.contains("check in") || m.contains("llegada") || m.contains("hora de entrada"))
            return Intent.CHECK_IN;

        if (m.contains("check out") || m.contains("salida") || m.contains("hora de salida"))
            return Intent.CHECK_OUT;

        if (m.contains("wifi") || m.contains("internet") || m.contains("contraseña"))
            return Intent.WIFI;

        if (m.contains("normas") || m.contains("reglas") || m.contains("rules"))
            return Intent.RULES;

        if (m.contains("dirección") || m.contains("address") || m.contains("ubicación"))
            return Intent.ADDRESS;

        // 2. Si no detectamos nada → pedir a la IA
        String prompt = """
                Classify the user's message into one of the following intents:
                - CHECK_IN
                - CHECK_OUT
                - WIFI
                - RULES
                - ADDRESS
                - UNKNOWN

                Respond ONLY with the intent name.

                Message:
                "%s"
                """.formatted(message);

        String result = aiService.generateReply(prompt, "").trim().toUpperCase();

        try {
            return Intent.valueOf(result);
        } catch (Exception e) {
            return Intent.UNKNOWN;
        }
    }
}
