package com.hostpilot.service;

import com.hostpilot.dto.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MemoryService {

    private final Map<Long, Deque<ChatMessage>> memory = new HashMap<>();

    private static final int MAX_HISTORY = 5;

    public void addMessage(Long propertyId, ChatMessage message) {
        memory.putIfAbsent(propertyId, new ArrayDeque<>());

        Deque<ChatMessage> history = memory.get(propertyId);

        if (history.size() >= MAX_HISTORY) {
            history.removeFirst();
        }

        history.addLast(message);
    }

    public List<ChatMessage> getHistory(Long propertyId) {
        return new ArrayList<>(memory.getOrDefault(propertyId, new ArrayDeque<>()));
    }

    public void clearHistory(Long propertyId) {
        memory.remove(propertyId);
    }
}
