package com.epam.prejap.teatrees.keyboard;

public interface KeyLogger {

    public void subscribeForKeys(KeySubscriber event, int... keyCodes);

    public void unsubscribeForKeys(KeySubscriber event, int... keyCodes);
}
