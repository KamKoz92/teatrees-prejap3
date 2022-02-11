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
    private Map<Key, List<KeySubscriber>> subscribers;
    private Key allKeys = Key.getKey(Key.VC_ALL);

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
        if (instance != null) {
            return instance;
        } else {
            System.err.println("No keyLogger registerd.");
            return null;
        }
    }

    private KeyLoggerImp() {
        subscribers = new HashMap<Key, List<KeySubscriber>>();
        subscribers.put(allKeys, new ArrayList<KeySubscriber>());
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
    public void subscribeForKeys(KeySubscriber event, int... keyCodes) {
        for (int code : keyCodes) {
            Key key = Key.getKey(code);
            if (!subscribers.containsKey(key)) {
                subscribers.put(key, new ArrayList<KeySubscriber>(Arrays.asList(event)));
            } else {
                subscribers.get(key).add(event);
            }
        }
    }

    @Override
    public void unsubscribeForKeys(KeySubscriber event, int... keyCodes) {
        for (int code : keyCodes) {
            Key key = Key.getKey(code);
            if (subscribers.containsKey(key) &&
                    subscribers.get(key).contains(event)) {
                subscribers.get(key).remove(event);
            }
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent keyEvent) {
        Key key = Key.getKey(keyEvent.getKeyCode());
        if (subscribers.containsKey(key)) {
            subscribers.get(key).forEach(sub -> sub.accept(key));
        }
        subscribers.get(allKeys).forEach(sub -> sub.accept(key));
    }
}
