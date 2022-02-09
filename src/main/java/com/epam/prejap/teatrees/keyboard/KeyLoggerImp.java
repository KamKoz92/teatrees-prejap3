package com.epam.prejap.teatrees.keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class KeyLoggerImp implements KeyLogger, NativeKeyListener {

    private static KeyLoggerImp instance;
    Map<Key, List<KeyEvent>> subscribers;

    public static void registerKeyLogger() {
        if (instance == null) {
            instance = new KeyLoggerImp();
        }
    }

    public static void unregisterKeyLogger() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

    public static KeyLogger getKeyLogger() {
        return instance;
    }

    private KeyLoggerImp() {
        subscribers = new HashMap<Key, List<KeyEvent>>();
        registerHook();
        addListener();
    }

    private void addListener() {

        GlobalScreen.addNativeKeyListener(this);
    }

    private void registerHook() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribeForKey(int keyCode, KeyEvent event) {
        Key key = Key.getKey(keyCode);
        if (!subscribers.containsKey(key)) {
            subscribers.put(key, new ArrayList<KeyEvent>(Arrays.asList(event)));
        } else {
            subscribers.get(key).add(event);
        }

    }

    @Override
    public void unsubscribeForKey(int keyCode, KeyEvent event) {
        Key key = Key.getKey(keyCode);
        if (subscribers.containsKey(key) &&
                subscribers.get(key).contains(event)) {
            subscribers.get(key).remove(event);
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent event) {
        Key key = Key.getKey(event.getKeyCode());
        if (subscribers.containsKey(key)) {
            subscribers.get(key).forEach(sub -> sub.accept(key));
        }
    }
}
