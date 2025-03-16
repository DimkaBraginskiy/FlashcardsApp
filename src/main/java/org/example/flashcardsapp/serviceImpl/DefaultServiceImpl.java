package org.example.flashcardsapp.serviceImpl;

import org.example.flashcardsapp.service.DisplayService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("default")
public class DefaultServiceImpl implements DisplayService {
    @Override
    public String format(String text) {
        return text;
    }
}
