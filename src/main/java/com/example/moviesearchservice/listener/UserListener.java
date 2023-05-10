package com.example.moviesearchservice.listener;

import com.example.moviesearchservice.model.User;
import com.example.moviesearchservice.service.SequenceGeneratorService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

public class UserListener extends AbstractMongoEventListener<User> {

    SequenceGeneratorService service;

    public UserListener(SequenceGeneratorService service) {
        this.service = service;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<User> event) {
        if (event.getSource().getUserId() < 1) {
            event.getSource().setUserId(service.generateUserId(User.SEQUENCE_NAME));
        }
    }
}
