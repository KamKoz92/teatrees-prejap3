package com.epam.prejap.teatrees.keyboard;

public interface KeyLogger {
    
    public void subscribeForKey(int keyCode, ConsumerEvent event);
    public void unsubscribeForKey(int keyCode, ConsumerEvent event); 
}
