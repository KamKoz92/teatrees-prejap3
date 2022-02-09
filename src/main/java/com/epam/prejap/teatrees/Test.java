package com.epam.prejap.teatrees;

import com.epam.prejap.teatrees.keyboard.ConsumerEvent;

public class Test implements ConsumerEvent {

    @Override
    public void accept(Integer t) {
        System.out.println("Hello from test class");
    }
    
}
