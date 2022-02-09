package com.epam.prejap.teatrees.keyboard;

public interface KeyLogger {

    public void subscribeForKey(int keyCode, KeyEvent event);

    public void unsubscribeForKey(int keyCode, KeyEvent event);
}
