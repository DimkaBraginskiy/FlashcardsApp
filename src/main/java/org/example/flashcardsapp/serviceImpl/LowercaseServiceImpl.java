package org.example.flashcardsapp.serviceImpl;

import org.example.flashcardsapp.service.DisplayService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("lowercase")
public class LowercaseServiceImpl implements DisplayService {
    @Override
    public String format(String text) {
        return text.toLowerCase();
    }
}
