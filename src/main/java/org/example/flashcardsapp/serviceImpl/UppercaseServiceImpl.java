package org.example.flashcardsapp.serviceImpl;

import org.example.flashcardsapp.service.DisplayService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("uppercase")
public class UppercaseServiceImpl implements DisplayService {
    @Override
    public String format(String text) {
        return text.toUpperCase();
    }
}
